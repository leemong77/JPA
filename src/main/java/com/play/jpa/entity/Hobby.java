package com.play.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hobby")
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    // 외래키(member_id)를 가지는 쪽 = 연관관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Hobby() {}

    public Hobby(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Member getMember() { return member; }
    
    public void setMember(Member member) {
        this.member = member;
        if (member != null) {
            member.getHobbies().add(this);  // 연관관계 편의 메서드
        }
    }
}