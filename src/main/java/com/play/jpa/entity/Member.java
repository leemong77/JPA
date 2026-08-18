package com.play.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="member")
public class Member {
    public Member(){};
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private String name;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;
    
    public Team getTeam() { return team; }
    public void setTeam(Team team) {
            this.team = team;
            if (team != null) {
                team.getMembers().add(this);  // 반대쪽 컬렉션도 같이 맞춰줌
        }
    }
    
    
    //
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
