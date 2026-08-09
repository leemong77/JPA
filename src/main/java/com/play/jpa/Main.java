package com.play.jpa;

import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 1. 팀 생성
        //Team team = new Team("개발팀");
        //em.persist(team);
        
        Team foundTeam = em.createQuery(
            "SELECT t FROM Team t WHERE t.name = :name", Team.class)
            .setParameter("name", "영업팀")
            .getSingleResult();

        Member member = foundTeam.getMembers().get(0);
        
        System.out.println(member.getName());
        
        tx.commit();
        
        /*
        Member member1 = em.find(Member.class, 1L);
        
        // 2. 멤버 생성 후 팀에 소속시키기
        Member member1 = new Member("홍길동", 25);
        member1.setTeam(team);   // ★ 주인 쪽에 값 세팅해야 DB에 반영됨
        em.persist(member1);

        Member member2 = new Member("김철수", 28);
        member2.setTeam(team);
        em.persist(member2);
        
        

        // 3. 조회: 멤버 -> 소속 팀 (다대일 조회는 자연스러움)
        Member found = em.find(Member.class, member1.getId());
        System.out.println("멤버 이름: " + found.getName());
        System.out.println("소속 팀: " + found.getTeam().getName());

        // 4. 조회: 팀 -> 소속 멤버 목록 (일대다 조회)
        Team foundTeam = em.find(Team.class, team.getId());
        for (Member m : foundTeam.getMembers()) {
            System.out.println("팀원: " + m.getName());
        }
        */
        em.close();
        emf.close();
    }
}