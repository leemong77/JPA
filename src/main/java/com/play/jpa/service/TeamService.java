/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.util.ColorSpec;
import com.play.jpa.util.Print;
import jakarta.persistence.EntityManager;
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
    
    
}
