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
    
    @Test
    void test_member(){
        Team t = ep.pickTeam("라이온즈");
        
        Member m = new Member();
        m.setName("임홍국");
        
        
        ep.addMember(t, m);
        
        
    }
}
