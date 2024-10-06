package com.chatbot.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessageDTO {
    private HttpStatus status;
    private String err;
}
