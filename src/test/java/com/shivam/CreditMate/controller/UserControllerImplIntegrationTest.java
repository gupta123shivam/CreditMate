package com.shivam.CreditMate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivam.CreditMate.dto.request.PasswordChangeDto;
import com.shivam.CreditMate.dto.request.UserProfileUpdateRequestDto;
import com.shivam.CreditMate.enums.Role;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.security.JwtUtil;
import com.shivam.CreditMate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Use a test profile for the integration testing environment
public class UserControllerImplIntegrationTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private String jwtToken;

    @BeforeEach
    public void setup() {
        // Clear the repository to ensure a clean state
        userRepository.deleteAll();

        // Initialize a test user and save to the repository
        testUser = User.builder()
                .email("test@example.com")
                .fullname("Test User")
                .role(Role.ROLE_OWNER)
                .password(passwordEncoder.encode("password"))
                .build();

        userRepository.save(testUser);

        jwtToken = jwtUtil.generateToken(testUser);
    }

    @Test
    public void testGetUserProfile_Success() throws Exception {
        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/users/profile")
                        .header("Authorization", "Bearer " + jwtToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.fullname").value("Test User"));
    }

    @Test
    public void testUpdateUserProfile_Success() throws Exception {
        // Create a request DTO for the update
        UserProfileUpdateRequestDto updateRequest = new UserProfileUpdateRequestDto();
        updateRequest.setFullname("Updated User");

        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/users/profile")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullname").value("Updated User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testUpdatePassword_Success() throws Exception {
        // Create a request DTO for password change
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setOldPassword("password"); // original password
        passwordChangeDto.setNewPassword("newPassword");

        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/users/change-password")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordChangeDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Password changed successfully."));

        // Verify the password was updated in the database
        User updatedUser = userRepository.findByEmail("test@example.com").orElse(null);
        assert updatedUser != null;
        assert passwordEncoder.matches("newPassword", updatedUser.getPassword());
    }

    @Test
    public void testUpdatePassword_Failure_IncorrectOldPassword() throws Exception {
        // Create a request DTO for password change
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setOldPassword("wrongOldPassword");
        passwordChangeDto.setNewPassword("newPassword");

        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/users/change-password")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordChangeDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("ERR_5002"))
                .andExpect(jsonPath("$.message").value("old password is not matching"));
    }
}
