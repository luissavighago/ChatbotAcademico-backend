package com.chatbot.chatbot.controller;

import com.chatbot.chatbot.dto.QuestionRecordDTO;
import com.chatbot.chatbot.dto.RestResponseDTO;
import com.chatbot.chatbot.service.PromptsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompts")
@Tag(name = "Prompts")
public class PromptController {

    @Autowired
    private PromptsService promptsService;

    @Operation(
            summary = "Realiza uma pergunta utilizando diferentes prompts, retornando uma lista de respostas para cada prompt",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao obter a resposta"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a pergunta")
    })
    @PostMapping("/ask")
    public RestResponseDTO sendQuestion(@RequestBody @Valid QuestionRecordDTO questionRecordDTO) {
        RestResponseDTO restResponseDTO = promptsService.askWithPrompts(questionRecordDTO);
        return ResponseEntity.status(200).body(restResponseDTO).getBody();
    }
}
