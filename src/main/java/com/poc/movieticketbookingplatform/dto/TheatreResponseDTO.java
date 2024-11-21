package com.poc.movieticketbookingplatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TheatreResponseDTO {
    private Long id;
    private String name;
    private String city;
    private String location;
    private List<ShowResponseDTO> shows;
}