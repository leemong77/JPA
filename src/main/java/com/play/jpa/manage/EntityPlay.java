package com.play.jpa.manage;

import com.play.jpa.util.ColorSpec;
import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.HobbyOfMember;
import com.play.jpa.entity.Job;
import com.play.jpa.entity.JobOfMember;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EntityPlay {
    private final EntityManager em;

    public EntityPlay(EntityManager em) {
        this.em = em;
    }
    
    public void createTeam(String teamName){
        
        String jpql = "select count(t) from Team t where t.name = :name";
        Long count = em.createQuery(jpql, Long.class)
            .setParameter("name", teamName)
            .getSingleResult();
        
        if(count > 0){
            System.out.println("duplicate teamName!!!");
        }else{
            Team t = new Team(); 
            t.setName(teamName);

            em.persist(t);
            System.out.println("NEW TEAM!!!");
        }
    }
    
    public Team pickTeam(int id){
        String jpql = "select t from Team t where t.id = :id";
        
        Team team = em.createQuery(jpql, Team.class)
                .setParameter("id", id)
                .getSingleResult();
                
        return team;
    }
    
    public void toBeTeam(Team t,Member member){
        
        Member m = pickMember(member.getName());
        
        if(m != null){
            System.out.println("to be team inner!!!");
            t.addMember(m);
            //m.setTeam(t);
            //em.persist(m);
            //em.persist(t);
        }
    }
    
    public void toBeTeam(Team t,int memberId){
        
        Member m = pickMember(memberId);
        
        if(m != null){
            t.addMember(m);
            //m.setTeam(t);
            em.persist(m);
        }
    }
    
    public List<Team> list(){
        List<Team> list = em.createQuery("select t from Team t",Team.class)
                .getResultList();
        
        return list;
    }
    
    public int totalCount(){
        Long count = em.createQuery("select count(t) from Team t",Long.class)
                .getSingleResult();
        
        return Integer.parseInt(count+"");
    }
    
    public void createMember(String name){
        List<Member> existing = em.createQuery(
                "select m from Member m where m.name=:name ", Member.class)
                .setParameter("name", name)
                .getResultList();
        
        if(!existing.isEmpty()){
            System.err.println("aleady exits!");
            return;
        }
        
        Member m =new Member();
        m.setName(name);
        em.persist(m);
        System.out.println("new Member!!");
    }   
    
    public Member pickMember(String name){
        String jpql = "select m from Member m where m.name = :name";
        Member m = em.createQuery(jpql,Member.class)
                .setParameter("name", name)
                .getSingleResult();
        
        return m;
    }
    public Member pickMember(int id){
        String jpql = "select m from Member m where m.id = :id";
        Member m = em.createQuery(jpql,Member.class)
                .setParameter("id", id)
                .getSingleResult();
        
        return m;
    }

    public void registerHobby(String hobbyName) {
        Hobby isH = pickHobby(hobbyName);
        
        if(isH == null){
            Hobby h = new Hobby();
            h.setHobbyName(hobbyName);
            em.persist(h);
        }
    }
    
    public List<Hobby> listHobby() {
        String jpql = "select h from Hobby h";
        List<Hobby> isList = em.createQuery(jpql,Hobby.class)
                .getResultList();
               
        return isList;
    }
    
    public Hobby pickHobby(String hobbyName) {
        String jpql = "select h from Hobby h where h.hobbyName = :name";
        List<Hobby> isList = em.createQuery(jpql,Hobby.class)
                .setParameter("name", hobbyName)
                .getResultList();
        
        if(isList.isEmpty()){
            return null;
        }else{
            return isList.get(0);
        }
        
    }

    public Hobby pickHobby(int id) {
        String jpql = "select h from Hobby h where h.id = :id";
        List<Hobby> isList = em.createQuery(jpql,Hobby.class)
                .setParameter("id", id)
                .getResultList();
        
        if(isList.isEmpty()){
            return null;
        }else{
            return isList.get(0);
        }
        
    }
    
    public void addHobby(Member m, Hobby h) {
        for(HobbyOfMember hom : m.getHobbyOfMembers()){
            if(h.getHobbyId().intValue() == hom.getHobby().getHobbyId().intValue())
                return;
        }
        
        HobbyOfMember hom = new HobbyOfMember();
        
        hom.setHobby(h);
        hom.setMember(m);
        
        if(h!=null && m!= null)
            em.persist(hom);
    }

    public void listJob(){
        String jpql = "select j from Job j";
        List<Job> list = em.createQuery(jpql,Job.class)
                .getResultList();
        for(Job j:list){
            System.out.println(j.getId()+": "+j.getName());
        }
    }
    
    public void registerJob(String jobName) {
        Job cj = pickJob(jobName);
        
        if(cj == null){
            Job j = new Job();
            j.setName(jobName);

            em.persist(j);
        }
    }

    public Job pickJob(String jobName) {
        String jpql = "select j from Job j where j.name=:name";
        
        List<Job> jobs = em.createQuery(jpql,Job.class)
                .setParameter("name", jobName)
                .getResultList();
        
        if(jobs.isEmpty()){
            return null;
        }else{
            return jobs.get(0);
        }
    }
    
    public Job pickJob(int id) {
        String jpql = "select j from Job j where j.id=:id";
        
        List<Job> jobs = em.createQuery(jpql,Job.class)
                .setParameter("id", id)
                .getResultList();
        
        if(jobs.isEmpty()){
            return null;
        }else{
            return jobs.get(0);
        }
    }

    public void showAllTeam() {
        String jpql = "select t from Team t";
        List<Team> teamList = em.createQuery(jpql,Team.class)
                .getResultList();
        
        for(Team t:teamList){
            System.out.println("======>   "+t.getId()+":"+t.getName());
        }
        
    }

    public void disassembling(Team t) {
        System.out.printf("\n%s(%d)\n",t.getName(),t.getId().intValue());
                
        List<Member> members = t.getMembers();
        
        for(Member m:members){
            System.out.printf("\tname:%s\n",m.getName());
        }
    }

    public void quit_a_club(Member m) {
        System.out.println("quit a club?");
        m.setTeam(null);
        em.persist(m);
    }

    public void find_a_position(Member m, int jobId) {
        JobOfMember jom = new JobOfMember();
        jom.setMember(m);
        jom.setJob(pickJob(jobId));
        
        em.persist(jom);
        
    }

    public void getJobs(Member m) {
        for(JobOfMember j:m.getJobList()){
            Job job = j.getJob();
            System.out.println(job.getId() +" :"+job.getName());
        }
    }
    
    public Member quickLinkMember(int id) {
        
        String jpql = "select m from Member m where m.id = :id";
        Member m = em.createQuery(jpql,Member.class)
                .setParameter("id", id)
                .getSingleResult();
        
        return m;
    }
    
    public void showAllMember() {
        String jpql = "select m from Member m ORDER BY m.id";
        List<Member> list = em.createQuery(jpql,Member.class)
                .getResultList();
        
        for(Member m:list){
            String teamName = m.getTeam()!=null?m.getTeam().getName():"";
            
            System.out.printf("id:%d name:%s team:%s\n "
                    ,m.getId(),m.getName(),teamName);
        }
    }

    public void coronation(Member nowMember) {
        if("박봉옥".equals(nowMember.getName())){
            nowMember.setIsQueen(true);
            em.persist(nowMember);
        }
            
    }
    
    public void hasHobby(Member m){
        List<HobbyOfMember> homList = m.getHobbyOfMembers();
        
        if(homList.isEmpty()){
            System.out.println(ColorSpec.REVERSE+ColorSpec.CYAN +"any Interest? NO"+ColorSpec.RESET);
            
        }
        
        homList.forEach(hom->{
            Hobby h = hom.getHobby();
            System.out.printf("%d:%s\n",h.getHobbyId(),h.getHobbyName());
        });
    }


}
