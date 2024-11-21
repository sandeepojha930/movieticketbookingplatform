package com.poc.movieticketbookingplatform.model;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
public class SubscriptionPlan {
    private Long id;
    private String name;
    private double price;
    private String duration; // e.g., "monthly", "yearly"

}