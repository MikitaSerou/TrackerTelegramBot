package com.example.botfornka.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(exclude = {"id"})
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "chat_id", unique = true)
    @NonNull
    Long chatId;

    @Column(name = "user_name")
    @NonNull
    String userName;

    @Column(name = "is_active", nullable = false)
    @NonNull
    Boolean isActive;

    public User(@NonNull Long chatId, @NonNull Boolean isActive) {
        this.chatId = chatId;
        this.isActive = isActive;
    }
}
