package com.play.jpa.manage;

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
    //private TeamManager teamManager;

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
        //teamManager = new TeamManager(em);
    }

    @AfterEach
    void tearDown() {
        em.close();
    }

    @Test
    void testAAA() {
        System.out.println("좋다!!");
                
    }
}