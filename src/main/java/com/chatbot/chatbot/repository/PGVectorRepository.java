package com.chatbot.chatbot.repository;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PGVectorRepository {

    private final VectorStore vectorStore;

    @Autowired
    public PGVectorRepository(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
        this.vectorStore = new PgVectorStore(jdbcTemplate, embeddingModel);
    }

    public void add(List<Document> documentList) {
        vectorStore.add(documentList);
    }
}
