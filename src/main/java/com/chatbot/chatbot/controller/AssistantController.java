package com.chatbot.chatbot.controller;

import com.chatbot.chatbot.service.EmbeddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
public class AssistantController {

    @Autowired
    private EmbeddingService embeddingService;

    @PostMapping("/file")
    public ResponseEntity<Object> loadFile() throws Exception {
        embeddingService.load();
        return ResponseEntity.status(200).body("File loaded successfully");
    }
}
