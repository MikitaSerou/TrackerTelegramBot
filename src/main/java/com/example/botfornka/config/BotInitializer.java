package com.example.botfornka.config;

import com.example.botfornka.service.Bot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BotInitializer {

    @Autowired
    private Bot bot;

}
