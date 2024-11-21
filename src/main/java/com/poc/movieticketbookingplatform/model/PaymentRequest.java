package com.poc.movieticketbookingplatform.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {
    private double amount;
    private String currency;
    private String paymentMethod;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String billingAddress;
}