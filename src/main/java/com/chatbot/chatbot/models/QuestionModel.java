package com.chatbot.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_question")
@Getter
@Setter
public class QuestionModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idQuestion;
    private String question;
    private LocalDateTime dhQuestion;

    @ManyToOne
    @JoinColumn(name = "chat", nullable = false)
    private ChatModel chat;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private ResponseModel response;

    @PrePersist
    protected void onCreate() {
        dhQuestion = LocalDateTime.now();
    }
}
