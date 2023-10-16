package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("Password");
        String jwt = "sampleJwtToken";

        // Mock your expected interactions
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwt);
        UserDetailsImpl userDetailsMock = new UserDetailsImpl(1L, "test@test.com", "testFirstName", "testLastName",
                false, "Password");

        when(authentication.getPrincipal()).thenReturn(userDetailsMock);

        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        assertTrue(response.getBody() instanceof JwtResponse);

        JwtResponse jwtResponse = (JwtResponse) response.getBody();

        assertNotNull(jwtResponse);
        assertEquals(jwt, jwtResponse.getToken());
        assertEquals(userDetailsMock.getId(), jwtResponse.getId());
        assertEquals(userDetailsMock.getUsername(), jwtResponse.getUsername());
        assertEquals(userDetailsMock.getFirstName(), jwtResponse.getFirstName());
        assertEquals(userDetailsMock.getLastName(), jwtResponse.getLastName());
        assertEquals(userDetailsMock.getAdmin(), jwtResponse.getAdmin());
    }

    @Test
    public void testRegisterUserEmailAlreadyTaken() {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("test@example.com");

        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(signUpRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String responseBody = ((MessageResponse) response.getBody()).getMessage();
        assertEquals("Error: Email is already taken!", responseBody);
    }

    @Test
    public void testRegisterUserSuccessful() {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password123");
        signUpRequest.setFirstName("John");
        signUpRequest.setLastName("Doe");

        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword123");

        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        // Assert the status code
        assertEquals(HttpStatus.OK, response.getStatusCode()); // or HttpStatus.OK if that's what you expect

        String responseBody = ((MessageResponse) response.getBody()).getMessage();
        assertEquals("User registered successfully!", responseBody); // Adjust this to your actual success message
    }

}
