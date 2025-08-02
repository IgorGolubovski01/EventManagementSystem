package com.example.EventManagementSystem.controller;

import com.example.EventManagementSystem.entities.User;
import com.example.EventManagementSystem.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("allUsers")
    public ResponseEntity<List<User>> allUsers(){
        return userService.allUsers();
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody User user){
        return userService.register(user);
    }








}
