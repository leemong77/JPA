package com.play.jpa.manage;

import com.play.jpa.util.ColorSpec;
import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.HobbyOfMember;
import com.play.jpa.entity.Job;
import com.play.jpa.entity.JobOfMember;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.util.Print;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;
import org.checkerframework.checker.units.qual.Prefix;

import static org.junit.jupiter.api.Assertions.*;

// mvn test -Dtest=com.play.jpa.manage.IntegratedVerification
public class IntegratedVerification {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    private EntityPlay ep;
    //private TeamManager tm;
    
    @BeforeAll
    static void setUpFactory() {
        emf = Persistence.createEntityManagerFactory("myPU");
    }
    
    @AfterAll
    static void closeFactory() {
        emf.close();
        System.out.println("------------------>아 좋다!!");
        System.out.println("------------------>아 좋다!!");
    }
    
    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
        ep = new EntityPlay(em);
    }
    
    @AfterEach
    void tearDown() {
        if(tx != null && tx.isActive())
            tx.commit();
        em.close();
    }
    
    /*
    ======>   402:타이거즈
    ======>   452:라이온즈
    ======>   502:자이언츠
    */
    @Test
    void test_team() throws Exception{
        
        
        //sweeper, lawyer
        Job lawyer = ep.pickJob(2);
        Job janitor = ep.pickJob("수위");
        Job whiteHand = ep.pickJob("백수");
        Job concretePourong = ep.pickJob("건설");
        Job fruitSaler = ep.pickJob("과일청과");
       
        Hobby fish = ep.pickHobby(2);
        Hobby mountainClimbing = ep.pickHobby(1);
        Hobby shopping = ep.pickHobby(7);
        
        //쇼핑 추가 취미에 문수인도 추가
        //ep.registerHobby("쇼핑",35);
        
        Member moon = ep.pickMember(402);
        ep.addHobby(moon, shopping);
        
        //갖고 있는 밥벌이
        assertTrue(moon.hasJob(lawyer));
        assertTrue(moon.hasJob(janitor));
        
        //없는 직업
        assertFalse(moon.hasJob(concretePourong));
        assertFalse(moon.hasJob(fruitSaler));
        
        int principal = moon.getPoint();
        Print.out("principal:" + principal);
        
        ep.work(moon, whiteHand);
        
        int dailyWage = whiteHand.getPoint();
        
        ep.enjoy(moon, shopping);
        ep.enjoy(moon, mountainClimbing);
        ep.enjoy(moon, fish);
        
        int consumedWage = fish.getPoint() 
                +shopping.getPoint()
                +mountainClimbing.getPoint()
                ;
        
        int bankStatement = dailyWage - consumedWage;
        
        Print.out("principal:" + principal);
        Print.out("dailyWage:" + dailyWage);
        Print.out("consumedWage:" + consumedWage);
        Print.out("bankStatement:" + bankStatement);
        Print.out("getPoint:" + moon.getPoint());
        
        assertEquals(moon.getPoint() , principal+bankStatement);
        
        /* *
        ep.enjoy(moon, shopping);
        ep.enjoy(moon,mountainClimbing);
        ep.enjoy(moon,fish);
        
        int minus = fish.getPoint()
                +mountainClimbing.getPoint()
                +shopping.getPoint();
        tx.commit();
        assertEquals(moon.getPoint(), point-minus);
        /* */
        //tx.rollback();
        
    }
    
    //@Test
    void test_member(){
        
        //자기_자신과는_항상_같다()
        Member member = new Member();
        member.setName("홍길동");
        assertEquals(member, member);
        
        Hobby h = new Hobby();
        h.setHobbyName("가무");
        
        HobbyOfMember hom = new HobbyOfMember();
        
        hom.setHobby(h);
        hom.setMember(member);
        
        member.getHobbyOfMembers().add(hom);
        
        
        assertTrue(member.hasHobby(h));
        
        
        // id가 둘 다 null인 신규(비영속) 객체 -> 같은 값이어도 다른 객체로 취급
        Member member1 = new Member();
        member1.setName("홍길동");
        
        System.out.println(member.getHobbyOfMembers());
        assertFalse(member1.hasHobby(h));
        
        ep.showAllTeam();
        Team t = ep.pickTeam( 402);
        
        t.getMembers().forEach(m->{System.out.println(m.getId()+":"+m.getName());});
        /*
        ep.toBeTeam(ep.pickTeam(402), ep.pickMember("임청하"));
        ep.toBeTeam(ep.pickTeam(402), ep.pickMember("개나리"));
        ep.toBeTeam(ep.pickTeam(452), ep.pickMember("마광수"));
        
        Team t = ep.pickTeam(502);
        
        String[] candidate = {"김덕수","마광수","개나리","아이우","임청하"};
        
        for(String human:candidate){
            ep.createMember(human);
            
            //if(human.)
            //ep.toBeTeam(t, ep.pickMember(human));
            
        }
        
        
        //hobby
        
        //김덕수
        Member m = ep.pickMember("김덕수");
        Hobby h = null;
        
        h = ep.pickHobby("맛집");
        ep.addHobby(m, h);
        /* *
        for(HobbyOfMember hom:m.getHobbyOfMembers()){
            Hobby hh = hom.getHobby();
            System.out.println(hh.getHobbyName());
        }
        /* */
        
    }
    
    //@Test
    void test_hobby(){
        //hobby 등록
        ep.registerHobby("여행");
        ep.registerHobby("맛집");
        ep.registerHobby("음주");
        
    }
    
    //@Test
    void test_addHobby(){
        Member m = ep.pickMember("임홍국");
        Hobby h = ep.pickHobby("맛집");
        
        ep.addHobby(m,h);
        
        h = ep.pickHobby("음주");
        ep.addHobby(m,h);
        
    }
    
    //@Test
    void test_job(){
        String[] jobNames = {"개발자","변호사","청소부","건설","수위","과일청과","백수"};
        
        for(String jobName:jobNames){
            ep.registerJob(jobName);
        }
        
        String jpql = "select count(j) from Job j";
        Long count = em.createQuery(jpql,Long.class)
                .getSingleResult();
        
        assertEquals(count.intValue(), jobNames.length);
                
        
        String jobName = "백수";
        Job j = ep.pickJob(jobName); 
        
        assertEquals(jobName, j.getName());
        
        JobOfMember jom = new JobOfMember();
        
        String memberName = "임홍국";
        
        Member m = ep.pickMember(memberName);
        
        jom.setMember(m);
        jom.setJob(j);
        
        em.persist(jom);
        
        
    }
}
