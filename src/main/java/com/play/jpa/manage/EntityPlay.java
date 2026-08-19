package com.play.jpa.manage;

import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.HobbyOfMember;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EntityPlay {
    private final EntityManager em;

    public EntityPlay(EntityManager em) {
        this.em = em;
    }
    
    public void createTeam(String teamName){
        
        String jpql = "select count(t) from Team t where t.name = :name";
        Long count = em.createQuery(jpql, Long.class)
            .setParameter("name", teamName)
            .getSingleResult();
        
        if(count > 0){
            System.out.println("duplicate teamName!!!");
        }else{
            Team t = new Team(); 
            t.setName(teamName);

            em.persist(t);
        }
    }
    
    public Team pickTeam(String teamName){
        String jpql = "select t from Team t where t.name = :name";
        
        Team team = em.createQuery(jpql, Team.class)
                .setParameter("name", teamName)
                .getSingleResult();
                
        return team;
    }
    
    public void addMember(Team t,Member member){
        
        Member m = pickMember(member.getName());
        
        if(m != null){
            m.setTeam(t);
            em.persist(m);
        }else{
            member.setTeam(t);
            em.persist(member);
        }
    }
    
    public List<Team> list(){
        List<Team> list = em.createQuery("select t from Team t",Team.class)
                .getResultList();
        
        return list;
    }
    
    public int totalCount(){
        Long count = em.createQuery("select count(t) from Team t",Long.class)
                .getSingleResult();
        
        return Integer.parseInt(count+"");
    }
    
    public void createMember(String name){
        List<Member> existing = em.createQuery(
                "select m from Member m where m.name=:name ", Member.class)
                .setParameter("name", name)
                .getResultList();
        
        if(!existing.isEmpty()){
            System.err.println("aleady exits!");
            return;
        }
        
        Member m =new Member();
        m.setName(name);
        em.persist(m);
        System.out.println("new Member!!");
    }   
    
    public Member pickMember(String name){
        String jpql = "select m from Member m where m.name = :name";
        Member m = em.createQuery(jpql,Member.class)
                .setParameter("name", name)
                .getSingleResult();
        
        return m;
    }

    void registerHobby(Hobby h) {
        Hobby isH = pickHobby(h.getHobbyName());
        
        if(isH == null){
            em.persist(h);
        }
    }

    Hobby pickHobby(String hobbyName) {
        String jpql = "select h from Hobby h where h.hobbyName = :name";
        List<Hobby> isList = em.createQuery(jpql,Hobby.class)
                .setParameter("name", hobbyName)
                .getResultList();
        
        if(isList.isEmpty()){
            return null;
        }else{
            return isList.get(0);
        }
        
    }

    void addHobby(Member m, Hobby h) {
        HobbyOfMember hom = new HobbyOfMember();
        
        hom.setHobby(h);
        hom.setMember(m);
        
        if(h!=null && m!= null)
            em.persist(hom);
    }
}
