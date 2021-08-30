package com.example.botfornka.service;

import com.example.botfornka.model.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface UserService {

    void saveUser(User user);

    void saveUserIfNotExistFromMessageInfo(Message message);

    boolean isExistByChatId(Long chatId);
}
