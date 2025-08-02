package com.example.EventManagementSystem.service;

import com.example.EventManagementSystem.dto.EventDto;
import com.example.EventManagementSystem.entities.Event;
import com.example.EventManagementSystem.entities.User;
import com.example.EventManagementSystem.repository.EventRepository;
import com.example.EventManagementSystem.repository.RoleRepository;
import com.example.EventManagementSystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@AllArgsConstructor
public class EventService {

    private EventRepository eventRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public ResponseEntity<?> getEvents() {

        ArrayList<EventDto> list = new ArrayList<>();
        for(Event e: eventRepository.findAll()){
            EventDto eventDto = new EventDto();
            String eventName = e.getEventName();

            String ticketsAvailable;
            if(e.getEventCapacity()-e.getSoldTickets() <= 0) {
                ticketsAvailable = "Sold out";
            }else{
                ticketsAvailable = String.valueOf(e.getEventCapacity()-e.getSoldTickets());
            }


            Date date = e.getDate();
            list.add(new EventDto(eventName,ticketsAvailable,date));
        }

        if(list.isEmpty()){
            return new ResponseEntity<>("No events currently available",HttpStatus.OK);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> createEvent(Event event) {

        event.setSoldTickets(0);
        eventRepository.save(event);
        return new ResponseEntity<>("Event added successfully",HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> deleteEvent(int eventId) {

        if(eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
            return new ResponseEntity<>("Event deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Event not found",HttpStatus.NOT_FOUND);
    }


}
