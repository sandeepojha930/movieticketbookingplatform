package com.poc.movieticketbookingplatform.service;

import com.poc.movieticketbookingplatform.model.Movie;
import com.poc.movieticketbookingplatform.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long movieId, Movie updatedMovie) {
        Optional<Movie> existingMovie = movieRepository.findById(movieId);
        if (existingMovie.isPresent()) {
            Movie movie = existingMovie.get();
            movie.setTitle(updatedMovie.getTitle());
            movie.setGenre(updatedMovie.getGenre());
            movie.setDuration(updatedMovie.getDuration());
            movie.setReleaseDate(updatedMovie.getReleaseDate());
            return movieRepository.save(movie);
        } else {
            throw new RuntimeException("Movie not found with id " + movieId);
        }
    }

    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + movieId));
    }
}