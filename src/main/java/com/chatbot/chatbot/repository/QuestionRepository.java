package com.chatbot.chatbot.repository;

import com.chatbot.chatbot.models.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionModel, UUID> {
}
