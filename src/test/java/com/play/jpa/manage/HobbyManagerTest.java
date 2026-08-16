/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.play.jpa.manage;

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
public class HobbyManagerTest {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private HobbyManager hm;
    
    public HobbyManagerTest() {
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
        hm = new HobbyManager(em);
    }
    
    @AfterEach
    public void tearDown() {
        em.close();
    }

    @Test
    public void testSomeMethod() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        hm.createHobby();
        System.err.println("HOBBY TEST!!!!");
        
        tx.commit();
    }
    
}
