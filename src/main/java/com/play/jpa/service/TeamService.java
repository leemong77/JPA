/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Chronicle;
import com.play.jpa.entity.Leader;
import com.play.jpa.entity.Ledger;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.persistence.EmUtil;
import com.play.jpa.persistence.QueryUtil;
import com.play.jpa.util.ColorSpec;
import com.play.jpa.util.Print;
import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author window10
 */
public class TeamService extends BaseService{
    
    public TeamService(EntityManager em) {
        super(em);
    }
    
    public void showTeams(){
        String jpql = "select distinct t from Team t join fetch t.members";
        
        List<Team> allTeam = query.selectList(jpql, Team.class);
        
        allTeam.forEach(t->{
            Print.out(t.getName()+"["+t.getId()+"] population : "+t.getMembers().size());
            
            int totalPoint = 0;
            for(Member m:t.getMembers()){
                totalPoint += m.getPoint();
            }
            Print.out(ColorSpec.BG_GREEN,"\tpoint: "+totalPoint);
        });
    }
    
    public void createTeam(Team newT){
        
        String jpql = "select count(t) from Team t where t.name = :name";
        
        Long count = query.selectOne(
                jpql, Long.class, "name",newT.getName());
               
        if(count > 0){
            Print.out("duplicate Name!!!");
        }else{
            EmUtil.execute(em-> {
                em.persist(newT);
                return null;
            });
            System.out.println("NEW TEAM!!!");
        }
    }
    
    public Team pickTeam(int id){
        String jpql = "select distinct t from Team t join fetch t.members where t.id = :id ";
        return query.selectOne(jpql, Team.class, "id",id);
    }
    
    public void termination(Team t, Member m){
        t.termination(m);
        query.update(t);
    }
    
    public void addMember(Team t, Member m){
        if(m.getTeam() != null){
            Print.out("이미 팀에 소속되어 있습니다.");
            return;
        }
        
        for(Member Affiliate:t.getMembers()){
            if(Affiliate.equals(m)){
                Print.out("aleady organization");
                return;
            }
        }
        
        t.getMembers().add(m);
        m.setTeam(t);
        
        query.persist(t);
    }
    
    
    public void elect(Team t,Member m){
        
        for(Member tm:t.getMembers()){
            Print.out(tm.getName());
            
            Print.out("equals: "+tm.equals(m));
        }
        
        if(!t.getMembers().contains(m)){
            Print.out("Not a Member!!");
            return;
        }
        
        Date now = new Date();
        Leader leader = t.getLeader();
        
        if(leader!= null){
            
            query.remove(t.getLeader());
            em.flush();
            Print.out(ColorSpec.BG_RED,"remove!!!");
            
            String jpql = "SELECT c FROM Chronicle c WHERE c.team = :team AND c.endDate IS NULL";
            Chronicle c = query.selectOne(jpql, Chronicle.class, "team",t);
            if(c!=null)
                c.setEndDate(new Date());
        }
        
        Leader l = new Leader();
        l.setTeam(t);
        l.setMember(m);
        
        query.persist(l);
        
        Chronicle c = new Chronicle(t,m,now);
        query.persist(c);
    }
    
    // 회비 걷자!
    public void feeCollect(Team t){
        Leader l = t.getLeader();
        Member leader = l.getMember();
        
        if(l == null) {
            Print.out("리더가 없으면 회비를 걷지 못함");
            return;
        }
        
        for(Member m:t.getMembers() ){
            try {
                if(m.getId() != leader.getId()){
                    m.usePoint(50);
                    leader.earnPoint(50);
                }
            } catch (Exception ex) {
                Logger.getLogger(TeamService.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
    }
    
    
    public void earnings(Team t){
        int totalPoint = 0;
        
        List<Member> squad = t.getMembers();
        
        for(Member crew:squad){
            int privatePoint = 0;
            
            List<Ledger> sweats = crew.getLedgerList();
            
            for(Ledger l:sweats){
                if(l.getJob() != null){
                  privatePoint += l.getPoint();
                }
            }
            
            Print.outU(ColorSpec.CYAN, crew.getName()+" workPoint: "+privatePoint);
            
            totalPoint += privatePoint;
            
        }
        
        Print.outU(ColorSpec.CYAN, t.getName()+" total workPoint: "+totalPoint);
    }
    
    public void consume(Team t){
        int totalPoint = 0;
        
        List<Member> squad = t.getMembers();
        
        for(Member crew:squad){
            int privatePoint = 0;
            
            List<Ledger> sweats = crew.getLedgerList();
            
            for(Ledger l:sweats){
                if(l.getHobby() != null){
                  privatePoint += l.getPoint();
                }
            }
            
            Print.outU(ColorSpec.CYAN, crew.getName()+" consumePoint: "+privatePoint);
            
            totalPoint += privatePoint;
            
        }
        
        Print.outU(ColorSpec.CYAN, t.getName()+" total consumePoint: "+totalPoint);
    }
  
    public void rank(String option){
        if(option.equals("P")){
            Print.outU("Population!!");
            
            List<Team> teams = getList();
            
            //sort print?
        }
    }
    
    public List<Team> getList(){
        String jpql = "select t from Team t";
        
        return query.selectList(jpql, Team.class);
    }
}
