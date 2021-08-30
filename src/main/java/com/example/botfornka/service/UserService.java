package com.example.botfornka.service;

import com.example.botfornka.model.User;
import com.example.botfornka.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void saveUserIfNotExistFromMessageInfo(Message message) {
        if (!isExistByChatId(message.getChatId())) {
            saveUser(generateUserFromMessage(message));
        }
    }

    private User generateUserFromMessage(Message message) {
        return new User(message.getChatId(), message.getChat().getUserName());
    }

    public boolean isExistByChatId(Long chatId) {
        return userRepository.findByChatId(chatId) != null;
    }
}