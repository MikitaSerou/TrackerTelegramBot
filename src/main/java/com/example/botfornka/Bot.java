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
    private final AnswerService messageServiceImpl;

    @Autowired
    public Bot(BotConfig config, AnswerService messageServiceImpl) {
        this.config = config;
        this.messageServiceImpl = messageServiceImpl;
    }

    public void onUpdateReceived(Update update) {
        SendMessage sendMessage;
        if (update.getMessage() != null) {
            if (update.getMessage().getAuthorSignature() != null && update.getMessage().getAuthorSignature().equals("ThisBot")) {
                sendMessage = messageServiceImpl.doMailingAnswer(update.getMessage());
                executeMessageSending(sendMessage);
            } else {
                sendMessage = messageServiceImpl.getMessageAndDoAnswer(update.getMessage());
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                executeMessageSending(sendMessage);
            }
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
