package com.play.jpa.entity;

import com.play.jpa.util.ColorSpec;
import com.play.jpa.util.Print;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
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
    
    @OneToOne(mappedBy = "team",fetch = FetchType.LAZY)
    private Leader leader;

    
    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }
            
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
    
    public void introduce(){
        int totalPoint = 0;
        Print.out(getName());
        
        Leader leader = getLeader();
        if(leader!=null){
            Print.out(ColorSpec.PURPLE,"leader: "+leader.getMember().getName());
        }
        
        for(Member m:this.members){
            Print.out("\t"+m.getName()+": "+m.getPoint());
            totalPoint += m.getPoint();
        }
        
        Print.out(ColorSpec.UNDERLINE ,"\t"+totalPoint);
    }
    
    public void termination(Member m){
        if(m == null){
            throw new IllegalArgumentException("추방할 멤버가 없습니다.");
        }
        
        if(!this.members.contains(m)){
            throw new IllegalArgumentException("현재 팀의 소속이 아닙니다.");
        }
        
        if(this.leader != null && this.leader.getMember().equals(m)){
            throw new IllegalArgumentException("리더는 강퇴할수 없습니다. 리더를 교체하세요.");
        }
        
        this.members.remove(m);
        m.leaveTeam();
        
    }
   
}
