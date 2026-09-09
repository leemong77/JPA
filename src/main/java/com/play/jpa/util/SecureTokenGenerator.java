/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SecureTokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public static String generateToken(int byteLength) {
        byte[] randomBytes = new byte[byteLength];
        secureRandom.nextBytes(randomBytes);
        // URL에 안전한 Base64 형태로 인코딩하여 반환
        return base64Encoder.encodeToString(randomBytes);
    }

    public static void main(String[] args) {
        // 32바이트 크기의 보안 토큰 생성 (약 43자리의 문자열)
        String secureToken = generateToken(32);
        System.out.println("보안성 높은 토큰: " + secureToken);
    }
}

