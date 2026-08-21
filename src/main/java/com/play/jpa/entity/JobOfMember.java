package com.play.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="job_of_member")
public class JobOfMember {
    public JobOfMember(){};
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
    
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getJobList().add(this);
    }
    
    // 단방향 @ManyToOne: Job 쪽에는 아무 것도 안 둠
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;
    
    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

}
