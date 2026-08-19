package com.play.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hobby_of_member")
public class HobbyOfMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    public HobbyOfMember() {}

    public HobbyOfMember(Member member, Hobby hobby) {
        setMember(member);
        setHobby(hobby);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Member getMember() { return member; }
    public void setMember(Member member) {
        this.member = member;
        if (member != null) {
            member.getHobbyOfMembers().add(this);
        }
    }

    public Hobby getHobby() { return hobby; }
    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
        if (hobby != null) {
            hobby.getHobbyOfMembers().add(this);
        }
    }
}