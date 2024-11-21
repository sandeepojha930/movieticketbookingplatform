package com.poc.movieticketbookingplatform.controller;

import com.poc.movieticketbookingplatform.model.PaymentCallback;
import com.poc.movieticketbookingplatform.model.PaymentRequest;
import com.poc.movieticketbookingplatform.model.PaymentResponse;
import com.poc.movieticketbookingplatform.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/callback")
    public ResponseEntity<PaymentResponse> handlePaymentCallback(@RequestBody PaymentCallback callbackData) {
        PaymentResponse response = paymentService.handlePaymentCallback(callbackData);
        return ResponseEntity.ok(response);
    }
}