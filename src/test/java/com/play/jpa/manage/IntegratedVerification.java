package com.play.jpa.manage;

import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.HobbyOfMember;
import com.play.jpa.entity.Job;
import com.play.jpa.entity.JobOfMember;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// mvn test -Dtest=com.play.jpa.manage.IntegratedVerification
public class IntegratedVerification {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    private EntityPlay ep;
    //private TeamManager tm;
    
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
        tx = em.getTransaction();
        tx.begin();
        ep = new EntityPlay(em);
    }
    
    @AfterEach
    void tearDown() {
        tx.commit();
        em.close();
    }
    
    /*
    ======>   402:타이거즈
    ======>   452:라이온즈
    ======>   502:자이언츠
    */
    //@Test
    void test_team(){
        int totalCount = ep.totalCount();
        int listSize = ep.list().size();
        
        System.out.println("totalCount:"+totalCount);
        System.out.println("listSize:"+listSize);
        
        assertEquals(listSize,totalCount);
        ep.showAllTeam();
        
        Team t = ep.pickTeam(502);
        
        ep.disassembling(t);
        
        //탈퇴 quit a club
        for(Member m:t.getMembers()){
            if(m.getName().equals("마광수")){
                System.out.println("is Here!!!!");
                //ep.quit_a_club(m);
            }
        }
    }
    
    @Test
    void test_member(){
        
        //자기_자신과는_항상_같다()
        Member member = new Member();
        member.setName("홍길동");
        assertEquals(member, member);
        
        /*
        ep.toBeTeam(ep.pickTeam(402), ep.pickMember("임청하"));
        ep.toBeTeam(ep.pickTeam(402), ep.pickMember("개나리"));
        ep.toBeTeam(ep.pickTeam(452), ep.pickMember("마광수"));
        
        Team t = ep.pickTeam(502);
        
        String[] candidate = {"김덕수","마광수","개나리","아이우","임청하"};
        
        for(String human:candidate){
            ep.createMember(human);
            
            //if(human.)
            //ep.toBeTeam(t, ep.pickMember(human));
            
        }
        
        
        //hobby
        
        //김덕수
        Member m = ep.pickMember("김덕수");
        Hobby h = null;
        
        h = ep.pickHobby("맛집");
        ep.addHobby(m, h);
        /* *
        for(HobbyOfMember hom:m.getHobbyOfMembers()){
            Hobby hh = hom.getHobby();
            System.out.println(hh.getHobbyName());
        }
        /* */
        
    }
    
    //@Test
    void test_hobby(){
        //hobby 등록
        ep.registerHobby("여행");
        ep.registerHobby("맛집");
        ep.registerHobby("음주");
        
    }
    
    //@Test
    void test_addHobby(){
        Member m = ep.pickMember("임홍국");
        Hobby h = ep.pickHobby("맛집");
        
        ep.addHobby(m,h);
        
        h = ep.pickHobby("음주");
        ep.addHobby(m,h);
        
    }
    
    //@Test
    void test_job(){
        String[] jobNames = {"개발자","변호사","청소부","건설","수위","과일청과","백수"};
        
        for(String jobName:jobNames){
            ep.registerJob(jobName);
        }
        
        String jpql = "select count(j) from Job j";
        Long count = em.createQuery(jpql,Long.class)
                .getSingleResult();
        
        assertEquals(count.intValue(), jobNames.length);
                
        
        String jobName = "백수";
        Job j = ep.pickJob(jobName); 
        
        assertEquals(jobName, j.getName());
        
        JobOfMember jom = new JobOfMember();
        
        String memberName = "임홍국";
        
        Member m = ep.pickMember(memberName);
        
        jom.setMember(m);
        jom.setJob(j);
        
        em.persist(jom);
        
        
    }
}
