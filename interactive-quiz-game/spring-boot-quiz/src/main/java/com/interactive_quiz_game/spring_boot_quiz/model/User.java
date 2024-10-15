/*
* File: User.java
* Author: Madison Vo
* Purpose: The User class represents an entity/table within the quizdatabase. It contains
*          the fields int userId (the primary key) and String username (the name of the user).
*          Getters and setters have been generated for each field.
*/

package com.interactive_quiz_game.spring_boot_quiz.model;

// imports
import lombok.Data;
import javax.persistence.*;

@Entity // show it is an entity
@Table(name = "User") // table name
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto incremented within database
    @Column(name = "userId")
    private int userId; // userid (primary key)

    @Column(name = "username")
    private String username; // username (name of the user)

    /* This method gets the userId */
    public int getUserId() {
        return userId;
    }

    /* This method sets the userId to the new, given userId */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /* This method gets the username */
    public String getUsername() {
        return username;
    }

    /* This method sets the username to the new, given username */
    public void setUsername(String username) {
        this.username = username;
    }
}
