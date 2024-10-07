package com.chatbot.chatbot.repository;

import com.chatbot.chatbot.models.ResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResponseRepository extends JpaRepository<ResponseModel, UUID> {
}
