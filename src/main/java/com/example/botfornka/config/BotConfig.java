package com.example.botfornka.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BotConfig {

    @Value("${botUserName}")
    private String botUserName;

    @Value("${token}")
    private String token;

}
