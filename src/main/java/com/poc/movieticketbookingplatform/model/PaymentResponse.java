package com.poc.movieticketbookingplatform.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PaymentResponse {
    private String message;
    private String transactionId;
    private String status;
    private double amount;
    private String currency;
    private String paymentMethod;
    private String timestamp;
}