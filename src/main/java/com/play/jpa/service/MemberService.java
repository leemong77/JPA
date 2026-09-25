/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Job;
import com.play.jpa.entity.JobOfMember;
import com.play.jpa.entity.Ledger;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.persistence.EmUtil;
import com.play.jpa.persistence.QueryUtil;
import com.play.jpa.util.ColorSpec;
import com.play.jpa.util.Print;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author window10
 */
public class MemberService  extends BaseService{
    
    public MemberService(EntityManager em) {
       super(em);
    }
    
    public void createMember(String name){
        String jpql = "select m from Member m where m.name=:name";
        
        List<Member> existing = query.selectList(jpql, Member.class,"name",name);
        
        if(!existing.isEmpty()){
            Print.out(ColorSpec.RED,"aleady exits!");
            return;
        }
        
        Member m =new Member();
        m.setName(name);
        m.setPoint(100);
        query.persist(m);
        Print.out("new Member!!");
    }
    
    public void toBeTeam(Member member, Team t){
        
        Member m = pickMember(member.getName());
        
        if(m != null){
            System.out.println("to be team inner!!!");
            t.addMember(m);
            //m.setTeam(t);
            //em.persist(m);
            query.persist(t);
        }
    }
    
    public Member pickMember(String name){
        String jpql = "select m from Member m where m.name = :name";
        return query.selectOne(jpql, Member.class, "name",name);
    }
    
    public Member pickMember(int id){
        String jpql = "select m from Member m where m.id = :id";
        return query.selectOne(jpql, Member.class, "id",id);
    }
    
    public void showMember(){
        List<Member> list = query.selectList("select m from Member m", Member.class);
        
       for(Member m:list){
           String teamName = "";
           if(m.getTeam()!=null)
               teamName = m.getTeam().getName();
           Print.out(ColorSpec.GREEN, m.getName()+"["+teamName+"]");
       }
    }
    
}
