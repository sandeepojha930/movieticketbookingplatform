package com.poc.movieticketbookingplatform.model;

import com.poc.movieticketbookingplatform.util.InputSanitizer;
import com.poc.movieticketbookingplatform.validator.ValidInput;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ValidInput
    private String title;
    private String genre;
    private String language;
    private int duration; // duration in minutes
    private LocalDate releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = InputSanitizer.sanitize(title);
    }
}