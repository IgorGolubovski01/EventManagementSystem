package com.example.EventManagementSystem.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private int amount;
    private String currency;
}
