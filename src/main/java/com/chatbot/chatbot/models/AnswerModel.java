package com.chatbot.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_answer")
@Getter
@Setter
public class AnswerModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAnswer;

    @OneToOne
    @JoinColumn(name = "idQuestion", nullable = false)
    private QuestionModel question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    @Column(nullable = false, length = 1)
    private char status = 'P';

    @Column(name = "dhResponse", nullable = false, updatable = false)
    private LocalDateTime dhResponse;

    @PrePersist
    protected void onCreate() {dhResponse = LocalDateTime.now();}
}
