package com.play.jpa.manage;

import com.play.jpa.entity.Member;
import jakarta.persistence.EntityManager;
import java.util.List;

/*
    mvn test "-Dtest=MemberManagerTest"
*/

public class MemberManager{
    private final EntityManager em;
    
    public MemberManager(EntityManager em){
        this.em = em;
    }

    public void createMember(String name,int age){
        List<Member> existing = em.createQuery(
                "select m from Member m where m.name=:name ", Member.class)
                .setParameter("name", name)
                .getResultList();
        
        if(!existing.isEmpty()){
            System.err.println("aleady exits!");
            return;
        }
        
        Member m =new Member(name,age);
        em.persist(m);
        System.out.println("new Member!!");
    }   
    
    public static void main(String[] args) {
        
        //MemberManager mm = new MemberManager(em);
        System.out.println("com.play.jpa.manage.MemberManager.main()");
    }
}