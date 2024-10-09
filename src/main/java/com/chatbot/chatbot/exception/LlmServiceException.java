package com.chatbot.chatbot.exception;

public class LlmServiceException extends RuntimeException{

    public LlmServiceException(String message) {
        super(message);
    }
}
