/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public class EmUtil {

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
            T result = callback.useEm(em); 
            
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
    
    /**
     * JPQL 단건 조회 공통 메서드
     * @param jpql 실행할 JPQL 문장
     * @param resultClass 변하는 M.class 자리에 들어갈 제네릭 타입
     * @param params "파라미터명", 값, "파라미터명", 값 순서대로 넣는 가변인자
     */
    public static <T> T queryForObject(String jpql, Class<T> resultClass, Object... params) {
        return execute(em -> {
            TypedQuery<T> query = em.createQuery(jpql, resultClass);
            
            // 파라미터를 짝수(이름), 홀수(값)로 매칭해서 세팅
            if (params != null) {
                for (int i = 0; i < params.length; i += 2) {
                    String paramName = (String) params[i];
                    Object paramValue = params[i + 1];
                    query.setParameter(paramName, paramValue);
                }
            }
            
            return query.getSingleResult();
        });
    }

    /**
     * JPQL 리스트 조회 공통 메서드 (위와 동일한데 결과만 List로 반환)
     */
    public static <T> List<T> queryForList(String jpql, Class<T> resultClass, Object... params) {
        return execute(em -> {
            TypedQuery<T> query = em.createQuery(jpql, resultClass);
            if (params != null) {
                for (int i = 0; i < params.length; i += 2) {
                    query.setParameter((String) params[i], params[i + 1]);
                }
            }
            return query.getResultList();
        });
    }
    
    public static <T> List<T> queryForListWithMap(String jpql, Class<T> resultClass, Map<String, Object> params) {
        return execute(em -> {
            TypedQuery<T> query = em.createQuery(jpql, resultClass);

            if (params != null) {
                // Map을 돌면서 파라미터 주입
                params.forEach(query::setParameter);
            }

            return query.getResultList();
        });
    }
}

