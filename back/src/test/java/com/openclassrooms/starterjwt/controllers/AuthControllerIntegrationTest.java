package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.hamcrest.Matchers.is;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        public void testAuthenticateUser() throws Exception {
                // Mocked request payload
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail("yoga@studio.com");
                loginRequest.setPassword("test!1234");
                Optional<User> opUser = null;
                User user = new User();
                user.setId(1L);
                user.setEmail("yoga@studio.com");
                user.setFirstName("Test");
                user.setLastName("User");
                user.setPassword("test!1234");
                user.setAdmin(true);
                UserDetailsImpl userDetails = new UserDetailsImpl(
                                user.getId(),
                                user.getEmail(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.isAdmin(),
                                user.getPassword());

                Authentication auth = Mockito.mock(Authentication.class);

                MvcResult mvcResult;

                this.mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/auth/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"email\":\"" + loginRequest.getEmail() + "\", " +
                                                                "\"password\":\"" + loginRequest.getPassword() + "\"}"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.token").exists())
                                .andExpect(jsonPath("$.username", is(user.getEmail())));
        }

        @Test
        public void testRegisterUser() throws Exception {
                SignupRequest signupRequest = new SignupRequest();
                signupRequest.setEmail("walid5@example.com");
                signupRequest.setPassword("password");
                signupRequest.setFirstName("John");
                signupRequest.setLastName("Doe");

                this.mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/auth/register")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(signupRequest)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.message", is("User registered successfully!")));

        }
        /*
         * @Test
         * public void testRegisterUser() throws Exception {
         * when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
         * doNothing().when(userRepository).save(any(User.class));
         * 
         * mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
         * .contentType(MediaType.APPLICATION_JSON)
         * .content("{\"email\": \"newuser@example.com\", \"password\": \"password\", \"firstName\": \"New\", \"lastName\": \"User\"}"
         * ))
         * .andExpect(status().isOk())
         * .andExpect(jsonPath("$.message").value("User registered successfully!"));
         * }
         * 
         * @Test
         * public void testRegisterUserWithEmailExists() throws Exception {
         * when(userRepository.existsByEmail("existinguser@example.com")).thenReturn(
         * true);
         * 
         * mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
         * .contentType(MediaType.APPLICATION_JSON)
         * .content("{\"email\": \"existinguser@example.com\", \"password\": \"password\", \"firstName\": \"New\", \"lastName\": \"User\"}"
         * ))
         * .andExpect(status().isBadRequest())
         * .andExpect(jsonPath("$.message").value("Error: Email is already taken!"));
         * }
         */
}
