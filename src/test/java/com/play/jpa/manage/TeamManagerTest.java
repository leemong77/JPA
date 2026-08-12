package com.play.jpa.manage;

import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamManagerTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private TeamManager teamManager;

    @BeforeAll
    static void setUpFactory() {
        emf = Persistence.createEntityManagerFactory("myPU");
    }

    @AfterAll
    static void closeFactory() {
        emf.close();
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        teamManager = new TeamManager(em);
    }

    @AfterEach
    void tearDown() {
        em.close();
    }

    @Test
    void 팀을_생성하면_조회된다() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        teamManager.createTeam("테스트팀");

        List<Team> result = em.createQuery(
                "SELECT t FROM Team t WHERE t.name = :name", Team.class)
            .setParameter("name", "테스트팀")
            .getResultList();

        assertEquals(1, result.size());
        assertEquals("테스트팀", result.get(0).getName());

        tx.rollback();
    }

    @Test
    void 같은_이름의_팀은_중복_생성되지_않는다() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        teamManager.createTeam("중복팀");
        teamManager.createTeam("중복팀");  // 두 번째는 스킵되어야 함

        List<Team> result = em.createQuery(
                "SELECT t FROM Team t WHERE t.name = :name", Team.class)
            .setParameter("name", "중복팀")
            .getResultList();

        assertEquals(1, result.size());  // 중복 생성 안 됐으므로 여전히 1건

        tx.rollback();
    }

    @Test
    void 팀에_멤버를_추가하면_소속된다() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 준비: 팀과 멤버 생성
        teamManager.createTeam("개발팀");
        Member member = new Member("홍길동", 25);
        em.persist(member);  // addTeam은 이미 영속 상태인 member를 기대하므로 먼저 persist

        // 실행: 팀에 멤버 추가
        teamManager.addTeam("개발팀", member);

        // 검증 1: member 쪽에서 team이 제대로 세팅됐는지
        assertNotNull(member.getTeam());
        assertEquals("개발팀", member.getTeam().getName());

        // 검증 2: DB 기준으로도 실제 연결됐는지 (JPQL로 재조회)
        List<Member> teamMembers = em.createQuery(
                "SELECT m FROM Member m WHERE m.team.name = :teamName", Member.class)
            .setParameter("teamName", "개발팀")
            .getResultList();

        assertEquals(3, teamMembers.size());
        assertEquals("홍길동", teamMembers.get(0).getName());

        tx.rollback();
    }

    @Test
    void 존재하지_않는_팀에_추가하면_소속되지_않는다() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member("김철수", 30);
        em.persist(member);

        teamManager.addTeam("없는팀", member);  // 경고 출력 후 아무 일도 안 일어나야 함

        assertNull(member.getTeam());  // team이 세팅되지 않았어야 함

        tx.rollback();
    }
}