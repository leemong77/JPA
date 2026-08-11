package com.play.jpa;

import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ModifyEnv {

    private EntityManager em;

    public ModifyEnv(EntityManager em) {
        this.em = em;
    }

    // member의 Team을 teamName으로 변경 (없으면 새로 생성)
    public void modifyTeam(Member member, String teamName) {
        Team team = findTeamByName(teamName);

        if (team == null) {
            team = new Team();
            team.setName(teamName);
            em.persist(team);
            System.out.println("Team 없어서 새로 생성 : " + teamName);
        }

        member.setTeam(team);
        System.out.println("Member [" + member.getName() + "] Team 변경 완료 -> " + teamName);
    }

    // member에 Hobby 추가
    public void addHobby(Member member, String hobbyName) {
        Hobby hobby = new Hobby();
        hobby.setName(hobbyName);
        hobby.setMember(member);
        em.persist(hobby);
        System.out.println("Member [" + member.getName() + "] Hobby 추가 완료 -> " + hobbyName);
    }

    // member의 Team, Hobby 목록 출력
    public void showMember(Member member) {
        System.out.println("=== Member 정보 ===");
        System.out.println("이름 : " + member.getName());
        System.out.println("나이 : " + member.getAge());
        System.out.println("성별 : " + member.getGender());

        Team team = member.getTeam();
        System.out.println("소속 팀 : " + (team != null ? team.getName() : "없음"));

        List<Hobby> hobbies = em.createQuery(
                "SELECT h FROM Hobby h WHERE h.member = :member", Hobby.class)
                .setParameter("member", member)
                .getResultList();

        if (hobbies.isEmpty()) {
            System.out.println("취미 : 없음");
        } else {
            System.out.println("취미 목록 :");
            for (Hobby h : hobbies) {
                System.out.println(" - " + h.getName());
            }
        }
        System.out.println("==================");
    }

    // Team 이름으로 조회 (내부 헬퍼 메소드)
    private Team findTeamByName(String teamName) {
        try {
            TypedQuery<Team> query = em.createQuery(
                    "SELECT t FROM Team t WHERE t.name = :name", Team.class);
            query.setParameter("name", teamName);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}