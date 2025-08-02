package com.example.EventManagementSystem.service;

import com.example.EventManagementSystem.dto.AuthResponse;
import com.example.EventManagementSystem.dto.RefreshRequest;
import com.example.EventManagementSystem.entities.User;
import com.example.EventManagementSystem.repository.RoleRepository;
import com.example.EventManagementSystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authMng;
    private final JWTService jwtService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authMng, JWTService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authMng = authMng;
        this.jwtService = jwtService;
    }

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<List<User>> allUsers() {
        return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<String> register(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            return new ResponseEntity<>("Username already in use",HttpStatus.BAD_REQUEST);
        }

//        if(user.getPassword().length() < 6){
//            return new ResponseEntity<>("Password must be at least 6 characters long",HttpStatus.BAD_REQUEST);
//        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByRoleName("user"));
        user.setBalance(0);

        userRepository.save(user);

        return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
    }


    public ResponseEntity<?> verify(User user) {
        Authentication authentication = authMng.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String accessToken = jwtService.generateToken(user.getUsername());
            String refreshToken = jwtService.generateRefreshToken(user.getUsername());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            return new ResponseEntity<>(tokens, HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<AuthResponse> refreshToken(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtService.isTokenExpired(refreshToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String username = jwtService.extractUserName(refreshToken);
        String newAccessToken = jwtService.generateToken(username);
        return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken));
    }

}
