package com.example.botfornka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BotForNkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotForNkaApplication.class, args);
    }
}
