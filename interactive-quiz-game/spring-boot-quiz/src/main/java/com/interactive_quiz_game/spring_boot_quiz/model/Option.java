package com.interactive_quiz_game.spring_boot_quiz.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "`Option`")
@Data
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionId")
    private int optionId;

    @Column(name = "questionId")
    private int questionId;

    @Column(name = "`option`")
    private String option;

    @Column(name = "isCorrect")
    private boolean isCorrect;

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

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
