package com.example.JWT.Token.Implementation.controller;

import com.example.JWT.Token.Implementation.entity.UserLoginRequest;
import com.example.JWT.Token.Implementation.jwtconfig.JwtTokenProvider;
import com.example.JWT.Token.Implementation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        System.out.println("Received UserLoginRequest: " + loginRequest);
        System.out.println("Username: " + loginRequest.getUsername());
        System.out.println("Password: " + loginRequest.getPassword());

        try {
            String token = userService.authenticate(loginRequest);
            return ResponseEntity.ok(Map.of("token", token,"message","Login successfull"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage(),"message","Login Failed"));
        }
    }



    @GetMapping("/secure")
    public ResponseEntity<?> secureEndpoint() {
        return ResponseEntity.ok(Map.of("message", "This is a secured endpoint."));
    }



    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserLoginRequest user) {
        try {
            String message = userService.registerUser(user);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}


