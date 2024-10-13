package com.chatbot.chatbot.dto;

import jakarta.validation.constraints.NotBlank;

public record EvaluateAnswerRecordDTO(
        @NotBlank(message = "O status do feedback é obrigatório") String feedbackStatus
) {}
