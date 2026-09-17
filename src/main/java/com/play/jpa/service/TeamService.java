/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Chronicle;
import com.play.jpa.entity.Leader;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.util.ColorSpec;
import com.play.jpa.util.Print;
import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 *
 * @author window10
 */
public class TeamService {
    private final EntityManager em;

    public TeamService(EntityManager em) {
        this.em = em;
    }
    
    public void showTeams(){
        String jpql = "select t from Team t";
        List<Team> allTeam = em.createQuery(jpql, Team.class).getResultList();
        
        allTeam.forEach(t->{
            Print.out(t.getName()+"["+t.getId()+"] population - "+t.getMembers().size());
            
            int totalPoint = 0;
            for(Member m:t.getMembers()){
                totalPoint += m.getPoint();
            }
            Print.out(ColorSpec.BG_GREEN,"\tpoint:"+totalPoint);
        });
    }
    
    public void createTeam(String teamName){
        
        String jpql = "select count(t) from Team t where t.name = :name";
        Long count = em.createQuery(jpql, Long.class)
            .setParameter("name", teamName)
            .getSingleResult();
        
        if(count > 0){
            System.out.println("duplicate teamName!!!");
        }else{
            Team t = new Team(); 
            t.setName(teamName);

            em.persist(t);
            System.out.println("NEW TEAM!!!");
        }
    }
    
    public Team pickTeam(int id){
        String jpql = "select t from Team t where t.id = :id";
        
        Team team = em.createQuery(jpql, Team.class)
                .setParameter("id", id)
                .getSingleResult();
                
        return team;
    }
    
    public void addMember(Team t, Member m){
        for(Member Affiliate:t.getMembers()){
            if(Affiliate.equals(m)){
                Print.out("aleady organization");
                return;
            }
        }
        
        t.getMembers().add(m);
        m.setTeam(t);
        
        em.persist(t);
    }
    
    
    public void elect(Team t,Member m){
        
        if(!t.getMembers().contains(m)){
            Print.out("Not a Member!!");
            return;
        }
        
        Date now = new Date();
        Leader leader = t.getLeader();
        
        if(leader!= null){
            Chronicle ongoing = findOngoingChronicle(t);
            if (ongoing != null) {
                ongoing.close(now);
            }
            
            Print.out(ColorSpec.BG_RED,"remove!!!");
            em.remove(t.getLeader());
            em.flush();
        }
        
        Leader l = new Leader();
        l.setTeam(t);
        l.setMember(m);
        
        em.persist(l);
        
        Chronicle c = new Chronicle(t,m,now);
        em.persist(c);
    }
    
    public Chronicle findOngoingChronicle(Team team) {
        return em.createQuery(
                "SELECT c FROM Chronicle c WHERE c.team = :team AND c.endDate IS NULL",
                Chronicle.class)
            .setParameter("team", team)
            .getResultStream()
            .findFirst()
            .orElse(null);
    }
}
