package com.example.botfornka.repository;

import com.example.botfornka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByChatId(Long chatId);

    @Query(value = "SELECT is_active FROM users WHERE chat_id = ?", nativeQuery = true)
    Boolean getUserStatus(Long chatId);

    List<User> findAllByIsActive(Boolean isActive);
}
