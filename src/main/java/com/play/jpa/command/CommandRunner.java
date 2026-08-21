package com.play.jpa.command;

import com.play.jpa.entity.Team;
import com.play.jpa.manage.EntityPlay;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.Scanner;

/**
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
            
    public static void main(String[] args) {
        
        //setting
        emf = Persistence.createEntityManagerFactory("myPU");
        em = emf.createEntityManager();
        tx =em.getTransaction();
        ep = new EntityPlay(em);
        
        Scanner scanner = new Scanner(System.in);

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
                dispatch(command, tokens);
                tx.commit();
            } catch (Exception e) {
                System.out.println("[오류] 명령 처리 중 예외 발생: " + e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * 명령어(command)에 따라 실제 처리를 분기한다.
     * 지금은 뼈대만 있으므로, 실제 로직은 이후 하나씩 채워넣는다.
     */
    private static void dispatch(String command, String[] tokens) {
        switch (command) {
            case "createTeam":
                String param = argsToString(tokens);
                ep.showAllTeam();
                Team t = ep.pickTeam(0);
                if(t != null){
                    System.out.println("");
                }
                        
                System.out.println("[미구현] createTeam 명령이 들어왔습니다. 인자: " + argsToString(tokens));
                break;

            case "showTeam":
                System.out.println("[미구현] showTeam 명령이 들어왔습니다. 인자: " + argsToString(tokens));
                break;

            case "createMember":
                System.out.println("[미구현] createMember 명령이 들어왔습니다. 인자: " + argsToString(tokens));
                break;

            default:
                System.out.println("알 수 없는 명령어입니다: " + command + " (help 입력 시 목록 확인)");
        }
    }

    private static String argsToString(String[] tokens) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < tokens.length; i++) {
            sb.append(tokens[i]).append(" ");
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
}