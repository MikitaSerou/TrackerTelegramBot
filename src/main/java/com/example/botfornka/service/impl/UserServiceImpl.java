package com.example.botfornka.service.impl;

import com.example.botfornka.model.User;
import com.example.botfornka.repository.UserRepository;
import com.example.botfornka.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveUserIfNotExistFromMessageInfo(Message message) {
        if (!isExistByChatId(message.getChatId())) {
            saveUser(generateUserFromMessage(message));
        }
    }

    private User generateUserFromMessage(Message message) {
        return new User(message.getChatId(), message.getChat().getUserName(), true);
    }

    @Override
    public boolean isExistByChatId(Long chatId) {
        return userRepository.findByChatId(chatId) != null;
    }
}