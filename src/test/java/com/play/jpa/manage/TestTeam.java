/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.manage;

import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.service.MemberService;
import com.play.jpa.service.TeamService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

//mvn test -Dtest=com.play.jpa.manage.TestTeam
public class TestTeam {
    @Test
    void test(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        
        TeamService ts = new TeamService(em);
        MemberService ms = new MemberService(em);
        
        ts.showTeams();
        
        Team tigers = ts.pickTeam(402);
        tigers.introduce();
        
        Team JDG = ts.pickTeam(702);
        
        JDG.setName("JDG");
        
        ts.createTeam(JDG);
        
        Member moonSuIn = ms.pickMember("문수인");
        Member brigitteLin = ms.pickMember("임청하");
        
        //ts.addMember(JDG, brigitteLin);
        
        ts.elect(JDG,brigitteLin);
        
        //ts.elect(tigers,moonSuIn);
        
        
        tx.commit();
        em.close();
        emf.close();
    }
}
