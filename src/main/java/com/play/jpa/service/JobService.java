/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

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
}
