package com.chatbot.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatResponseDTO extends DataResponseDTO{
    private UUID idChat;
    private UUID idQuestion;
    private UUID idAnswer;
    private String answer;
}
