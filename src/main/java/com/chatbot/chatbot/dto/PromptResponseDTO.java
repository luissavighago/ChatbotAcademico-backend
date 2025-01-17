package com.chatbot.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromptResponseDTO extends DataResponseDTO {
    private UUID idChat;
    private UUID idQuestion;
    private List<AnswerResponseDTO> answers;
}
