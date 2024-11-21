package com.poc.movieticketbookingplatform.model;

import com.poc.movieticketbookingplatform.util.InputSanitizer;
import com.poc.movieticketbookingplatform.validator.ValidInput;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ValidInput
    private String username;
    private String password;
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = InputSanitizer.sanitize(username);
    }
}