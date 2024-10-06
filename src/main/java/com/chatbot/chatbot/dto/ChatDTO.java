package com.chatbot.chatbot.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ChatDTO {
    private UUID idChat;
    private LocalDateTime dhChat;
}
