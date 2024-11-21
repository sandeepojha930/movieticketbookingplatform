package com.poc.movieticketbookingplatform.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentCallback {
    private String transactionId;
    private String status;
    private double amount;
    private String currency;
    private String paymentMethod;
    private String timestamp;
}