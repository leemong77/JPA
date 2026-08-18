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

    /*
        mvn test -Dtest=com.play.jpa.manage.TeamManagerTest
    */
    
    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    private TeamManager tm;
    

    @BeforeAll
    static void setUpFactory() {
        emf = Persistence.createEntityManagerFactory("myPU");
        
    }

    @AfterAll
    static void closeFactory() {
        emf.close();
        System.out.println("------------------>아 좋다!!");
        System.out.println("------------------>아 좋다!!");
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        tm = new TeamManager(em);
        tx = em.getTransaction();
        
        tx.begin();
    }

    @AfterEach
    void tearDown() {
        tx.commit();
        em.close();
    }

    //@Test
    void test_team_base() {
        
        String teamName = "자이언츠";
        tm.createTeam(teamName);
        
        String jpql = "select t from Team t where t.name = :name";
        List<Team> result = em.createQuery(jpql, Team.class)
            .setParameter("name", teamName)
            .getResultList();
        
        assertEquals(1, result.size());
        
        Team t = tm.pickTeam(teamName);
        
        assertTrue(t.getName().equals(teamName));
        
    }
    
    @Test
    void test_add_member(){
       String jpql = "select m from Member m where m.name = :name";
       Member m = em.createQuery(jpql, Member.class).setParameter("name", "김갑돌").getSingleResult();
       
       jpql = "select t from Team t where t.name = :name";
       Team t = em.createQuery(jpql,Team.class).setParameter("name", "자이언츠").getSingleResult();
       
       tm.addMember(t, m);
        
    }
}