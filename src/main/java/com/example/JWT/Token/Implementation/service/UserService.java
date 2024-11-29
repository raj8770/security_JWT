package com.example.JWT.Token.Implementation.service;

import com.example.JWT.Token.Implementation.entity.UserLoginRequest;
import com.example.JWT.Token.Implementation.jwtconfig.JwtTokenProvider;
import com.example.JWT.Token.Implementation.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {



        private final JwtTokenProvider jwtTokenProvider;
        private final PasswordEncoder passwordEncoder;


        @Autowired
        UserRepo userRepo;

        // Simulating a database with a simple map for this example
        private final Map<String, String> userDatabase = new HashMap<>();

        public UserService(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
            this.jwtTokenProvider = jwtTokenProvider;
            this.passwordEncoder = passwordEncoder;

            // Adding a sample user to the "database"
            userDatabase.put("testUser", passwordEncoder.encode("testPassword"));
        }

    public String authenticate(UserLoginRequest loginRequest) {

         Optional<UserLoginRequest> user = userRepo.findByUsername(loginRequest.getUsername());

        if (user.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            // Generate JWT token if authentication is successful
            return jwtTokenProvider.generateToken(loginRequest.getUsername());
        }

        throw new RuntimeException("Invalid username or password.");
    }


    public String registerUser(UserLoginRequest userLoginRequest) {
        // Check if username is already taken
        if (userRepo.findByUsername(userLoginRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists.");
        }

        // Encode the password before saving
        userLoginRequest.setPassword(passwordEncoder.encode(userLoginRequest.getPassword()));

        // Save the user in the database
        userRepo.save(userLoginRequest);

        return "User registered successfully!";
    }
    }





