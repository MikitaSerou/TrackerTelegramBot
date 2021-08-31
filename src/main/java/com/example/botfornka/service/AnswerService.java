package com.example.botfornka.service;

import com.example.botfornka.enums.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface AnswerService {

    SendMessage GetMessageAndDoAnswerByReceivedText(Message message);

    SendMessage chooseAnswerByCommand(Message message, Command command);

    SendMessage doStartTrackingAnswer(Message message);

    SendMessage doStopTrackingAnswer(Long chatId);

    SendMessage doPingAnswer(Long chatId);

    SendMessage doCurrentStatusAnswer(Long chatId);

    SendMessage getWrongCommandMessage(Long chatId);
}

