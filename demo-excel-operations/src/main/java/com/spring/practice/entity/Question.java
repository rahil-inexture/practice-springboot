package com.spring.practice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "tbl_question")
@AllArgsConstructor
@NoArgsConstructor
public class Question extends DateAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "question_id")
    private Long questionId;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "quiz_id")
    private Long quizId;
    @Column(name = "question", columnDefinition = "TEXT")
    private String question;
    @Column(name = "options", columnDefinition = "TEXT")
    private String options;
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    public enum QuestionType {
        CHECKBOX, MULTIPLE_CHOICE, TEXT, LIKE_DISLIKE
    }
}


