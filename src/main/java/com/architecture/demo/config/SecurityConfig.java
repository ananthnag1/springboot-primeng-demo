package com.architecture.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable for Postman/H2 console usage
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // H2 stays open
                        .anyRequest().authenticated()                 // API requires login
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // Fixes H2 blank screen
                .httpBasic(httpBasic -> {}); // Enables Basic Auth for Postman

        return http.build();
    }
}