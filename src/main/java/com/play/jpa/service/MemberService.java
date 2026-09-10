/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import jakarta.persistence.EntityManager;

/**
 *
 * @author window10
 */
public class MemberService {
    private final EntityManager em;

    public MemberService(EntityManager em) {
        this.em = em;
    }
    
    public void toBeTeam(Member member, Team t){
        
        Member m = pickMember(member.getName());
        
        if(m != null){
            System.out.println("to be team inner!!!");
            t.addMember(m);
            //m.setTeam(t);
            //em.persist(m);
            em.persist(t);
        }
    }
    
    public Member pickMember(String name){
        String jpql = "select m from Member m where m.name = :name";
        Member m = em.createQuery(jpql,Member.class)
                .setParameter("name", name)
                .getSingleResult();
        
        return m;
    }
    
    public Member pickMember(int id){
        String jpql = "select m from Member m where m.id = :id";
        Member m = em.createQuery(jpql,Member.class)
                .setParameter("id", id)
                .getSingleResult();
        
        return m;
    }
    
}
