package com.play.jpa.manage;

import com.play.jpa.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class MemberManager {
    private final EntityManager em;
    
    public MemberManager(EntityManager em){
        this.em = em;
    }

    public void createMember(String name){
        List<Member> existing = em.createQuery(
                "select m from Member m where m.name=:name ", Member.class)
                .setParameter("name", name)
                .getResultList();
        
        if(!existing.isEmpty()){
            System.err.println("aleady exits!");
            return;
        }
        
        Member m =new Member();
        m.setName(name);
        em.persist(m);
        System.out.println("new Member!!");
    }   
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        MemberManager mm = new MemberManager(em);
        
        mm.createMember("김갑돌");
        
        tx.commit();
        em.close();
        emf.close();
    }
}
