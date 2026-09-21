package com.play.jpa.service;

import com.play.jpa.persistence.QueryUtil;
import jakarta.persistence.EntityManager;

public abstract class BaseService {
    protected final EntityManager em;
    protected final QueryUtil query;

    protected BaseService(EntityManager em) {
        this.em = em;
        this.query = new QueryUtil(em);
    }
}

