package com.example.botfornka.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface AnswerService {

    SendMessage doAnswerForUser(Update update);

    SendMessage getMessageAndDoAnswer(Message message);

    SendMessage doStartTrackingAnswer(Message message);

    SendMessage doStopTrackingAnswer(Long chatId);

    SendMessage doPingAnswer(Long chatId);

    SendMessage doTrackingStatusAnswer(Long chatId);

    SendMessage doWrongCommandAnswer(Long chatId);

    SendMessage getServerNotAllowedMessage(Long chatId, Exception exception);

    SendMessage doUnknownErrorAnswer(Long chatId);

    SendMessage getRestoredMailingMessage(Long chatId);

    SendMessage getLostMailingMessage(Long chatId);

    SendMessage doMailingAnswer(Message message);
}

