/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.manage;

import com.play.jpa.entity.HobbyOfMember;
import com.play.jpa.entity.Job;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.service.HobbyService;
import com.play.jpa.service.JobService;
import com.play.jpa.service.MemberService;
import com.play.jpa.service.TeamService;
import com.play.jpa.util.Print;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

//mvn test -Dtest=com.play.jpa.manage.TestTeam
public class TestTeam {
    @Test
    void test(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        
        TeamService ts = new TeamService(em);
        MemberService ms = new MemberService(em);
        HobbyService hs = new HobbyService(em);
        JobService js = new JobService(em);
        
        ts.showTeams();
        
        //team
        Team QueenBee = ts.pickTeam(602);
        Team tigers = ts.pickTeam(402);
        Team JDG = ts.pickTeam(702);
        Team giants = ts.pickTeam(502);
        Team lions = ts.pickTeam(452);
        
        //team 생성
        
        //member
        Member moonSuIn = ms.pickMember("문수인");
        Member brigitteLin = ms.pickMember("임청하");
        Member gabDol = ms.pickMember("김갑돌");
        Member hongIl = ms.pickMember("최홍일");
        Member shl = ms.pickMember("임상현");
        Member shit = ms.pickMember("함재삼");
        
        //멤버생성
        //ms.createMember("임상현");
        
        //addMember
        //ts.addMember(QueenBee, gabDol);
        
        for(HobbyOfMember hom:gabDol.getHobbyList()){
            Print.out(hom.getHobby().getHobbyName());
        }
        
        //job 등록
        js.generate_jobs("catButler(고양이집사)",30);
        js.generate_jobs("DogWalker(댕댕이산책대행자)",25);
        js.generate_jobs("PandaFluffer(판다사육사)",40);
        js.generate_jobs("ProBreather(숨쉬기전문)",10);
        js.generate_jobs("BedTester(침대누워보기)",5);
        //Job dayLaborer = new Job();
        //dayLaborer.setName("일용직 노동자");
        //dayLaborer.setPoint(20);
        //js.generate_jobs(dayLaborer);
        //giants.addMember(shl);
        //ts.elect(giants, shl);
        
        Job sweeper = js.pickJob(2);
        Job merchant = js.pickJob(6);
        Job prosecutor = js.pickJob(52);
        Job scv = js.pickJob(4);
        Job lawyer = js.pickJob(2);
        Job dayLaborer = js.pickJob(302); 
        
        js.find_a_job(shit, dayLaborer);
        
        
        //lions.addMember(ms.pickMember("함재삼"));
        //lions.addMember(ms.pickMember("함재삼"));
        
        js.showJob();
        ms.showMember();
        
        giants.introduce();
        lions.introduce();
        
        ts.addMember(lions, shit);
        //brigitteLin.showJobList();
        
        /*
        find_a_job
        */
        //js.work(hongIl, scv);
        //js.work(hongIl, merchant);
        
        //js.work(gabDol, sweeper);
        //js.work(gabDol, merchant);
        
        //ts.elect(JDG, brigitteLin);
        
        giants.introduce();
                
        //JDG.introduce();
        //QueenBee.introduce();
        //tigers.introduce();
        //ts.termination(giants,gabDol);
        
        
        //team 으로 멀 할수 있디?
        //
        //ts.addMember(JDG, brigitteLin);
        
        //ts.elect(JDG,brigitteLin);
        
        //ts.elect(tigers,moonSuIn);
        
        
        tx.commit();
        em.close();
        emf.close();
    }
}
