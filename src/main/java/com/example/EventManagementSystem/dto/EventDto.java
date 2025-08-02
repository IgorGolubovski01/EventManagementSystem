package com.example.EventManagementSystem.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDto {
    private String eventName;
    private String ticketsAvailable;
    private Date date;
}
