/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.listener;

import com.play.jpa.entity.Chronicle;
import com.play.jpa.entity.Leader;
import com.play.jpa.util.Print;
import jakarta.persistence.PrePersist;

/**
 *
 * @author window10
 */
public class LeaderListener {
    @PrePersist
    public void beforeSave(Leader l) {
       Print.out("리더삭제 크로니클 업데이트");
       
    }
}
