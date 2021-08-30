package com.example.botfornka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@AutoConfigurationPackage
public class BotForNkaApplication/* extends SpringBootServletInitializer*/ {

    public static void main(String[] args) {
   //     new SpringApplicationBuilder(BotForNkaApplication.class).run(args);
        SpringApplication.run(BotForNkaApplication.class, args);
    }

}
