/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.entity;

import com.play.jpa.listener.ChronicleListener;
import com.play.jpa.util.Print;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(ChronicleListener.class)
@Table(name = "chronicle")
public class Chronicle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;   // null이면 "현재까지 진행 중"이라는 의미

    protected Chronicle() {}

    public Chronicle(Team team, Member member, Date startDate) {
        this.team = team;
        this.member = member;
        this.startDate = startDate;
    }

    /**
     * 이 재임 기간을 종료 처리한다. (리더 교체 시 호출)
     */
    public void close(Date endDate) {
        this.endDate = endDate;
    }

    public Long getId() { return id; }
    public Team getTeam() { return team; }
    public Member getMember() { return member; }
    public Date getStartDate() { return startDate; }
    public void setEndDate(Date d) { this.endDate = d; }
    public Date getEndDate() { return endDate; }
    public boolean isOngoing() { return endDate == null; }
    
}