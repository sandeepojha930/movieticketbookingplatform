package com.poc.movieticketbookingplatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowResponseDTO {
    private Long id;
    private LocalDateTime showTime;
    private String movieTitle;
    private double price;
    private int availableSeats;
}