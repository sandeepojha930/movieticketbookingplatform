package com.poc.movieticketbookingplatform.service;

import com.poc.movieticketbookingplatform.dto.ShowResponseDTO;
import com.poc.movieticketbookingplatform.dto.TheatreResponseDTO;
import com.poc.movieticketbookingplatform.model.Show;
import com.poc.movieticketbookingplatform.model.Theatre;
import com.poc.movieticketbookingplatform.repository.ShowRepository;
import com.poc.movieticketbookingplatform.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TheatreService {
    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private ShowRepository showRepository;

    public Theatre saveTheatre(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    public Theatre updateTheatre(Long theatreId, Theatre updatedTheatre) {
        Optional<Theatre> existingTheatre = theatreRepository.findById(theatreId);
        if (existingTheatre.isPresent()) {
            Theatre theatre = existingTheatre.get();
            theatre.setName(updatedTheatre.getName());
            theatre.setLocation(updatedTheatre.getLocation());
            return theatreRepository.save(theatre);
        } else {
            throw new RuntimeException("Theatre not found with id " + theatreId);
        }
    }

    public void deleteTheatre(Long theatreId) {
        theatreRepository.deleteById(theatreId);
    }

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public Theatre getTheatreById(Long theatreId) {
        return theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found with id " + theatreId));
    }

    public List<TheatreResponseDTO> browseTheatresByMovieTownAndDate(Long movieId, String town, LocalDate date) {
        List<Theatre> theatres = theatreRepository.findByCity(town);
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = date.atTime(LocalTime.MAX);
        return theatres.stream()
                .filter(theatre -> theatre.getShows().stream()
                        .anyMatch(show -> show.getMovie().getId().equals(movieId) &&
                                show.getShowTime().isAfter(startDateTime) &&
                                show.getShowTime().isBefore(endDateTime)))
                .map(this::convertToTheatreResponseDTO)
                .collect(Collectors.toList());
    }

    private TheatreResponseDTO convertToTheatreResponseDTO(Theatre theatre) {
        TheatreResponseDTO dto = new TheatreResponseDTO();
        dto.setId(theatre.getId());
        dto.setName(theatre.getName());
        dto.setCity(theatre.getCity());
        dto.setLocation(theatre.getLocation());
        dto.setShows(theatre.getShows().stream()
                .map(this::convertToShowResponseDTO)
                .collect(Collectors.toList()));
        return dto;
    }
    private ShowResponseDTO convertToShowResponseDTO(Show show) {
        ShowResponseDTO dto = new ShowResponseDTO();
        dto.setId(show.getId());
        dto.setShowTime(show.getShowTime());
        dto.setPrice(show.getPrice());
        dto.setAvailableSeats(show.getAvailableSeats());
        dto.setMovieTitle(show.getMovie().getTitle());
        return dto;
    }
}