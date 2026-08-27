package com.play.jpa.util;

public class ColorSpec {
    // 텍스트 색상 기본 코드 [1, 2]
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    // 화려한 효과 코드 (터미널 환경에 따라 미지원될 수 있음) [2]
    public static final String BOLD = "\u001B[1m";      // 굵게
    public static final String UNDERLINE = "\u001B[4m"; // 밑줄
    public static final String BLINK = "\u001B[5m";     // 깜빡임
    public static final String REVERSE = "\u001B[7m";   // 반전 (글자색 <-> 배경색)

    // 배경 색상
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    
}
