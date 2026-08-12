package com.play.jpa.manage;

import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Team 엔티티를 관리하는 클래스.
 * EntityManager는 생성자로 주입받으며, 트랜잭션은 이 클래스 밖(호출하는 쪽)에서 관리한다.
 */
public class TeamManager {

    private final EntityManager em;

    public TeamManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 새 팀을 생성한다. 이미 같은 이름의 팀이 있으면 경고 메시지를 출력하고 건너뛴다.
     */
    public void createTeam(String name) {
        List<Team> existing = em.createQuery(
                "SELECT t FROM Team t WHERE t.name = :name", Team.class)
            .setParameter("name", name)
            .getResultList();

        if (!existing.isEmpty()) {
            System.out.println("[경고] 이미 존재하는 팀입니다: " + name);
            return;
        }

        Team team = new Team(name);
        em.persist(team);
        System.out.println("[팀 생성] " + name);
    }

    /**
     * 모든 팀을 조회해서 출력한다.
     */
    public void showTeam() {
        List<Team> teams = em.createQuery(
                "SELECT t FROM Team t", Team.class)
            .getResultList();

        if (teams.isEmpty()) {
            System.out.println("등록된 팀이 없습니다.");
            return;
        }

        for (Team t : teams) {
            System.out.println("팀: " + t.getName() + " (id=" + t.getId() + ")");
        }
    }

    /**
     * 이름에 해당하는 팀만 조회해서 출력한다.
     */
    public void showTeam(String name) {
        List<Team> teams = em.createQuery(
                "SELECT t FROM Team t WHERE t.name = :name", Team.class)
            .setParameter("name", name)
            .getResultList();

        if (teams.isEmpty()) {
            System.out.println("해당 이름의 팀이 없습니다: " + name);
            return;
        }

        for (Team t : teams) {
            System.out.println("팀: " + t.getName() + " (id=" + t.getId() + ")");
        }
    }

    /**
     * teamName에 해당하는 팀에 member를 추가한다.
     * 팀이 존재하지 않으면 경고 메시지를 출력하고 건너뛴다.
     */
    public void addTeam(String teamName, Member member) {
        List<Team> result = em.createQuery(
                "SELECT t FROM Team t WHERE t.name = :name", Team.class)
            .setParameter("name", teamName)
            .getResultList();

        if (result.isEmpty()) {
            System.out.println("[경고] 팀을 찾을 수 없습니다: " + teamName);
            return;
        }

        Team team = result.get(0);
        member.setTeam(team);  // 연관관계 편의 메서드가 team.getMembers()에도 추가해줌
        System.out.println("[팀 소속 추가] " + member.getName() + " -> " + teamName);
    }
}