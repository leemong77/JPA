package com.play.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="job")
public class Job {
    public Job(){}
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;

    private int point;
    
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    
}
