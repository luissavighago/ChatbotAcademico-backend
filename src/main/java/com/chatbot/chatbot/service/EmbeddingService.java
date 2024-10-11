package com.chatbot.chatbot.service;

import com.chatbot.chatbot.exception.DocumentParsingException;
import com.chatbot.chatbot.exception.FileProcessingException;
import com.chatbot.chatbot.repository.PGVectorRepository;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmbeddingService {

    @Autowired
    private PGVectorRepository pgVectorRepository;

    public void load(){
        try {
            String content = new String(Files.readAllBytes(toPath("regulamento2.txt")));
            List<Document> documentList = setDocumentList(content);
            pgVectorRepository.add(documentList);
        } catch (IOException e) {
            throw new FileProcessingException("Erro ao carregar o regulamento de estágios", e);
        }
    }

    private List<Document> setDocumentList(String content) {
        try {
            //separa o trecho por capitulos
            String[] parts = content.split("_CAPITULO_");

            List<Document> documentList = new ArrayList<>();
            for (String part : parts) {
                if(part.trim().isEmpty()) continue;
                Document document = new Document(part.trim());
                documentList.add(document);
            }

            return documentList;
        } catch (Exception e) {
            throw new DocumentParsingException("Erro ao processar o conteúdo do regulamento", e);
        }
    }

    private static Path toPath(String fileName) {
        try {
            URL fileUrl = EmbeddingService.class.getClassLoader().getResource(fileName);
            return Paths.get(fileUrl.toURI());
        } catch (Exception e) {
            throw new FileProcessingException("Erro ao acessar o regulamento", e);
        }
    }
}
