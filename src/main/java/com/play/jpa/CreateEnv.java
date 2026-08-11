package com.play.jpa;

import com.play.jpa.entity.Gender;
import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;

import jakarta.persistence.EntityManager;

public class CreateEnv {

    private EntityManager em;

    // EntityManager를 외부에서 주입받아 공유
    public CreateEnv(EntityManager em) {
        this.em = em;
    }

    // Team 생성 - name 인자로 받고, 생성된 Team 리턴
    public Team CreateTeam(String name) {
        Team team = new Team();
        team.setName(name);
        em.persist(team);
        System.out.println("Team 생성 완료 : " + name);
        return team;
    }

    // Member 생성 - name, age, gender, team 인자로 받음
    public Member CreateMember(String name, int age, Gender gender, Team team) {
        Member member = new Member();
        member.setName(name);
        member.setAge(age);
        member.setGender(gender);
        member.setTeam(team);   // 같은 영속성 컨텍스트라 재조회 필요 없음
        em.persist(member);
        System.out.println("Member 생성 완료 : " + name);
        return member;
    }

    // Hobby 생성 - name, member 인자로 받음
    public Hobby CreateHobby(String name, Member member) {
        Hobby hobby = new Hobby();
        hobby.setName(name);
        hobby.setMember(member);
        em.persist(hobby);
        System.out.println("Hobby 생성 완료 : " + name);
        return hobby;
    }
}