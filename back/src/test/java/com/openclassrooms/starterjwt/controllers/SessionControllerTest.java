package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private SessionMapper sessionMapper;

    @Mock
    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        Session session = new Session(); // Create a mock Session object with some data.
        when(sessionService.getById(anyLong())).thenReturn(session);
        when(sessionMapper.toDto((Session) session)).thenReturn(new SessionDto());

        // Act
        ResponseEntity<?> response = sessionController.findById("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof SessionDto);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(sessionService.getById(anyLong())).thenReturn(null);

        // Act
        ResponseEntity<?> response = sessionController.findById("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindById_BadRequest() {
        // Act
        ResponseEntity<?> response = sessionController.findById("invalid_id");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testFindAll() {
        // Arrange
        Session session = new Session(); // Create a mock Session object with some data.
        when(sessionService.findAll()).thenReturn(Arrays.asList(session, session));
        when(sessionMapper.toDto(Collections.singletonList(any())))
                .thenReturn(Arrays.asList(new SessionDto(), new SessionDto()));

        // Act
        ResponseEntity<?> response = sessionController.findAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof List);
        assertEquals(2, ((List<?>) response.getBody()).size());
    }

    @Test
    void testCreateSession() {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        Session session = new Session();
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionService.create(any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

        // Act
        ResponseEntity<?> response = sessionController.create(sessionDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof SessionDto);
    }

    @Test
    void testUpdateSession_Success() {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        Session session = new Session();
        when(sessionService.update(anyLong(), any(Session.class))).thenReturn(session);
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

        // Act
        ResponseEntity<?> response = sessionController.update("1", sessionDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof SessionDto);
    }

    @Test
    void testUpdateSession_BadRequest() {
        ResponseEntity<?> response = sessionController.update("invalid_id", new SessionDto());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testSave_Success() {
        Session session = new Session();
        when(sessionService.getById(anyLong())).thenReturn(session);

        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService, times(1)).delete(anyLong());
    }

    @Test
    void testSave_NumberFormatException() {
        // Act
        ResponseEntity<?> response = sessionController.save("invalid_id");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testSave_NotFound() {
        when(sessionService.getById(anyLong())).thenReturn(null);

        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testParticipate() {
        // Act
        ResponseEntity<?> response = sessionController.participate("1", "1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService, times(1)).participate(anyLong(), anyLong());
    }

    @Test
    void testParticipate_BadRequest() {
        // Act
        ResponseEntity<?> response = sessionController.participate("invalid_id", "1");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testNoLongerParticipate_Success() {
        // Act
        ResponseEntity<?> response = sessionController.noLongerParticipate("1", "1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService, times(1)).noLongerParticipate(anyLong(), anyLong());
    }

    @Test
    void testNoLongerParticipate_BadRequest() {
        // Act
        ResponseEntity<?> response = sessionController.noLongerParticipate("invalid_id", "1");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
