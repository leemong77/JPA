package com.play.jpa.manage;

import com.play.jpa.entity.Team;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TeamManager {
    private final EntityManager em;

    public TeamManager(EntityManager em) {
        this.em = em;
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
        }
    }
    
}
