package com.example.EventManagementSystem.controller;

import com.example.EventManagementSystem.dto.AuthResponse;
import com.example.EventManagementSystem.dto.RefreshRequest;
import com.example.EventManagementSystem.entities.User;
import com.example.EventManagementSystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return userService.verify(user);
    }

    @PostMapping("refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        return userService.refreshToken(request);
    }
}
