/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.persistence;

import static com.play.jpa.persistence.EmUtil.execute;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public class QueryUtil {
    private final EntityManager em; 
    
    public QueryUtil(EntityManager em){
        this.em = em;
    }
    
    public <T> T selectOne(String jpql, Class<T> resultClass, Object... params ) {
        
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
       
    }
    
    public <T> List<T> selectList(String jpql, Class<T> resultClass, Object... params ) {
        
         TypedQuery<T> query = em.createQuery(jpql, resultClass);
            if (params != null) {
                for (int i = 0; i < params.length; i += 2) {
                    query.setParameter((String) params[i], params[i + 1]);
                }
            }
            return query.getResultList();
       
    }
    
    public <T> List<T> queryForListWithMap(String jpql, Class<T> resultClass, Map<String, Object> params) {
        TypedQuery<T> query = em.createQuery(jpql, resultClass);

        if (params != null) {
            // Map을 돌면서 파라미터 주입
            params.forEach(query::setParameter);
        }

        return query.getResultList();
    }
    
    //1. 완전신규저장
    public <T> void persist(T entity){
        em.persist(entity);
    }
    
    //2. 준영속 엔티티를 다시 저장/수정 (반환값 필수사용)
    public <T> T update(T entity){
        return em.merge(entity);
    }
    
    //3. 준영속 엔티티 삭제(merge 후 remove)
    public <T> void remove(T entity){
        T managed = em.merge(entity);
        em.remove(managed);
    }
    
    public <T> T findById(Class<T> entityClass, Object id) {
        return em.find(entityClass, id);
    }
    
    public int bulkUpdate(String jpql, Object... params) {
        Query query = em.createQuery(jpql);
        if (params != null) {
            for (int i = 0; i < params.length; i += 2) {
                query.setParameter((String) params[i], params[i + 1]);
            }
        }
        return query.executeUpdate();
    }
    
    public <T> List<T> selectPage(String jpql, Class<T> resultClass, int page, int size, Object... params) {
        TypedQuery<T> query = em.createQuery(jpql, resultClass);
        if (params != null) {
            for (int i = 0; i < params.length; i += 2) {
                query.setParameter((String) params[i], params[i + 1]);
            }
        }
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }
    
    public long count(String jpql, Object... params) {
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        if (params != null) {
            for (int i = 0; i < params.length; i += 2) {
                query.setParameter((String) params[i], params[i + 1]);
            }
        }
        return query.getSingleResult();
    }
}
