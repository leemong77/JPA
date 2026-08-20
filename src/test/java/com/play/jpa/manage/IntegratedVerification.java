package com.play.jpa.manage;

import com.play.jpa.entity.Hobby;
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
    
    @Test
    void test_team(){
        int totalCount = ep.totalCount();
        int listSize = ep.list().size();
        
        System.out.println("totalCount:"+totalCount);
        System.out.println("listSize:"+listSize);
        
        assertEquals(listSize,totalCount);
    }
    
    //@Test
    void test_member(){
        Team t = ep.pickTeam("라이온즈");
        
        Member m = new Member();
        m.setName("임홍국");
        
        ep.addMember(t, m);
        
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
    
    @Test
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
