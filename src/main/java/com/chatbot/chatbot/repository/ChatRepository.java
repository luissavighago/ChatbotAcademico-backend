package com.chatbot.chatbot.repository;

import com.chatbot.chatbot.models.ChatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<ChatModel, UUID>{
}
