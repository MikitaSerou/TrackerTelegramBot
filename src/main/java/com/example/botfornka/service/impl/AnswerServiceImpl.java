package com.example.botfornka.service.impl;

import com.example.botfornka.enums.Command;
import com.example.botfornka.service.AnswerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class AnswerServiceImpl implements AnswerService {

    public SendMessage GetMessageAndDoAnswerByReceivedText(Long chatId, String text) {
        try {
            return chooseAnswerByCommand(chatId, Command.getByText(text));
        } catch (Exception e) {
            return getWrongCommandMessage(chatId);
        }
    }

    @Override
    public SendMessage chooseAnswerByCommand(Long chatId, Command command) {
        return switch (command) {
            case START -> doStartTrackingAnswer(chatId);
            case STOP -> doStopTrackingAnswer(chatId);
            case PING -> doPingAnswer(chatId);
            case STATUS -> doCurrentStatusAnswer(chatId);
        };

    }

    @Override
    public SendMessage doStartTrackingAnswer(Long chatId) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        builder.text("You started tracking server state").chatId(String.valueOf(chatId));
        return builder.build();
    }

    @Override
    public SendMessage doStopTrackingAnswer(Long chatId) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        builder.text("You started tracking server state").chatId(String.valueOf(chatId));
        return builder.build();
    }

    @Override
    public SendMessage doPingAnswer(Long chatId) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        builder.text("You started tracking server state").chatId(String.valueOf(chatId));
        return builder.build();
    }

    @Override
    public SendMessage doCurrentStatusAnswer(Long chatId) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        builder.text("You started tracking server state").chatId(String.valueOf(chatId));
        return builder.build();
    }

    @Override
    public SendMessage getWrongCommandMessage(Long chatId) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        builder.text("Not allowed command. Please try again with one from list.").chatId(String.valueOf(chatId));
        return builder.build();
    }
}