package com.cmms4.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * CMMS4 - PasswordGenerator
 * 비밀번호 해시 생성 유틸리티
 * 
 * @author cmms4
 * @since 2024-03-19
 */
public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "1234";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Encoded Password: " + encodedPassword);
    }
} 