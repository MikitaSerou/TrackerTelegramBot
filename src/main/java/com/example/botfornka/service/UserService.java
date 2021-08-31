package com.example.botfornka.service;

import com.example.botfornka.model.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface UserService {

    void saveUser(User user);

    User getUserByChatId(Long chatId);

    void saveUserIfNotExistFromMessageInfo(Message message);

    boolean isExistByChatId(Long chatId);

    void setUserSubscriptionStatus(User user, Boolean isSubscribe);

    Boolean getUserSubscriptionStatus(User user);
}
