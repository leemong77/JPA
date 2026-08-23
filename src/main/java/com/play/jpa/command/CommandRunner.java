package com.play.jpa.command;

import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.HobbyOfMember;
import com.play.jpa.entity.Member;
import com.play.jpa.entity.Team;
import com.play.jpa.manage.EntityPlay;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * mvn compile exec:java -Dexec.mainClass="com.play.jpa.command.CommandRunner" -Dexec.args="" -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstdin.encoding=UTF-8
 * 
 * 커맨드라인에서 사용자 입력을 계속 받아,
 * 입력된 명령어에 따라 적절한 동작을 실행하는 대화형 테스트 도구.
 *
 * 사용법 예시:
 *   > createTeam 개발팀
 *   > showTeam
 *   > exit
 */
public class CommandRunner {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;
    private static EntityPlay ep;
            
    static Team nowTeam = null;
    static Member nowMember = null;
    public static void main(String[] args) {
        
        //setting
        emf = Persistence.createEntityManagerFactory("myPU");
        em = emf.createEntityManager();
        tx =em.getTransaction();
        ep = new EntityPlay(em);
        
        Scanner scanner = new Scanner(System.in,"MS949");

        System.out.println("=== JPA 테스트 콘솔 ===");
        System.out.println("명령어를 입력하세요. 종료하려면 'exit' 입력.");
        printHelp();

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;  // 빈 입력은 무시하고 다시 대기
            }

            if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) {
                System.out.println("종료합니다.");
                break;
            }

            if (line.equalsIgnoreCase("help")) {
                printHelp();
                continue;
            }

            // 공백 기준으로 명령어와 인자 분리
            String[] tokens = line.split("\\s+");
            String command = tokens[0];

            try {
                tx.begin();
                isDescend();
                dispatch(command, tokens);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                System.out.println("[오류] 명령 처리 중 예외 발생: " + e.getMessage());
            }
        }

        scanner.close();
        System.exit(0);
    }

    /**
     * 명령어(command)에 따라 실제 처리를 분기한다.
     * 지금은 뼈대만 있으므로, 실제 로직은 이후 하나씩 채워넣는다.
     */
    private static void dispatch(String command, String[] tokens) {
        String param = "";
        switch (command) {
            case "createTeam":
                createTeam(tokens);
                break;

            case "showTeam":
                ep.showAllTeam();
                break;
            case "pickTeam":
                param = argsToString(tokens);
                nowTeam =ep.pickTeam(Integer.parseInt(param));
                break;
            case "nowTeam":
                System.out.println(nowTeam.getId()+" :"+nowTeam.getName());
                break;
            case "getMembers":
                for(Member m:nowTeam.getMembers()){
                    System.out.println(m.getId()+" "+m.getName());
                }
                break;
            case "pickMember":
                param = argsToString(tokens);
                nowMember =ep.pickMember(Integer.parseInt(param));
                break;
            case "nowMember":
                System.out.println(nowMember.getId()+" :"+nowMember.getName());
                break;

            case "getHobby":
                List<HobbyOfMember> hobbyList = nowMember.getHobbyOfMembers();
                for(HobbyOfMember hom : hobbyList){
                    Hobby h = hom.getHobby();
                    System.out.println(h.getHobbyName());
                }
                break;
                
            case "listHobby":
                for(Hobby h:ep.listHobby()){
                    System.out.println(h.getHobbyId()+" :"+h.getHobbyName());
                }
                break;
                
            case "setHobby":
                param = argsToString(tokens);
                Hobby h = ep.pickHobby(Integer.parseInt(param));
                ep.addHobby(nowMember, h);
                System.out.println("");
                break;
                
            case "listJob":
                ep.listJob();
                break;
            case "haveJob":
                param = argsToString(tokens);
                ep.find_a_position(nowMember,Integer.parseInt(param));
                break;
            case "getJobs":
                ep.getJobs(nowMember);
                break;
            case "quickM":
                param = argsToString(tokens);
                int id = Integer.parseInt(param);
                nowMember = ep.quickLinkMember(id);
                nowTeam = nowMember.getTeam();
                break;
            case "showM":
                ep.showAllMember();
                break;
            case "createMember":
                param = argsToString(tokens);
                ep.createMember(param);
                break;
            case "coronation":
                ep.coronation(nowMember);
                break;
            case "checkEnc":
                param = argsToString(tokens);
                EncodingFinder.find(param);
                break;    
                
            default:
                System.out.println("알 수 없는 명령어입니다: " + command + " (help 입력 시 목록 확인)");
        }
    }

    private static String argsToString(String[] tokens) {
        //String validParam = new String(args[0].getBytes("MS949"), "MS949");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < tokens.length; i++) {
            try {
                String validParam = new String(tokens[i].getBytes("MS949"), "MS949");
                sb.append(validParam).append(" ");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(CommandRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sb.toString().trim();
    }

    private static void printHelp() {
        System.out.println("""
                사용 가능한 명령어:
                  createTeam <팀이름>
                  showTeam [팀이름]
                  createMember <이름> <나이>
                  help              - 도움말 출력
                  exit / quit       - 종료
                """);
    }

    private static void createTeam(String[] tokens) {
        String param = argsToString(tokens);
        System.out.println(param);
        
        ep.createTeam(param);
    }

    private static void isDescend() {
        if(nowMember!= null && nowMember.getIsQueen()){
            System.out.println(ColorSpec.REVERSE + ColorSpec.CYAN + " 여왕님 강림하셨습니다!!! " + ColorSpec.RESET);
        }
        
    }
}