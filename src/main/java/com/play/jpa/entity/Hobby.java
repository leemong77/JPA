package com.play.jpa.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hobby")
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id")
    private Long hobbyId;

    @Column(name = "hobby_name", nullable = false)
    private String hobbyName;
    
    private Integer point;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    // HobbyOfMember 쪽의 "hobby" 필드가 주인
    @OneToMany(mappedBy = "hobby")
    private List<HobbyOfMember> hobbyOfMembers = new ArrayList<>();

    public Hobby() {}

    public Hobby(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public Long getHobbyId() { return hobbyId; }
    public void setHobbyId(Long hobbyId) { this.hobbyId = hobbyId; }
    public String getHobbyName() { return hobbyName; }
    public void setHobbyName(String hobbyName) { this.hobbyName = hobbyName; }
    public List<HobbyOfMember> getHobbyOfMembers() { return hobbyOfMembers; }
    public void setHobbyOfMembers(List<HobbyOfMember> hobbyOfMembers) { this.hobbyOfMembers = hobbyOfMembers;}
}