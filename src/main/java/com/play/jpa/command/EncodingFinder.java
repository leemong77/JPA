package com.play.jpa.command;

public class EncodingFinder {
    public static void find(String hangul) {
        if (hangul.length() == 0) {
            System.out.println("테스트할 한글 파라미터를 입력해주세요.");
            return;
        }

        String brokenStr = hangul;
        System.out.println("[입력된 원본 깨진 문자열]: " + brokenStr);
        System.out.println("--------------------------------------------------");

        // Windows cmd 환경에서 주로 충돌하는 인코딩 후보군
        String[] encodings = {"UTF-8", "CP949", "MS949", "EUC-KR", "ISO-8859-1"};

        System.out.println("[인코딩 조합별 복원 테스트]");
        for (String from : encodings) {
            for (String to : encodings) {
                if (from.equals(to)) continue;
                
                try {
                    // 깨진 문자열의 바이트를 'from' 인코딩으로 추출한 뒤, 'to' 인코딩으로 변환
                    String decoded = new String(brokenStr.getBytes(from), to);
                    
                    // 콘솔에 출력하여 한글이 제대로 깨지지 않고 보이는 조합을 찾습니다.
                    System.out.printf("From [%-10s] To [%-10s] -> %s\n", from, to, decoded);
                } catch (Exception e) {
                    // 지원하지 않는 인코딩 예외 처리
                }
            }
        }
    }
}
