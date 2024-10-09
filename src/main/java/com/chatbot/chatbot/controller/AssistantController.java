package com.chatbot.chatbot.controller;

import com.chatbot.chatbot.dto.RestResponseDTO;
import com.chatbot.chatbot.service.AssistantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Obtem uma resposta dando incio a conversa", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao obter a resposta"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a pergunta")
    })
    @GetMapping("/ask")
    public RestResponseDTO sendQuestion(
            @RequestParam @Valid @NotNull(message = "A pergunta é obrigatória") @NotBlank(message = "A pergunta é obrigatória") String question) {
        RestResponseDTO restResponseDTO = assistantService.sendQuestion(question);
        return ResponseEntity.status(200).body(restResponseDTO).getBody();
    }

    @Operation(summary = "Obtem uma resposta com uma conversa em andamento", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao obter a resposta"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a pergunta")
    })
    @GetMapping("/askWithChat")
    public ResponseEntity<RestResponseDTO> sendQuestion(
            @RequestParam @Valid @NotNull(message = "A pergunta é obrigatória") @NotBlank(message = "A pergunta é obrigatória") String question,
            @RequestParam(value = "id") UUID idChat) {

        RestResponseDTO restResponseDTO = assistantService.sendQuestion(question, idChat);
        return ResponseEntity.ok(restResponseDTO);
    }
}
