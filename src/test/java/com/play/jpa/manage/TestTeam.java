/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.manage;

import com.play.jpa.entity.HobbyOfMember;
import com.play.jpa.entity.Job;
import com.play.jpa.entity.Ledger;
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
import java.util.List;
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
        Team blizzard = ts.pickTeam(802);
        
        ts.earnings(JDG);
        ts.consume(JDG);
        
        ts.consume(lions);
        
        List<Team> P_orders = ts.rank("P");
        
        for(Team t:P_orders){
            Print.out(t.getName()+" : "+t.getMembers().size());
        }
        
        List<Team> W_orders = ts.rank("W");
        for(Team t:W_orders){
            int totPoint = 0;
            
            for(Member m:t.getMembers()){
                totPoint += m.getPoint();
            }
            
            Print.out(t.getName()+" : "+totPoint);
        }
        Print.outU("--------------------------");
        List<Team> H_orders = ts.rank("H");
        for(Team t:H_orders){
            int totPoint = 0;
            
            for(Member m:t.getMembers()){
                for(Ledger l:m.getLedgerList()){
                    if(l.getJob() == null)
                        totPoint += l.getPoint();
                }
                
            }
            
            Print.out(t.getName()+" : "+totPoint);
        }
        
        
        
        //team 생성
        
        //blizzard.setName("블리자드");
        //ts.createTeam(blizzard);
        
        /*
        //member
        Member moonSuIn = ms.pickMember("문수인");
        Member brigitteLin = ms.pickMember("임청하");
        Member gabDol = ms.pickMember("김갑돌");
        Member hongIl = ms.pickMember("최홍일");
        Member shl = ms.pickMember("임상현");
        Member duksu= ms.pickMember("김덕수");
        
        //라이온즈 멤버들
        Member HongK = ms.pickMember("임홍국");
        Member maK = ms.pickMember("마광수");
        Member assHole = ms.pickMember("함재삼");
        Member fuckSuin = ms.pickMember("김수인");
        Member aiu = ms.pickMember("아이우");
        Member pil = ms.pickMember("임상필");
        Member chohee = ms.pickMember("김초희");
        Member jangKookYoung = ms.pickMember("장국영");
        Member juUnbal = ms.pickMember("주윤발");
        Member hwanBiHong = ms.pickMember("이연걸");
        Member forsythias = ms.pickMember("개나리");
        
        //멤버생성
        //ms.createMember("임상현");
        
        //팀 소속
        //ts.addMember(JDG, jangKookYoung);
        
        //리더 선출
        ts.elect(blizzard, pil);
        
        
        //job 등록
        js.generate_jobs("actor",35);
        
        //job 
        Job sweeper = js.pickJob(2);
        Job merchant = js.pickJob(6);
        Job prosecutor = js.pickJob(52);
        Job scv = js.pickJob(4);
        Job lawyer = js.pickJob(2);
        Job dayLaborer = js.pickJob(302); 
        Job catButler = js.pickJob(352); 
        Job dogWalker = js.pickJob(353); 
        Job pandaFluffer = js.pickJob(354); 
        Job proBreather = js.pickJob(355); 
        Job bedTester = js.pickJob(356); 
        Job PooScooper = js.pickJob(402); 
        Job actor = js.pickJob(452); 
        
        //직업 매칭
        js.find_a_job(forsythias, sweeper);
        js.find_a_job(forsythias, catButler);
        js.find_a_job(forsythias, merchant);
        
        js.showJob();
        ms.showMember();
        
        //ts.addMember(lions, shit);
        //brigitteLin.showJobList();
        
        
        maK.introduction();
        //work
        js.work(forsythias, sweeper);
        js.work(forsythias, catButler);
        js.work(forsythias, merchant);
        js.work(maK, lawyer);
        js.work(maK, bedTester);
        //js.work(chohee, pandaFluffer);
        //js.work(chohee, bedTester);
        //for(int i=0;i<10;i++)
        //    js.work(assHole, PooScooper);
        //js.work(hongIl, merchant);
        
        //js.work(gabDol, sweeper);
        //js.work(gabDol, merchant);
        
        //ts.elect(JDG, brigitteLin);
        
        //giants.introduce();
        lions.introduce();
        tigers.introduce();
        //blizzard.introduce();
        //JDG.introduce();
        
        //ts.feeCollect(lions);
        //ts.feeCollect(lions);
        //JDG.introduce();
        //QueenBee.introduce();
        //tigers.introduce();
        //ts.termination(giants,gabDol);
        
        
        //team 으로 멀 할수 있디?
        //
        //ts.addMember(JDG, brigitteLin);
        
        //ts.elect(JDG,brigitteLin);
        
        //ts.elect(tigers,moonSuIn);
        */
        
        tx.commit();
        em.close();
        emf.close();
        
    }
}
