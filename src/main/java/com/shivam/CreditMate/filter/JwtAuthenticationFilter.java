package com.shivam.CreditMate.filter;

import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.security.JwtUtil;
import com.shivam.CreditMate.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * This filter is responsible for processing JWT-based authentication for incoming HTTP requests.
 * It checks the Authorization header for a JWT token and validates it. If the token is valid, it sets the
 * authentication in the security context.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(
            JwtUtil jwtUtil,
            UserService userService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * Filters incoming requests to validate JWT tokens and set the security context.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Extract the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");

        // If the Authorization header is missing or does not contain a Bearer token, skip filtering
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract JWT token from the Authorization header
            final String jwt = authHeader.substring(7);
            final String username = jwtUtil.extractUsername(jwt);

            // Get current authentication object from the security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // If username is extracted from the token and there is no authentication object in the context
            if (username != null && authentication == null) {
                // Load user details from the UserService
                User userDetails = userService.loadUserByUsername(username);

                // Validate the token with the user details
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    // Create an authentication token for the user
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Set additional details for the authentication token
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication token in the security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Continue the filter chain
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // Handle any exception that occurs during filtering using the configured exception resolver
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
