package com.example.botfornka;

import com.example.botfornka.config.BotConfig;
import com.example.botfornka.service.AnswerService;
import com.example.botfornka.service.RestService;
import com.example.botfornka.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class Bot extends TelegramLongPollingBot {

    @Value("${tracked.url}")
    private String TRACKED_DEFAULT_URL;

    private final BotConfig config;
    private final RestService restService;
    private final UserService userServiceImpl;
    private final AnswerService messageServiceImpl;

    @Autowired
    public Bot(BotConfig config, RestService urlSender, UserService userServiceImpl, AnswerService messageServiceImpl) {
        this.config = config;
        this.restService = urlSender;
        this.userServiceImpl = userServiceImpl;
        this.messageServiceImpl = messageServiceImpl;
    }

    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage;
        if (update.getMessage() != null) {
            sendMessage = messageServiceImpl.GetMessageAndDoAnswerByReceivedText(chatId, update.getMessage().getText());
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            executeMessageSending(sendMessage);
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
