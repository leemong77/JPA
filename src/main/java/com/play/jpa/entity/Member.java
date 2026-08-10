package com.play.jpa.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private int age;

    // 외래키(team_id)를 가지는 쪽 = 연관관계의 주인
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Member() {}

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public Team getTeam() { return team; }
    public void setTeam(Team team) {
            this.team = team;
            if (team != null) {
                team.getMembers().add(this);  // 반대쪽 컬렉션도 같이 맞춰줌
        }
    }
    
    // Hobby 쪽의 "member" 필드가 주인
    @OneToMany(mappedBy = "member")
    private List<Hobby> hobbies = new ArrayList<>();
    
    public List<Hobby> getHobbies() { return hobbies; }
    public void setHobbies(List<Hobby> hobbies) { this.hobbies = hobbies; }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    // getter/setter 추가
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
}