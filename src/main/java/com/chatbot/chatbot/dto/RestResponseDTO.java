package com.chatbot.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponseDTO {
    private ErrorMessageDTO error;
    private boolean success;
    private DataResponseDTO data;

    public RestResponseDTO(boolean success, DataResponseDTO data) {
        this.success = success;
        this.data = data;
    }

    public RestResponseDTO(ErrorMessageDTO errorMessageDTO, boolean success) {
        this.error = errorMessageDTO;
        this.success = success;
    }
}
