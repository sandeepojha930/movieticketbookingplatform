package com.poc.movieticketbookingplatform.service;

import com.poc.movieticketbookingplatform.model.PaymentCallback;
import com.poc.movieticketbookingplatform.model.PaymentRequest;
import com.poc.movieticketbookingplatform.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    @Value("${payment.gateway.epx.apiKey}")
    private String apiKey;

    @Value("${payment.gateway.epx.secretKey}")
    private String secretKey;

    @Value("${payment.gateway.epx.url}")
    private String epxUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        String url = epxUrl + "/process";
        PaymentResponse response = restTemplate.postForObject(url, paymentRequest, PaymentResponse.class);
        return response;
    }

    public PaymentResponse handlePaymentCallback(PaymentCallback callbackData) {
        String url = epxUrl + "/callback";
        PaymentResponse response = restTemplate.postForObject(url, callbackData, PaymentResponse.class);
        return response;
    }
}