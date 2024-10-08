package com.shivam.CreditMate.security;

import com.shivam.CreditMate.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final SecretKey secretKey;
    private final long expirationInSeconds;

    // Constructor initializes secret key and expiration time from properties
    public JwtUtil(@Value("${jwt.secret-key}") String key, @Value("${jwt.expiration-seconds}") int expirationInSeconds) {
        this.secretKey = getSecretKey(key);
        this.expirationInSeconds = expirationInSeconds;
    }

    // Generate JWT token with user details and expiration
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * expirationInSeconds)) // Expiry
                .signWith(SignatureAlgorithm.HS256, this.secretKey)
                .compact();
    }

    // Extract claim using a function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parse and extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    }

    // Extract username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Check if the token has expired
    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // Validate token by checking username and expiration
    public boolean validateTokenWithCurrentUser(String token, User userDetails) {
        final String username = extractUsername(token);

        return (
//                userDetails.isLoggedIn() &&
                username.equals(userDetails.getUsername()) &&
                        !isTokenExpired(token));
    }

    // Validate token by checking username and expiration
    public boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }

    // Convert hex string to byte array
    private byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    // Create SecretKey from hex string
    private SecretKey getSecretKey(String key) {
        byte[] keyBytes = hexStringToByteArray(key);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
