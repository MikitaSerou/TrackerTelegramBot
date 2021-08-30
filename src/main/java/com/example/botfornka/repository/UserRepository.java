package com.example.botfornka.repository;

import com.example.botfornka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByChatId(Long chatId);
}
