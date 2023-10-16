package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSession() {
        Session mockSession = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(mockSession);

        Session result = sessionService.create(new Session());

        assertEquals(mockSession, result);
    }

    @Test
    public void testGetSessionById() {
        Session mockSession = new Session();
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));

        Session result = sessionService.getById(1L);

        assertEquals(mockSession, result);
    }

    @Test
    public void testGetSessionById_NotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        Session result = sessionService.getById(1L);

        assertNull(result);
    }

    @Test
    public void testParticipate() {
        Session mockSession = new Session();
        mockSession.setUsers(new ArrayList<>()); // Initialize the list
        User mockUser = new User();
        mockUser.setId(1L);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        sessionService.participate(1L, 1L);

        assertTrue(mockSession.getUsers().contains(mockUser));
    }

    @Test
    public void testParticipate_SessionOrUserNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            sessionService.participate(1L, 1L);
        });
    }

    @Test
    public void testParticipate_AlreadyParticipating() {
        Session mockSession = new Session();
        mockSession.setUsers(new ArrayList<>()); // Initialize the list
        User mockUser = new User();
        mockUser.setId(1L);
        mockSession.getUsers().add(mockUser);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        assertThrows(BadRequestException.class, () -> {
            sessionService.participate(1L, 1L);
        });
    }

    @Test
    public void testDeleteSession() {
        // We only need to ensure the method is called.
        // Actual deletion is handled by the repository.
        doNothing().when(sessionRepository).deleteById(1L);

        sessionService.delete(1L);

        verify(sessionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateSession() {
        Session mockSession = new Session();
        mockSession.setId(1L);

        when(sessionRepository.save(any(Session.class))).thenReturn(mockSession);

        Session updatedSession = new Session();
        Session result = sessionService.update(1L, updatedSession);

        assertEquals(mockSession, result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testNoLongerParticipate_UserNotParticipating() {
        Session mockSession = new Session();
        mockSession.setUsers(new ArrayList<>());

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));

        assertThrows(BadRequestException.class, () -> {
            sessionService.noLongerParticipate(1L, 1L);
        });
    }

    @Test
    public void testNoLongerParticipate_Success() {
        Session mockSession = new Session();
        mockSession.setUsers(new ArrayList<>());
        User mockUser = new User();
        mockUser.setId(1L);
        mockSession.getUsers().add(mockUser);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));

        sessionService.noLongerParticipate(1L, 1L);

        assertFalse(mockSession.getUsers().contains(mockUser));
    }

    @Test
    public void testNoLongerParticipate_SessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            sessionService.noLongerParticipate(1L, 1L);
        });
    }

}
