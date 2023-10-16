package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

@Disabled
public class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    private final String mockJwtSecret = "testSecret";
    private final int mockJwtExpirationMs = 600000; // e.g., 10 minutes

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void testGenerateJwtToken() {
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test@test.com", "testFirstName", "testLastName",
                false, "Password");
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String jwt = jwtUtils.generateJwtToken(authentication);

        assertNotNull(jwt);
        assertEquals(userDetails.getUsername(), jwtUtils.getUserNameFromJwtToken(jwt));
    }

    @Test
    void testGetUserNameFromJwtToken() {
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test@test.com", "testFirstName", "testLastName",
                false, "Password");
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String jwt = jwtUtils.generateJwtToken(authentication);

        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void testValidateJwtToken_Valid() {
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test@test.com", "testFirstName", "testLastName",
                false, "Password");
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String jwt = jwtUtils.generateJwtToken(authentication);

        assertTrue(jwtUtils.validateJwtToken(jwt));
    }

    @Test
    void testValidateJwtToken_Invalid() {
        String invalidJwt = "invalidToken";

        assertFalse(jwtUtils.validateJwtToken(invalidJwt));
    }

    // ... Additional tests for invalid signature, expired token, etc. can be added
}
