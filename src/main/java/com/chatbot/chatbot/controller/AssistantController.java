package com.chatbot.chatbot.controller;

import com.chatbot.chatbot.dto.DataResponseDTO;
import com.chatbot.chatbot.dto.EvaluateAnswerRecordDTO;
import com.chatbot.chatbot.dto.QuestionRecordDTO;
import com.chatbot.chatbot.dto.RestResponseDTO;
import com.chatbot.chatbot.service.AssistantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @PostMapping("/ask")
    public RestResponseDTO sendQuestion(@RequestBody @Valid QuestionRecordDTO questionRecordDTO) {
        RestResponseDTO restResponseDTO = assistantService.sendQuestion(questionRecordDTO);
        return ResponseEntity.status(200).body(restResponseDTO).getBody();
    }

    @Operation(summary = "Avaliar resposta do chatbot", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao registrar o feedback"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos ou ausentes"),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao processar a avaliação")
    })
    @PutMapping("/evaluate-answer/{id}")
    public ResponseEntity<RestResponseDTO> evaluateAnswer(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid EvaluateAnswerRecordDTO evaluateAnswerRecordDTO) {

        assistantService.evaluateAnswer(id, evaluateAnswerRecordDTO);
        DataResponseDTO dataResponseDTO = new DataResponseDTO();
        dataResponseDTO.setMessage("Avaliação registrada com sucesso");
        return ResponseEntity.ok(new RestResponseDTO(true, dataResponseDTO));
    }
}
