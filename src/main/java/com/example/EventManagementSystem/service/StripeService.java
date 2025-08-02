package com.example.EventManagementSystem.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    public StripeService(){
        Stripe.apiKey = "sk_test_51QT0bRQ5QqlsljTLoo7zNl3HnBytP43n0njw0ipTarT0DSvTzVINJ0iEUtmFhuYTbl4VQxzP6QGfalvDlndgs0G500sBdDcxoS";
    }

    public String createPaymentIntent(int amount, String currency) throws StripeException{
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long)amount)
                .setCurrency(currency)
                .addPaymentMethodType("card")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent.getClientSecret();
    }
}
