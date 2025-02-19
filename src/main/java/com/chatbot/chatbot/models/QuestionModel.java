package com.chatbot.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDateTime dhQuestion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @ManyToOne
    @JoinColumn(name = "chat", nullable = false)
    private ChatModel chat;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AnswerModel> answers;

    @PrePersist
    protected void onCreate() {
        dhQuestion = LocalDateTime.now();
    }
}
