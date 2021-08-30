package com.example.botfornka;

import com.example.botfornka.config.BotConfig;
import com.example.botfornka.service.MessageService;
import com.example.botfornka.service.RestService;
import com.example.botfornka.service.UserService;
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
    private final RestService restService;
    private final UserService userServiceImpl;
    private final MessageService messageServiceImpl;

    @Autowired
    public Bot(BotConfig config, RestService urlSender, UserService userServiceImpl, MessageService messageServiceImpl) {
        this.config = config;
        this.restService = urlSender;
        this.userServiceImpl = userServiceImpl;
        this.messageServiceImpl = messageServiceImpl;
    }

    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        String messageText;
        String chatId;

        if (update.getMessage() != null) {
            userServiceImpl.saveUserIfNotExistFromMessageInfo(update.getMessage());

            chatId = update.getMessage().getChatId().toString();
            update.getMessage().getAuthorSignature();
            builder.chatId(chatId);
            messageText = update.getMessage().getText();
        } else {
            chatId = update.getChannelPost().getChatId().toString();
            builder.chatId(chatId);
            messageText = update.getChannelPost().getText();
        }

        if (messageText.contains("/hello")) {
            builder.text(restService.receiveGetResponseSummaryByUrl("https://www.nbrb.by/api/exrates/rates/840?parammode=1"));
            try {
                execute(builder.build());
            } catch (TelegramApiException e) {
                log.error(e.getLocalizedMessage());
            }
        }

        if (messageText.contains("/chatId")) {
            builder.text("Channel ID: " + chatId);
            try {
                execute(builder.build());
            } catch (TelegramApiException e) {
                log.error(e.getLocalizedMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
