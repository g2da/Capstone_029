package com.example.abeec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ABeeCApplication {

    public static void main(String[] args) {
        SpringApplication.run(ABeeCApplication.class, args);
    }

}
