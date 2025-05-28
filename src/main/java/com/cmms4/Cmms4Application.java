package com.cmms4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * cmms4 - Cmms4Application
 * cmms4 애플리케이션의 메인 클래스
 * 
 * @author cmms4
 * @since 2024-03-19
 */
@SpringBootApplication
@EntityScan(basePackages = "com.cmms4")
@EnableJpaRepositories(basePackages = "com.cmms4")
public class Cmms4Application {

    public static void main(String[] args) {
        SpringApplication.run(Cmms4Application.class, args);
    }
} 