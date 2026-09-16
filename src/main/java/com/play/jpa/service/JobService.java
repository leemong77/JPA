/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Job;
import com.play.jpa.entity.JobOfMember;
import com.play.jpa.entity.Ledger;
import com.play.jpa.entity.Member;
import com.play.jpa.util.Print;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
        String jpql = "select count(j) from Job j where j.name = :name";
        Long isExist = em.createQuery(jpql,Long.class)
                .setParameter("name", j.getName())
                .getSingleResult();
        if(isExist > 0){
            Print.out("["+j.getName()+"] This has already been created!");
        }else{
            em.persist(j);
        }
    }
            
    public void find_a_job(Member m,Job j){
        
        JobOfMember jom = new JobOfMember();
        jom.setMember(m);
        jom.setJob(j);
        
        em.persist(jom);
        
    }

    public void work(Member m, Job j) {
        List<JobOfMember> list = m.getJobList();
        
        Optional<JobOfMember> opt = list.stream()
                .filter(jom->jom.getJob().equals(j)).findFirst();
        
        if(opt.isPresent()){
            m.earnPoint(j.getPoint());
            
            Ledger l = new Ledger();
            l.setMember(m);
            l.setJob(j);
            l.setPoint(j.getPoint());
            em.persist(l);
            
        }else{
            Print.out("Thst's not your job");
        }
            
    }
}
