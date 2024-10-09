package com.chatbot.chatbot.exception;

import com.chatbot.chatbot.dto.ErrorMessageDTO;
import com.chatbot.chatbot.dto.RestResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<RestResponseDTO> exceptionHandler(Exception ex){
        RestResponseDTO restResponseDTO = new RestResponseDTO(
                new ErrorMessageDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                false
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restResponseDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<RestResponseDTO> runtimeExceptionHandler(RuntimeException ex){
        RestResponseDTO restResponseDTO = new RestResponseDTO(
                new ErrorMessageDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                false
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restResponseDTO);
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<RestResponseDTO> fileProcessingExceptionHandler(FileProcessingException ex) {
        RestResponseDTO restResponseDTO = new RestResponseDTO(
                new ErrorMessageDTO(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()),
                false
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(restResponseDTO);
    }

    @ExceptionHandler(DocumentParsingException.class)
    public ResponseEntity<RestResponseDTO> documentParsingExceptionHandler(DocumentParsingException ex) {
        RestResponseDTO restResponseDTO = new RestResponseDTO(
                new ErrorMessageDTO(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()),
                false
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(restResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponseDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        RestResponseDTO restResponseDTO = new RestResponseDTO(
                new ErrorMessageDTO(HttpStatus.BAD_REQUEST, ex.getMessage()),
                false
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponseDTO);
    }

    @ExceptionHandler(LlmServiceException.class)
    public ResponseEntity<RestResponseDTO> llmServiceExceptionHandler(LlmServiceException ex) {
        RestResponseDTO restResponseDTO = new RestResponseDTO(
                new ErrorMessageDTO(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()),
                false
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(restResponseDTO);
    }
}
