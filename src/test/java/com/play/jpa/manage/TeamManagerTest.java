package com.play.jpa.manage;

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

    @Test
    void test_team_base() {
        
        String teamName = "라이온즈";
        tm.createTeam(teamName);
        
        String jpql = "select t from Team t where t.name = :name";
        List<Team> result = em.createQuery(jpql, Team.class)
            .setParameter("name", teamName)
            .getResultList();
        
        assertEquals(1, result.size());
        
    }
}