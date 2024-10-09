package com.interactive_quiz_game.spring_boot_quiz.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;

    @Column(name = "username")
    private String username;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
