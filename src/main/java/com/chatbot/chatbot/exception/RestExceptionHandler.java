package com.chatbot.chatbot.exception;

import com.chatbot.chatbot.dto.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorMessageDTO> exceptionHandler(Exception ex){
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ErrorMessageDTO> runtimeExceptionHandler(RuntimeException ex){
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageDTO);
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ErrorMessageDTO> fileProcessingExceptionHandler(FileProcessingException ex) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DocumentParsingException.class)
    public ResponseEntity<ErrorMessageDTO> documentParsingExceptionHandler(DocumentParsingException ex) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
