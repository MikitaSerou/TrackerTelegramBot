package com.example.botfornka.service.impl;

import com.example.botfornka.enums.Command;
import com.example.botfornka.model.User;
import com.example.botfornka.service.AnswerService;
import com.example.botfornka.service.RestService;
import com.example.botfornka.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final UserService userService;
    private final RestService restService;

    @Autowired
    public AnswerServiceImpl(UserService userService, RestService restService) {
        this.userService = userService;
        this.restService = restService;
    }

    public SendMessage GetMessageAndDoAnswerByReceivedText(Message message) {
        try {
            return chooseAnswerByCommand(message, Command.getByText(message.getText()));
        } catch (Exception e) {
            return getWrongCommandMessage(message.getChatId());
        }
    }

    @Override
    public SendMessage chooseAnswerByCommand(Message message, Command command) {
        return switch (command) {
            case START -> doStartTrackingAnswer(message);
            case STOP -> doStopTrackingAnswer(message.getChatId());
            case PING -> doPingAnswer(message.getChatId());
            case STATUS -> doCurrentStatusAnswer(message.getChatId());
        };

    }

    @Override
    public SendMessage doStartTrackingAnswer(Message message) {
        userService.startUserSubscription(message);
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        return builder.text(EmojiParser.parseToUnicode(":bulb: You started tracking server state"))
                .chatId(String.valueOf(message.getChatId())).build();
    }

    @Override
    public SendMessage doStopTrackingAnswer(Long chatId) {
        userService.setUserSubscriptionStatus(userService.getUserByChatId(chatId), false);
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        return builder.text(EmojiParser.parseToUnicode(":stop_sign: Tracking is stopped"))
                .chatId(String.valueOf(chatId)).build();
    }

    @Override
    public SendMessage doPingAnswer(Long chatId) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        builder.text(restService.receiveDefaultResponseSummary()).chatId(String.valueOf(chatId));
        return builder.build();
    }

    @Override
    public SendMessage doCurrentStatusAnswer(Long chatId) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        User currentUser = userService.getUserByChatId(chatId);
        builder.text(getTextAccordingToStatus(userService.getUserSubscriptionStatus(currentUser)))
                .chatId(String.valueOf(chatId));
        return builder.build();
    }

    private String getTextAccordingToStatus(Boolean status) {
        return status ? EmojiParser.parseToUnicode(":green_book: You are tracking current server statement")
                : EmojiParser.parseToUnicode(":closed_book: You are not tracking current server statement");
    }

    @Override
    public SendMessage getWrongCommandMessage(Long chatId) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        builder.text("Not allowed command. Please try again with one from list.").chatId(String.valueOf(chatId));
        return builder.build();
    }
}