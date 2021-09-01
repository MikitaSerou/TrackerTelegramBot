package com.example.botfornka.service.impl;

import com.example.botfornka.Bot;
import com.example.botfornka.model.User;
import com.example.botfornka.service.AnswerService;
import com.example.botfornka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class MailingService {

    @Autowired
    private UserService userService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private Bot bot;

    public void sendRestoredConnectionMailing() {
        List<User> activeUsers = userService.findAllByStatus(true);
        for (User user : activeUsers) {
            Update update = new Update();
            Message message = new Message();
            message.setText("restore");
            message.setAuthorSignature("ThisBot");
            update.setMessage(message);
            Chat chat = new Chat();
            chat.setId(user.getChatId());
            update.getMessage().setChat(chat);
            bot.onUpdateReceived(update);
        }
        System.err.println("GOOD");
    }

    public void sendLostConnectingMailing() {
        List<User> activeUsers = userService.findAllByStatus(true);
        for (User user : activeUsers) {
            Update update = new Update();
            Message message = new Message();
            message.setText("lost");
            message.setAuthorSignature("ThisBot");
            update.setMessage(message);
            Chat chat = new Chat();
            chat.setId(user.getChatId());
            update.getMessage().setChat(chat);
            bot.onUpdateReceived(update);
        }
        System.err.println("BAD");
    }

}
