/*
 * File: Quiz.java
 * Author: Madison Vo
 * Purpose: The Quiz class represents an entity/table within the quizdatabase. It contains
 *          the fields int quizId (the primary key), int userId (foreign key referencing a
 *          user), String category (the category the quiz will be on), String difficulty
 *          (the difficulty level of the quiz) and int score (the current score of the quiz).
 *          Getter and setters have been generated for each field.
 */

package com.interactive_quiz_game.spring_boot_quiz.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Quiz")
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quizId")
    private int quizId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "category")
    private String category;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "score")
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
