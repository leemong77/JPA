package com.play.jpa.manage;

import com.play.jpa.service.TeamService;
import com.play.jpa.util.ColorSpec;
import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.HobbyOfMember;
import com.play.jpa.entity.Job;
import com.play.jpa.entity.JobOfMember;
import com.play.jpa.entity.Ledger;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.service.JobService;
import com.play.jpa.service.MemberService;
import com.play.jpa.util.Print;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;
import org.checkerframework.checker.units.qual.Prefix;
import org.hibernate.stat.Statistics;

import static org.junit.jupiter.api.Assertions.*;
//mvn test -Dtest=com.play.jpa.manage.IntegratedVerificationmvn test -Dtest=com.play.jpa.manage.IntegratedVerification
// mvn test -Dtest=com.play.jpa.manage.IntegratedVerificationmvn test -Dtest=com.play.jpa.manage.IntegratedVerification

public class IntegratedVerification {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    private EntityPlay ep;
    private TeamService ts;
    private MemberService ms;
    private JobService js;
    
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
        ts = new TeamService(em);
        ms = new MemberService(em);
        js = new JobService(em);
    }
    
    @AfterEach
    void tearDown() {
        if(tx != null && tx.isActive())
            tx.commit();
        em.close();
    }
    
    //@Test
    void 같은_id로_두번_조회하면_두번째는_캐시에서_가져온다() {
        
        // 준비: Ledger 하나 미리 저장해두기 (기존 데이터 활용해도 무방)
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        Ledger ledger = new Ledger();  // 실제로는 Member/Job 등과 함께 구성하셔야 함
        em1.persist(ledger);
        tx1.commit();
        Long ledgerId = ledger.getId();
        em1.close();

        // 통계 초기화
        Statistics stats = emf.unwrap(org.hibernate.SessionFactory.class).getStatistics();
        stats.clear();

        // 1번째 조회: 서로 다른 EntityManager(=1차 캐시 없음) → DB에서 읽고 2차 캐시에 적재
        EntityManager em2 = emf.createEntityManager();
        em2.find(Ledger.class, ledgerId);
        em2.close();

        // 2번째 조회: 또 다른 EntityManager → 이번엔 2차 캐시에서 읽어야 함
        EntityManager em3 = emf.createEntityManager();
        em3.find(Ledger.class, ledgerId);
        em3.close();

        System.out.println("2차 캐시 히트 수: " + stats.getSecondLevelCacheHitCount());
        System.out.println("2차 캐시 미스 수: " + stats.getSecondLevelCacheMissCount());

        assertTrue(stats.getSecondLevelCacheHitCount() >= 1);
    }
    
    /*
    ======>   402:타이거즈
    ======>   452:라이온즈
    ======>   502:자이언츠
    */
    @Test
    void test_team() throws Exception{
        
        //ts.createTeam("봉스팍");
        //ts.showTeams();
        
        Team tigers = ts.pickTeam(402);
        tigers.introduce();
        
        Team bigBoss = ts.pickTeam(602);
        Member bongQ = ms.pickMember(352);
        
        ts.addMember(bigBoss, bongQ);
        
        Job boss = new Job();
        boss.setName("BOSS");
        boss.setPoint(150);
        
        Job President = new Job();
        President.setName("President");
        President.setPoint(300);
        
        Job ChairMan = new Job();
        ChairMan.setName("ChairMan");
        ChairMan.setPoint(230);
        
        js.generate_jobs(boss);
        js.generate_jobs(President);
        js.generate_jobs(ChairMan);
        
        
        js.find_a_job(bongQ, boss);
        js.find_a_job(bongQ, President);
        js.find_a_job(bongQ, ChairMan);
        
        
        //sweeper, lawyer
        Job lawyer = ep.pickJob(2);
        Job janitor = ep.pickJob("수위");
        Job whiteHand = ep.pickJob("백수");
        Job concretePourong = ep.pickJob("건설");
        Job fruitSaler = ep.pickJob("과일청과");
        Job Prosecutor = ep.pickJob("검사");
        
        Hobby fish = ep.pickHobby(2);
        Hobby mountainClimbing = ep.pickHobby(1);
        Hobby shopping = ep.pickHobby(7);
        Hobby drinkingSoJu = ep.pickHobby("음주");
        
        //쇼핑 추가 취미에 문수인도 추가
        //ep.registerHobby("쇼핑",35);
        //ep.registerJob("검사",45);
        
        
        //Member hongKuk = ep.pickMember("임홍국");
        //ep.getAJob(hongKuk, Prosecutor);
        
        //hongKuk.introduction();
        
        //Member queenBee = ep.pickMember("박봉옥");
        
        /* *
        Member moon = ep.pickMember("문수인");
        Member hongKuk = ep.pickMember("임홍국");
        
        Member anyone = hongKuk;
        
        ep.showEarning(anyone);
        ep.showUsePoint(anyone);
        
        Print.out(ColorSpec.PURPLE,moon.getPoint()+"");
        
        
        //ep.createAccount(queenBee);
        
        
        ep.work(hongKuk, fruitSaler);
        ep.work(hongKuk, whiteHand);
        ep.work(hongKuk, Prosecutor);
        //ep.retire(hongKuk, whiteHand);
        
        ep.enjoy(hongKuk, drinkingSoJu);
        ep.enjoy(hongKuk, fish);
        ep.enjoy(hongKuk, mountainClimbing);
        ep.enjoy(hongKuk, shopping);
        
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
        
        /*
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
        /*
        //hobby 등록
        ep.registerHobby("여행");
        ep.registerHobby("맛집");
        ep.registerHobby("음주");
        */
    }
    
    //@Test
    void test_addHobby(){
        
        Member m = null; //ep.pickMember("임홍국");
        Hobby h = ep.pickHobby("맛집");
        
        ep.addHobby(m,h);
        
        h = ep.pickHobby("음주");
        ep.addHobby(m,h);
        
    }
    
    //@Test
    void test_job(){
        /*
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
        
        */
    }
}
