/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Chronicle;
import com.play.jpa.entity.Leader;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.persistence.EmUtil;
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
    private EntityManager em;

    public TeamService(){}
    
    public TeamService(EntityManager em) {
        this.em = em;
    }
    
    public void showTeams(){
        String jpql = "select distinct t from Team t join fetch t.members";
        
        List<Team> allTeam = EmUtil.queryForList(jpql, Team.class);
        
        allTeam.forEach(t->{
            Print.out(t.getName()+"["+t.getId()+"] population : "+t.getMembers().size());
            
            int totalPoint = 0;
            for(Member m:t.getMembers()){
                totalPoint += m.getPoint();
            }
            Print.out(ColorSpec.BG_GREEN,"\tpoint: "+totalPoint);
        });
    }
    
    public void createTeam(Team newT){
        
        String jpql = "select count(t) from Team t where t.name = :name";
        
        Long count = EmUtil.queryForObject(
                jpql, Long.class, "name",newT.getName());
               
        if(count > 0){
            Print.out("duplicate Name!!!");
        }else{
            EmUtil.execute(em-> {
                em.persist(newT);
                return null;
            });
            System.out.println("NEW TEAM!!!");
        }
    }
    
    public Team pickTeam(int id){
        String jpql = "select distinct t from Team t join fetch t.members where t.id = :id ";
        return EmUtil.queryForObject(jpql, Team.class, "id",id);
    }
    
    public void termination(Team t, Member m){
        t.termination(m);
        EmUtil.execute(em->{
            em.persist(t);
            return null;
        });
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
            Print.out(ColorSpec.BG_RED,"remove!!!");
            EmUtil.remove(t.getLeader());
        }
        
        Leader l = new Leader();
        l.setTeam(t);
        l.setMember(m);
        
        EmUtil.persist(l);
        
        Chronicle c = new Chronicle(t,m,now);
        EmUtil.persist(c);
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
