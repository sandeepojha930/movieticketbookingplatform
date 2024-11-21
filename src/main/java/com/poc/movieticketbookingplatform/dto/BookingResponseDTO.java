package com.poc.movieticketbookingplatform.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponseDTO {
    private Long id;
    private Long requestId;
    private Long bookingId;
    private double totalPrice;
}