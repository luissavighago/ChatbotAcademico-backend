package com.chatbot.chatbot.controller;

import org.springframework.ai.chat.model.Generation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot")
public class AssistantController {

    @GetMapping("/ask")
    public Generation sendMessage(@RequestParam(value = "message", defaultValue = "") String message) {
        //TODO - Implementar endpoint
        return null;
    }
}
