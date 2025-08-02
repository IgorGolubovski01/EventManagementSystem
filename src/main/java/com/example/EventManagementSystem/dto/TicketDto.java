package com.example.EventManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketDto {
    private String eventName;
    private float ticketPrice;

}
