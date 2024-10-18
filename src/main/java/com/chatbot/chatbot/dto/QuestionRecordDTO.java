package com.chatbot.chatbot.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record QuestionRecordDTO(
        UUID idChat,
        @NotBlank(message = "A pergunta é obrigatória") String question,
        String apiKey
) {}
