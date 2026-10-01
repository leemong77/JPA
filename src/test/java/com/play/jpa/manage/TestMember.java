package com.play.jpa.manage;

import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.Job;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.service.HobbyService;
import com.play.jpa.service.JobService;
import com.play.jpa.service.MemberService;
import com.play.jpa.service.TeamService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;


//mvn test -Dtest=com.play.jpa.manage.TestMember
public class TestMember {
    
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
        
        
        Member jkyong = ms.pickMember(602);
        Member JuYunBal = ms.pickMember(603);
        Member LeeYunGul = ms.pickMember(604);
        
        Job act = js.pickJob(452);
        
        hs.showHobbies();
        
        Hobby fish = hs.pickHobby(2);
        
        ms.getInto(fish, jkyong);
        hs.enjoyHobby(fish, jkyong);
        //js.work(jkyong, act);
        //js.work(JuYunBal, act);
        //js.work(LeeYunGul, act);
        
        
        
        
        //ms.showMember();
        
        tx.commit();
        em.close();
        emf.close();
    }
}
