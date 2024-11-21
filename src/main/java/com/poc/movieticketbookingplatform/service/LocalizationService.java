package com.poc.movieticketbookingplatform.service;

import com.poc.movieticketbookingplatform.model.Movie;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationService {

    public Movie localizeMovie(Movie movie, Locale locale) {
        // Logic to localize movie details based on the locale
        return movie;
    }
}