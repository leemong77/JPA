package com.play.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="team")
public class Team {
    public Team(){}
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
    public void setMembers(List<Member> members) { this.members = members; }
    public List<Member> getMembers() { return members; }
    
    //
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addMember(Member m){
        getMembers().add(m);
        m.assignTeam(this);
    }
    
    public static Team pick(EntityManager em,int id){
        
        String jpql = "select t from Team t where t.id = :id";
        Team team = em.createQuery(jpql, Team.class)
                .setParameter("id", id)
                .getSingleResult();
                
        return team;
    }
    
    public static void showAll(EntityManager em){
        
        List<Team> list = em.createQuery("select t from Team t",Team.class)
                .getResultList();
        
        list.forEach(t->{System.out.println(t.getId()+" :"+t.getName());});
                
    }
}
