package com.play.jpa.manage;

import com.play.jpa.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author window10
 */
public class MemberManagerTest {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private MemberManager mm;
    
    public MemberManagerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("myPU");
    }
    
    @AfterAll
    public static void tearDownClass() {
        emf.close();
               
    }
    
    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        mm = new MemberManager(em);
    }
    
    @AfterEach
    public void tearDown() {
        em.close();
    }

    @Test
    public void 멤버_생성() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        mm.createMember("김영희", 30);
        
        tx.commit();
    }
    
}
