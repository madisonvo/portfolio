/*
 * File: Question.java
 * Author: Madison Vo
 * Purpose: The Question class represents an entity/table within the quizdatabase. It contains
 *          the fields int questionId (the primary key), int quizId (foreign key referencing a
 *          quiz), String difficulty (the difficulty level for the quiz), String question (the
 *          text question). Getter and setters have been generated for each field.
 */

package com.interactive_quiz_game.spring_boot_quiz.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Question")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionId")
    private int questionId;

    @Column(name = "quizId")
    private int quizId;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "question")
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
