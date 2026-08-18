package com.play.jpa.manage;

import jakarta.persistence.EntityManager;

public class TeamManager {
    private final EntityManager em;

    public TeamManager(EntityManager em) {
        this.em = em;
    }
    
    
    
}
