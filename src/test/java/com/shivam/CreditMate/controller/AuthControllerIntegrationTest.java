package com.shivam.CreditMate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.enums.Role;
import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @Autowired
    public AuthControllerIntegrationTest(PasswordEncoder passwordEncoder) {
        System.out.println(passwordEncoder);
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    public void setup() {
        userRepository.deleteAll(); // Clean up the repository before each test

        // Create a test user and generate a JWT token
        User user = new User();
        user.setFullname("Test User");
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(Role.ROLE_OWNER);
        user.setLoggedIn(true);
        userRepository.save(user);

        jwtToken = jwtUtil.generateToken(user);
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setFullname("New User");
        registerRequest.setEmail("newuser@example.com");
        registerRequest.setPassword("newpassword");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.fullname").value("New User"))
                .andExpect(jsonPath("$.email").value("newuser@example.com"))
                .andExpect(jsonPath("$.username").value("newuser@example.com"));

        // Verify that the user is saved in the database
        Optional<User> savedUser = userRepository.findByEmail("newuser@example.com");
        assertTrue(savedUser.isPresent());
        assertEquals(savedUser.get().getFullname(), "New User");
        assertEquals(savedUser.get().getRole(), Role.ROLE_OWNER);
    }

    @Test
    public void testRegisterUserFailure_DuplicateEmail() throws Exception {
        // Register the same user twice to check for duplicate email handling
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setFullname("Duplicate User");
        registerRequest.setEmail("test@example.com"); // Existing email
        registerRequest.setPassword("password123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("ERR_2001"))
                .andExpect(jsonPath("$.message").value(containsString("Email already exists")));
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        // set registered user whose jwt token we have as loggedIn
//        userRepository.findByUsername()

        // Login user
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(header().string("Authorization", containsString("Bearer ")))
                .andExpect(jsonPath("$.jwtToken").exists()) // TODO just for development
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andReturn();

        // Extract JWT from the Authorization header
        String authHeader = result.getResponse().getHeader("Authorization");
        assertNotNull(authHeader);
        assertTrue(authHeader.startsWith("Bearer "));

        String jwtToken = authHeader.substring(7); // Remove "Bearer " prefix
        assertNotNull(jwtToken);
        assertTrue(jwtUtil.validateToken(jwtToken));
    }

    @Test
    public void testLoginUserFailure_WrongPassword() throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorCode").value("ERR_2004"))
                .andExpect(jsonPath("$.message").value(containsString(AppErrorCodes.ERR_2004.getMessage())));
    }

    @Test
    public void testLogoutUserSuccess() throws Exception {
        // Log out
        mockMvc.perform(get("/api/auth/logout")
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User logged out successfully"));

        // Verify that the user's loggedIn status is set to false in the database
        Optional<User> user = userRepository.findByEmail("test@example.com");
        assertTrue(user.isPresent());
        assertFalse(user.get().isLoggedIn());
    }

    @Test
    public void testLogoutUserFailure_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/auth/logout").header("Authorization", "Bearer " + "Invalid JwtToken"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorCode").value("ERR_2003"));
    }

    @Test
    public void testAccessProtectedEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/api/auth/logout"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void testInvalidJsonRequest() throws Exception {
        String invalidJson = "{\"invalidField\": \"value\"}";

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(not(blankOrNullString())));
    }
}
