package com.play.jpa;

import jakarta.persistence.EntityManager;

public class InitEnv {

    private EntityManager em;

    public InitEnv(EntityManager em) {
        this.em = em;
    }

    public void delHobby() {
        em.createQuery("DELETE FROM Hobby h").executeUpdate();
        System.out.println("Hobby 삭제 완료");
    }

    public void delMember() {
        em.createQuery("DELETE FROM Member m").executeUpdate();
        System.out.println("Member 삭제 완료");
    }

    public void delTeam() {
        em.createQuery("DELETE FROM Team t").executeUpdate();
        System.out.println("Team 삭제 완료");
    }

    public void initAll() {
        delHobby();
        delMember();
        delTeam();
    }
}