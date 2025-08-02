package com.example.EventManagementSystem.controller;

import com.example.EventManagementSystem.entities.Event;
import com.example.EventManagementSystem.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("event")
@AllArgsConstructor
public class EventController {

    private EventService eventService;

    @GetMapping("getEvents")
    public ResponseEntity<?> getEvents(){
        return eventService.getEvents();
    }

    @PostMapping("createEvent")
    public ResponseEntity<String> createEvent(@Valid @RequestBody Event event){
        return eventService.createEvent(event);
    }

    @DeleteMapping("deleteEvent/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable int eventId){
        return eventService.deleteEvent(eventId);
    }
}
