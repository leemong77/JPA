package com.play.jpa;

import com.play.jpa.entity.Gender;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            /* 
            // 1. 기존 데이터 초기화 (Hobby -> Member -> Team)
            InitEnv initEnv = new InitEnv(em);
            initEnv.initAll();

            // 2. 신규 데이터 생성 (Team -> Member -> Hobby)
            CreateEnv createEnv = new CreateEnv(em);

            Team team1 = createEnv.CreateTeam("개발팀");
            Team team2 = createEnv.CreateTeam("기획팀");

            Member member1 = createEnv.CreateMember("홍길동", 28, Gender.MALE, team1);
            Member member2 = createEnv.CreateMember("김영희", 25, Gender.FEMALE, team1);

            createEnv.CreateHobby("독서", member1);
            createEnv.CreateHobby("등산", member1);
            createEnv.CreateHobby("영화감상", member2);

            */
            
            ModifyEnv Mtool = new ModifyEnv(em);
            
            Member member = Mtool.findMemberByName("홍길동");
            
            Mtool.showMember(member);
            
            tx.commit();
            System.out.println("전체 트랜잭션 커밋 완료");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
                System.out.println("오류 발생 - 전체 롤백");
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}