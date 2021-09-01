package com.example.botfornka.service.impl;

import com.example.botfornka.enums.Command;
import com.example.botfornka.model.User;
import com.example.botfornka.service.AnswerService;
import com.example.botfornka.service.RestService;
import com.example.botfornka.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final UserService userService;
    private final RestService restService;

    @Autowired
    public AnswerServiceImpl(UserService userService, RestService restService) {
        this.userService = userService;
        this.restService = restService;
    }

    public SendMessage doAnswerForUser(Update update) {
        SendMessage sendMessage = getMessageAndDoAnswer(update.getMessage());
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        return sendMessage;
    }

    @Override
    public SendMessage getMessageAndDoAnswer(Message message) {
        try {
            return chooseAnswerByCommand(message, Command.getByText(message.getText()));
        } catch (Exception exception) {
            return getHandledExceptionAnswer(exception, message);
        }
    }

    private SendMessage getHandledExceptionAnswer(Exception exception, Message message) {
        if (IllegalArgumentException.class.equals(exception.getClass())) {
            return doWrongCommandAnswer(message.getChatId());
        } else if (ResourceAccessException.class.equals(exception.getClass())) {
            return getServerNotAllowedMessage(message.getChatId(), exception);
        } else {
            return doUnknownErrorAnswer(message.getChatId());
        }
    }

    @Override
    public SendMessage doUnknownErrorAnswer(Long chatId) {
        return buildMessage(chatId,
                EmojiParser.parseToUnicode("Unknown error"));
    }

    private SendMessage chooseAnswerByCommand(Message message, Command command) {
        return switch (command) {
            case START -> doStartTrackingAnswer(message);
            case STOP -> doStopTrackingAnswer(message.getChatId());
            case PING -> doPingAnswer(message.getChatId());
            case STATUS -> doTrackingStatusAnswer(message.getChatId());
        };
    }

    public SendMessage doMailingAnswer(Message message) {
        return switch (message.getText()) {
            case "restore" -> getRestoredMailingMessage(message.getChatId());
            case "lost" -> getLostMailingMessage(message.getChatId());
            default -> buildMessage(message.getChatId(), "Have a good day)");
        };
    }

    @Override
    public SendMessage doStartTrackingAnswer(Message message) {
        userService.startUserSubscription(message);
        return buildMessage(message.getChatId(),
                EmojiParser.parseToUnicode(":bulb: You started tracking server state by URL:\n" +
                        restService.getTRACKED_DEFAULT_URL()));
    }

    @Override
    public SendMessage doStopTrackingAnswer(Long chatId) {
        userService.setUserSubscriptionStatus(userService.getUserByChatId(chatId), false);
        return buildMessage(chatId, EmojiParser.parseToUnicode(":stop_sign: Tracking is stopped"));
    }

    @Override
    public SendMessage doPingAnswer(Long chatId) {
        return buildMessage(chatId, restService.receiveDefaultResponseSummary());
    }

    @Override
    public SendMessage doTrackingStatusAnswer(Long chatId) {
        if (userService.isExistByChatId(chatId)) {
            User currentUser = userService.getUserByChatId(chatId);
            return buildMessage(chatId, getTextAccordingToStatus(userService.getUserSubscriptionStatus(currentUser)));
        }
        return buildMessage(chatId, getTextAccordingToStatus(false));
    }

    private String getTextAccordingToStatus(Boolean status) {
        return status ? EmojiParser.parseToUnicode(":green_book: You are tracking current server statement by URL:\n" +
                restService.getTRACKED_DEFAULT_URL())
                : EmojiParser.parseToUnicode(":closed_book: You are not tracking current server statement");
    }

    @Override
    public SendMessage doWrongCommandAnswer(Long chatId) {
        return buildMessage(chatId,
                EmojiParser.parseToUnicode(":warning: Not allowed command. Please try again with one from list."));
    }

    @Override
    public SendMessage getServerNotAllowedMessage(Long chatId, Exception exception) {
        return buildMessage(chatId,
                EmojiParser.parseToUnicode(":zap::zap::zap:Can not connect to server!:zap::zap::zap:\n" +
                        restService.getTRACKED_DEFAULT_URL() + "\n\n"
                        + exception.getLocalizedMessage()));
    }

    @Override
    public SendMessage getRestoredMailingMessage(Long chatId) {
        return buildMessage(chatId,
                EmojiParser.parseToUnicode(":sparkle:SERVER CONNECTION RESTORED:sparkle:"));
    }

    @Override
    public SendMessage getLostMailingMessage(Long chatId) {
        return buildMessage(chatId,
                EmojiParser.parseToUnicode(":exclamation:LOST SERVER CONNECTION:exclamation:"));
    }

    private SendMessage buildMessage(Long chatId, String text) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        return builder.text(text).chatId(String.valueOf(chatId)).build();
    }
}