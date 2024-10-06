package com.chatbot.chatbot.controller;

import com.chatbot.chatbot.dto.RestResponseDTO;
import com.chatbot.chatbot.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot")
@Tag(name = "Chatbot")
public class AssistantController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/ask")
    public RestResponseDTO sendQuestion(@RequestParam(value = "message", defaultValue = "") String question) {
        chatService.sendQuestion(question);
        return null;
    }
}
