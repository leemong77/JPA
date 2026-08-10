package com.play.jpa;

import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.Member;
import jakarta.persistence.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
        EntityManager em = emf.createEntityManager();

        try {
            createMember(em, "홍길동", 25);
            createMember(em, "홍길동", 25);  // 중복 -> 바이패스
            createMember(em, "김철수", 30);

            //setHobby(em, "홍길동", 25, "독서");
            //setHobby(em, "홍길동", 25, "등산");
            //setHobby(em, "김철수", 30, "수영");
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void createMember(EntityManager em, String name, int age) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<Member> duplicates = em.createQuery(
                "SELECT m FROM Member m WHERE m.name = :name AND m.age = :age",
                Member.class)
            .setParameter("name", name)
            .setParameter("age", age)
            .getResultList();

        if (!duplicates.isEmpty()) {
            System.out.println("[바이패스] 이미 존재하는 멤버입니다: " + name + " (" + age + "세)");
            tx.rollback();
            return;
        }

        Member member = new Member(name, age);
        em.persist(member);
        tx.commit();

        System.out.println("[저장 완료] " + name + " (" + age + "세)");
    }

    /**
     * 이름+나이로 멤버를 찾아 취미를 하나 추가합니다.
     * 해당 멤버가 존재하지 않으면 아무 것도 하지 않습니다.
     */
    public static void setHobby(EntityManager em, String memberName, int age, String hobbyName) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<Member> result = em.createQuery(
                "SELECT m FROM Member m WHERE m.name = :name AND m.age = :age",
                Member.class)
            .setParameter("name", memberName)
            .setParameter("age", age)
            .getResultList();

        if (result.isEmpty()) {
            System.out.println("[실패] 멤버를 찾을 수 없습니다: " + memberName + " (" + age + "세)");
            tx.rollback();
            return;
        }

        Member member = result.get(0);
        Hobby hobby = new Hobby(hobbyName);
        hobby.setMember(member);   // 연관관계 편의 메서드가 양쪽 다 세팅해줌
        em.persist(hobby);

        tx.commit();

        System.out.println("[취미 등록] " + memberName + " -> " + hobbyName);
    }
}