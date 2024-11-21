package com.poc.movieticketbookingplatform.controller;

import com.poc.movieticketbookingplatform.dto.TheatreResponseDTO;
import com.poc.movieticketbookingplatform.model.Theatre;
import com.poc.movieticketbookingplatform.service.TheatreService;
import com.poc.movieticketbookingplatform.service.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private IntegrationService integrationService;

    @PostMapping
    public ResponseEntity<Theatre> createTheatre(@RequestBody Theatre theatre) {
        return ResponseEntity.ok(theatreService.saveTheatre(theatre));
    }

    @PutMapping("/{theatreId}")
    public ResponseEntity<Theatre> updateTheatre(@PathVariable Long theatreId, @RequestBody Theatre updatedTheatre) {
        return ResponseEntity.ok(theatreService.updateTheatre(theatreId, updatedTheatre));
    }

    @DeleteMapping("/{theatreId}")
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long theatreId) {
        theatreService.deleteTheatre(theatreId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Theatre>> getAllTheatres() {
        return ResponseEntity.ok(theatreService.getAllTheatres());
    }

    @GetMapping("/{theatreId}")
    public ResponseEntity<Theatre> getTheatreById(@PathVariable Long theatreId) {
        return ResponseEntity.ok(theatreService.getTheatreById(theatreId));
    }

    @GetMapping("/browse")
    public ResponseEntity<List<TheatreResponseDTO>> browseTheatresByMovieTownAndDate(
            @RequestParam Long movieId,
            @RequestParam String town,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(theatreService.browseTheatresByMovieTownAndDate(movieId, town, date));
    }

    @PostMapping("/integrate/existing")
    public ResponseEntity<String> integrateWithExistingSystem(@RequestParam String theatreId) {
        integrationService.integrateWithExistingSystem(theatreId);
        return ResponseEntity.ok("Integrated with existing system");
    }

    @PostMapping("/integrate/new")
    public ResponseEntity<String> integrateWithNewTheatre(@RequestParam String theatreId) {
        integrationService.integrateWithNewTheatre(theatreId);
        return ResponseEntity.ok("Integrated with new theatre");
    }
}