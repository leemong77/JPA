/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.listener;

import com.play.jpa.entity.Chronicle;
import com.play.jpa.util.Print;
import jakarta.persistence.PrePersist;

/**
 *
 * @author window10
 */
public class ChronicleListener {
    @PrePersist
    public void beforeSave(Chronicle chronicle) {
        Print.out("[이력 기록] 팀 "+chronicle.getTeam().getName()+" 의 " + chronicle.getMember().getName() + " 리더 등록됨");
    }
    
}
