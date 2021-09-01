package com.example.botfornka.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface AnswerService {

    SendMessage getMessageAndDoAnswer(Message message);

    SendMessage doStartTrackingAnswer(Message message);

    SendMessage doStopTrackingAnswer(Long chatId);

    SendMessage doPingAnswer(Long chatId);

    SendMessage doTrackingStatusAnswer(Long chatId);

    SendMessage getWrongCommandMessage(Long chatId);

    SendMessage getServerNotAllowedMessage(Long chatId, Exception exception);

    SendMessage doUnknownErrorAnswer(Long chatId);
}

