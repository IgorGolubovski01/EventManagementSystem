package com.example.EventManagementSystem.controller;

import com.example.EventManagementSystem.entities.Ticket;
import com.example.EventManagementSystem.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ticket")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    //User
    @PostMapping("purchaseTicket")
    public ResponseEntity<String> purchaseTicket(@RequestParam int eventId){
        return ticketService.purchaseTicket(eventId);
    }

    //Admin
    @GetMapping("getTicketPurchases")
    public ResponseEntity<List<Ticket>> getTickets(){
        return ticketService.getAllTickets();
    }

}
