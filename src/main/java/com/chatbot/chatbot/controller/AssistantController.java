package com.chatbot.chatbot.controller;

import com.chatbot.chatbot.dto.ChatResponseDTO;
import com.chatbot.chatbot.dto.RestResponseDTO;
import com.chatbot.chatbot.service.AssistantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot")
@Tag(name = "Chatbot")
public class AssistantController {

    @Autowired
    private AssistantService assistantService;

    //TODO - Adicionar documentação Swagger para o método abaixo
    @GetMapping("/ask")
    public RestResponseDTO sendQuestion(@RequestParam(value = "message", defaultValue = "") String question) {
        RestResponseDTO restResponseDTO = assistantService.sendQuestion(question);
        return ResponseEntity.status(200).body(restResponseDTO).getBody();
    }
}
