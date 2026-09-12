/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Job;
import com.play.jpa.entity.JobOfMember;
import com.play.jpa.entity.Member;
import jakarta.persistence.EntityManager;

/**
 *
 * @author window10
 */
public class JobService {
    private final EntityManager em;

    public JobService(EntityManager em) {
        this.em = em;
    }
    
    public boolean isExist(Job j){
        boolean isExist = false;
        
        
        return isExist;
    }
    
    public void generate_jobs(Job j){
        em.persist(j);
    }
            
    public void find_a_job(Member m,Job j){
        
        JobOfMember jom = new JobOfMember();
        jom.setMember(m);
        jom.setJob(j);
        
        em.persist(jom);
        
    }
}
