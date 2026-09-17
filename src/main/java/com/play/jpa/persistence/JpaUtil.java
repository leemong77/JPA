/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaUtil {

    // 1. Factory는 딱 하나만 static으로 로딩
    private static final EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("myPU"); // persistence.xml의 unit-name

    // 2. 트랜잭션 및 EntityManager 생명주기를 완벽하게 제어하는 템플릿 메서드
    public static <T> T execute(JpaCallback<T> callback) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            // 실제 비즈니스 로직 실행
            T result = callback.doInJpa(em); 
            
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e); // 언체크 예외로 전환하여 전파
        } finally {
            // ★ 무조건 실행되므로 커넥션 풀 고갈을 완벽히 방지합니다.
            em.close(); 
        }
    }

    public static void closeFactory() {
        if (emf != null && emf.isOpen()) emf.close();
    }
}

