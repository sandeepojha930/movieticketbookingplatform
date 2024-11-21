package com.poc.movieticketbookingplatform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

@lombok.Getter
@lombok.Setter
public class BookingDTO {
    private Long theatreId;
    private Long showId;
    private List<String> seats;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookingDate;
}