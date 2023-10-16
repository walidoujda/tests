package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserService userService = mock(UserService.class);
    private UserMapper userMapper = mock(UserMapper.class);

    @BeforeEach
    public void setUp() {
        userController = new UserController(userService, userMapper);
    }

    @Test
    public void findById_existingId_shouldReturnUser() {
        User mockUser = new User();
        mockUser.setId(1L);
        // Add more mock data as needed

        when(userService.findById(1L)).thenReturn(mockUser);

        UserDto mockUserDto = new UserDto();
        // Add mock data for DTO as needed

        when(userMapper.toDto(mockUser)).thenReturn(mockUserDto);

        ResponseEntity<?> response = userController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUserDto, response.getBody());
    }

    @Test
    public void findById_nonExistingId_shouldReturnNotFound() {
        when(userService.findById(1L)).thenReturn(null);

        ResponseEntity<?> response = userController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findById_invalidId_shouldReturnBadRequest() {
        ResponseEntity<?> response = userController.findById("invalid_id");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void save_existingUserAndAuthorized_shouldDelete() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");

        when(userService.findById(1L)).thenReturn(mockUser);

        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("test@example.com");

        Authentication auth = new UsernamePasswordAuthenticationToken(mockUserDetails, null, Collections.emptyList());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        ResponseEntity<?> response = userController.save("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void save_existingUserAndNotAuthorized_shouldReturnUnauthorized() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");

        when(userService.findById(1L)).thenReturn(mockUser);

        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("another@example.com");

        Authentication auth = new UsernamePasswordAuthenticationToken(mockUserDetails, null, Collections.emptyList());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        ResponseEntity<?> response = userController.save("1");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    // Add more test scenarios as needed
}
