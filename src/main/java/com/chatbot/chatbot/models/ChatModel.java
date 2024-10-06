package com.chatbot.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_chat")
@Getter
@Setter
public class ChatModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idChat;
    private LocalDateTime dhChat;

    @PrePersist
    protected void onCreate() {
        dhChat = LocalDateTime.now();
    }
}
