package com.chatbot.chatbot.controller;

import com.chatbot.chatbot.dto.DataResponseDTO;
import com.chatbot.chatbot.dto.RestResponseDTO;
import com.chatbot.chatbot.service.EmbeddingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/regulation")
@Tag(name = "Regulamentos")
public class RegulationsController {

    @Autowired
    private EmbeddingService embeddingService;

    @Operation(summary = "Armazena os regulamentos de estágíos utilizados como contexto para o chatbot", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regulamento armazenado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao armazenar o regulamento")
    })
    @PostMapping("/load")
    public ResponseEntity<RestResponseDTO> loadFile() {
        embeddingService.load();

        DataResponseDTO dataResponseDTO = new DataResponseDTO();
        dataResponseDTO.setMessage("Regulamento armazenado com sucesso");

        return ResponseEntity.status(200).body(new RestResponseDTO(true, dataResponseDTO));
    }
}
