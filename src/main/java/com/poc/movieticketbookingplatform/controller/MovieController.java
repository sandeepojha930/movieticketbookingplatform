package com.poc.movieticketbookingplatform.controller;

import com.poc.movieticketbookingplatform.model.Movie;
import com.poc.movieticketbookingplatform.service.MovieService;
import com.poc.movieticketbookingplatform.service.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private LocalizationService localizationService;

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.createMovie(movie);
    }

    @PutMapping("/{movieId}")
    public Movie updateMovie(@PathVariable Long movieId, @RequestBody Movie updatedMovie) {
        return movieService.updateMovie(movieId, updatedMovie);
    }

    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{movieId}")
    public Movie getMovieById(@PathVariable Long movieId) {
        return movieService.getMovieById(movieId);
    }

    @PostMapping("/localize")
    public ResponseEntity<Movie> localizeMovie(@RequestBody Movie movie, @RequestParam String language) {
        Locale locale = new Locale(language);
        Movie localizedMovie = localizationService.localizeMovie(movie, locale);
        return ResponseEntity.ok(localizedMovie);
    }
}