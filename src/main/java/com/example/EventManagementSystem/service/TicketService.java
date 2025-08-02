package com.example.EventManagementSystem.service;

import com.example.EventManagementSystem.entities.Event;
import com.example.EventManagementSystem.entities.Ticket;
import com.example.EventManagementSystem.entities.User;
import com.example.EventManagementSystem.repository.EventRepository;
import com.example.EventManagementSystem.repository.TicketRepository;
import com.example.EventManagementSystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;    
    private final EventRepository eventRepository;
    private final EmailService emailService;

    @Transactional
    public ResponseEntity<String> purchaseTicket(int eventId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        //Event presence
        if(eventOptional.isEmpty())
            return new ResponseEntity<>("Event not found",HttpStatus.NOT_FOUND);

        //Balance check
        if(user.getBalance() < eventOptional.get().getEventPrice())
            return new ResponseEntity<>("Insufficient funds",HttpStatus.BAD_REQUEST);

        //Capacity check
        if(eventOptional.get().getEventCapacity() <= eventOptional.get().getSoldTickets())
            return new ResponseEntity<>("Event is fully booked",HttpStatus.NOT_ACCEPTABLE);


        user.setBalance(user.getBalance()-eventOptional.get().getEventPrice());
        eventOptional.get().setSoldTickets(eventOptional.get().getSoldTickets()+1);

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setEvent(eventOptional.get());
        ticket.setTransactionDate(new Date(System.currentTimeMillis()));

        ticketRepository.save(ticket);

        String emailBody = String.format(
                "Hello %s," +
                        "\n" +
                        "\nThank you for purchasing a ticket." +
                        "\n" +
                        "\nEvent Details: " +
                        "\nEvent Name: %s" +
                        "\n- Date: %s" +
                        "\n" +
                        "\nEnjoy the event!" +
                        "\n" +
                        "\nBest regards," +
                        "\nEvent Management Team",
                user.getUsername(),
                eventOptional.get().getEventName(),
                eventOptional.get().getDate()
        );
        emailService.sendEmail(user.getEmail(), "Ticket Confirmation", emailBody);

        return new ResponseEntity<>("Ticket purchased, check email for more information",HttpStatus.OK);
    }

    public ResponseEntity<List<Ticket>> getAllTickets() {
        return new ResponseEntity<>(ticketRepository.findAll(),HttpStatus.OK);
    }
}
