package com.play.jpa.entity;

import com.play.jpa.util.ColorSpec;
import com.play.jpa.util.Print;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member")
public class Member {
    public Member(){};
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private String name;
    
    private Integer  point;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public List<HobbyOfMember> getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(List<HobbyOfMember> hobbyList) {
        this.hobbyList = hobbyList;
    }
    
    @Column(name = "is_queen")
    private Boolean isQueen;

    public Boolean getIsQueen() {
        if(isQueen == null){
            this.setIsQueen(false);
            
            isQueen = false;
        }
        
        return isQueen;
    }

    public void setIsQueen(Boolean isQueen) {
        this.isQueen = isQueen;
    }
    
    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    public Team getTeam() { return team; }
    
    
    public void setTeam(Team team) {
        this.team = team;
        if (team != null) {
                team.getMembers().add(this);  // 반대쪽 컬렉션도 같이 맞춰줌
        }
    }
    
    
    // HobbyOfMember 쪽의 "member" 필드가 주인
    @OneToMany(mappedBy = "member")
    private List<HobbyOfMember> hobbyList = new ArrayList<>();
    
    public List<HobbyOfMember> getHobbyOfMembers() { return hobbyList; }
    public void setHobbyOfMembers(List<HobbyOfMember> hobbyOfMembers) { this.hobbyList = hobbyOfMembers; }
    
    @OneToMany(mappedBy = "member")
    private List<JobOfMember> jobList = new ArrayList<>();
    
    @OneToMany(mappedBy = "member")
    private List<Ledger> ledgerList = new ArrayList<>();

    public void setLedgerList(List<Ledger> ledgerList) {
        this.ledgerList = ledgerList;
    }

    public List<Ledger> getLedgerList() {
        return ledgerList;
    }
    

    public List<JobOfMember> getJobList() {
        return jobList;
    }

    public void setJobList(List<JobOfMember> jobList) {
        this.jobList = jobList;
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

    void assignTeam(Team t) {
        this.team = t;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return id != null && id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();  // id 기반이 아니라 클래스 기반 고정값 사용 (JPA 프록시 이슈 회피)
    }
    
    public boolean hasHobby(Hobby hobby) {
        return hobbyList.stream()
            .anyMatch(hom -> hom.getHobby().equals(hobby));
    }
     
    
    public void showJobList(){
        jobList.forEach(jom->{
            Job j = jom.getJob();
            Print.reverse(ColorSpec.PURPLE,+j.getId()+":"+j.getName());
        });
    }
    
    public void showHobbyList(){
        
        hobbyList.forEach(hom->{
            Hobby h = hom.getHobby();
            Print.reverse(ColorSpec.CYAN,"\t"+h.getHobbyId()+":"+h.getHobbyName());
        });
        
        
    }

    public void earnPoint(int point) {
        
        this.point += point;
        
    }

    public void usePoint(int point) {
        this.point -= point;
    }
    
}
