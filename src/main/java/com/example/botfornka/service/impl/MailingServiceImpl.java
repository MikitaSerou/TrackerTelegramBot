package com.example.botfornka.service.impl;

import com.example.botfornka.Bot;
import com.example.botfornka.model.User;
import com.example.botfornka.service.MailingService;
import com.example.botfornka.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@Slf4j
public class MailingServiceImpl implements MailingService {

    private final UserService userService;
    private final Bot bot;

    @Autowired
    public MailingServiceImpl(UserService userService, Bot bot) {
        this.userService = userService;
        this.bot = bot;
    }

    @Override
    public void sendRestoredConnectionMailing() {
        log.info("---------Start mailing restored connection message---------");
        List<User> activeUsers = userService.findAllByStatus(true);
        for (User user : activeUsers) {
            log.info("Send message for user " + user.getChatId());
            bot.onUpdateReceived(generateMailingUpdate(user, "restore"));
        }
    }

    @Override
    public void sendLostConnectingMailing() {
        log.info("---------Start mailing lost connection message---------");
        List<User> activeUsers = userService.findAllByStatus(true);
        for (User user : activeUsers) {
            log.info("Send message for user " + user.getChatId());
            bot.onUpdateReceived(generateMailingUpdate(user, "lost"));
        }
    }

    private Update generateMailingUpdate(User user, String keyWord) {
        Update update = new Update();
        update.setMessage(createMessage(keyWord));
        update.getMessage().setChat(createChat(user));
        return update;
    }

    private Message createMessage(String keyWord) {
        Message message = new Message();
        message.setText(keyWord);
        message.setAuthorSignature("ThisBot");
        return message;
    }

    private Chat createChat(User user) {
        Chat chat = new Chat();
        chat.setId(user.getChatId());
        return chat;
    }
}
