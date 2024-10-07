package com.chatbot.chatbot.repository;

import com.chatbot.chatbot.models.AnswerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnswerRepository extends JpaRepository<AnswerModel, UUID> {
}
