/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.listener;

import com.play.jpa.entity.Chronicle;
import com.play.jpa.entity.Leader;
import com.play.jpa.entity.Team;
import com.play.jpa.persistence.JpaUtil;
import com.play.jpa.util.Print;
import jakarta.persistence.PrePersist;
import java.util.Date;

/**
 *
 * @author window10
 */
public class LeaderListener {
    @PrePersist
    public void beforeSave(Leader l) {
        Print.out("리더삭제 크로니클 업데이트");
       
        Team t = l.getTeam();
        JpaUtil.execute(em->{
            Chronicle c = em.createQuery(
                "SELECT c FROM Chronicle c WHERE c.team = :team AND c.endDate IS NULL",
                Chronicle.class)
            .setParameter("team", t)
            .getResultStream()
            .findFirst()
            .orElse(null);
            
            if(c!=null)
                c.setEndDate(new Date());
            return null;
        });
       
    }
}
