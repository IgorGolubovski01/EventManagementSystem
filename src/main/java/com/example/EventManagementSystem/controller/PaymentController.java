package com.example.EventManagementSystem.controller;

import com.example.EventManagementSystem.dto.PaymentRequest;
import com.example.EventManagementSystem.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService){
        this.stripeService = stripeService;
    }

    @PostMapping("/createPaymentIntent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentRequest paymentRequest){
        try {
            String clientSecret = stripeService.createPaymentIntent(paymentRequest.getAmount(), paymentRequest.getCurrency());
            return ResponseEntity.ok(Map.of("clientSecret", clientSecret));
        }catch (StripeException e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
            }
        }
    }
