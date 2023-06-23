package com.kk.jjiiim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
public class JjiiimApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjiiimApplication.class, args);
    }

}
