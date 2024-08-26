package com.shivam.CreditMate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health",
                                "/swagger-ui.html",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/swagger-ui/**",
                                "/actuator/health",
                                "/v3/api-docs/**",
                                "/api/users/register",
                                "/api/users/login").permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


}
