package com.example.botfornka;

import com.example.botfornka.config.BotConfig;
import com.example.botfornka.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class Bot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final AnswerService answerService;

    @Autowired
    public Bot(BotConfig config, AnswerService answerService) {
        this.config = config;
        this.answerService = answerService;
    }

    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            executeMessageSending(getDefinedUpdate(update));
        }
    }

    private SendMessage getDefinedUpdate(Update update) {
        if (update.getMessage().getAuthorSignature() != null && update.getMessage().getAuthorSignature().equals("ThisBot")) {
            return answerService.doMailingAnswer(update.getMessage());
        } else {
            return answerService.doAnswerForUser(update);
        }
    }

    private void executeMessageSending(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBOT_NAME();
    }

    @Override
    public String getBotToken() {
        return config.getTOKEN();
    }
}
