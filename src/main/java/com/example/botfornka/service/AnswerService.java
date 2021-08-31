package com.example.botfornka.service;

import com.example.botfornka.enums.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerService {

    SendMessage GetMessageAndDoAnswerByReceivedText(Long chatId, String text);

    SendMessage chooseAnswerByCommand(Long chatId, Command command);

    SendMessage doStartTrackingAnswer(Long chatId);

    SendMessage doStopTrackingAnswer(Long chatId);

    SendMessage doPingAnswer(Long chatId);

    SendMessage doCurrentStatusAnswer(Long chatId);

    SendMessage getWrongCommandMessage(Long chatId);
}

