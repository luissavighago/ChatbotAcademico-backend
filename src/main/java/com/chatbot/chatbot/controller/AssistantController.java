package com.chatbot.chatbot.controller;

import com.chatbot.chatbot.dto.RestResponseDTO;
import com.chatbot.chatbot.service.AssistantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/chatbot")
@Tag(name = "Chatbot")
public class AssistantController {

    @Autowired
    private AssistantService assistantService;

    //TODO - Adicionar documentação Swagger para o método abaixo
    @GetMapping("/ask")
    public RestResponseDTO sendQuestion(
            @RequestParam @Valid @NotNull(message = "A pergunta é obrigatória") @NotBlank(message = "A pergunta é obrigatória") String question) {
        RestResponseDTO restResponseDTO = assistantService.sendQuestion(question);
        return ResponseEntity.status(200).body(restResponseDTO).getBody();
    }

    //TODO - Adicionar documentação Swagger para o método abaixo
    @GetMapping("/askWithChat")
    public ResponseEntity<RestResponseDTO> sendQuestion(
            @RequestParam @Valid @NotNull(message = "A pergunta é obrigatória") @NotBlank(message = "A pergunta é obrigatória") String question,
            @RequestParam(value = "id") UUID idChat) {

        RestResponseDTO restResponseDTO = assistantService.sendQuestion(question, idChat);
        return ResponseEntity.ok(restResponseDTO);
    }
}
