package com.example.JWT.Token.Implementation.config;

import com.example.JWT.Token.Implementation.jwtconfig.JwtAuthenticationFilter;
import com.example.JWT.Token.Implementation.jwtconfig.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()  // Allow login route without authentication
                        .anyRequest().authenticated()          // Protect all other routes
                )
                .addFilter(new JwtAuthenticationFilter(jwtTokenProvider));  // Add JWT filter for stateless authentication
                //.formLogin().disable()  // Remove form-based login (not needed with JWT)
                //.httpBasic().disable(); // Remove HTTP Basic authentication (optional)

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

