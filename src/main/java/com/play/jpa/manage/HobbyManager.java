package com.play.jpa.manage;

import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.Member;
import jakarta.persistence.EntityManager;


public class HobbyManager{
    private final EntityManager em;
    
    public HobbyManager(EntityManager em){
        this.em = em;
    }
    
    public void createHobby(){
        
        Hobby h = new Hobby();
        String getMember = "select m from Member m where m.name = :name";
        Member member = em.createQuery(getMember,Member.class)
                .setParameter("name", "홍길동")
                .getSingleResult();
        
        h.setMember(member);
        h.setName("낚시");
        em.persist(h);
    }
}