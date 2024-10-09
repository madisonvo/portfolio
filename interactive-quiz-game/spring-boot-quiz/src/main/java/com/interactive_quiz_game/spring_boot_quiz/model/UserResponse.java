package com.interactive_quiz_game.spring_boot_quiz.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "UserResponse")
@Data
public class UserResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userResponseId")
    private int userResponseId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "questionId")
    private int questionId;

    @Column(name = "optionId")
    private int optionId;

    @Column(name = "isCorrect")
    private boolean isCorrect;

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserResponseId() {
        return userResponseId;
    }

    public void setUserResponseId(int userResponseId) {
        this.userResponseId = userResponseId;
    }
}
