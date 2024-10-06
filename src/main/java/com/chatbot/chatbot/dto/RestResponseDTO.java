package com.chatbot.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponseDTO {
    private ErrorMessageDTO error;
    private boolean success;
    private Object data;

    public RestResponseDTO(ErrorMessageDTO errorMessageDTO, boolean success, Object data) {
        this.error = errorMessageDTO;
        this.success = success;
        this.data = data;
    }

    public RestResponseDTO(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public RestResponseDTO(ErrorMessageDTO errorMessageDTO, boolean success) {
        this.error = errorMessageDTO;
        this.success = success;
    }
}
