/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.manage;

import com.play.jpa.entity.Hobby;
import com.play.jpa.service.HobbyService;
import com.play.jpa.util.Print;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import org.junit.jupiter.api.Test;

//mvn test -Dtest=com.play.jpa.manage.TestHobby
public class TestHobby {

    @Test
    public void hobby(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        
        HobbyService hs = new HobbyService(em);
        
        hs.showHobbies();
        
        hs.registerHobby("스타크래프트", 20);
        
        List<Hobby> hobbies = hs.exesizeJPQL("주");
        
        hobbies.stream().forEach(h->Print.out(h.getHobbyName()));
        
        tx.commit();
        em.close();
        emf.close();
    }
}
