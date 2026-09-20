/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.manage;

import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.service.MemberService;
import com.play.jpa.service.TeamService;
import org.junit.jupiter.api.Test;

//mvn test -Dtest=com.play.jpa.manage.TestTeam
public class TestTeam {
    @Test
    void test(){
        TeamService ts = new TeamService();
        
        
        ts.showTeams();
        
        Team tigers = ts.pickTeam(402);
        tigers.introduce();
        
        Team JDG = new Team();
        JDG.setName("JDG");
        
        ts.createTeam(JDG);
        
        MemberService ms = new MemberService();
        
        Member moonSuIn = ms.pickMember("문수인");
        Member brigitteLin = ms.pickMember("임청하");
        
        ts.termination(tigers, brigitteLin);
        ts.elect(tigers,moonSuIn);
        
        
    }
}
