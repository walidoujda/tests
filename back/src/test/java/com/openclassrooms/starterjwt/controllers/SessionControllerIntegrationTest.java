package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.SessionService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class SessionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private SessionController sessionController;
    private SessionService sessionService;
    private SessionMapper sessionMapper;

    @BeforeEach
    void setup() {
        // Initialisation de vos services, mappers et du contrôleur

        sessionService = Mockito.mock(SessionService.class);
        sessionController = new SessionController(sessionService, sessionMapper);

        // Configuration du MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(sessionController).build();
    }

    @Test
    void testFindById() throws Exception {
        // Créez un objet Session factice pour le test
        Session session = new Session();
        session.setId(1L);

        // Mockez le comportement de votre service
        Mockito.when(sessionService.getById(1L)).thenReturn(session);

        // Effectuez la requête et vérifiez la réponse
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testFindAll() throws Exception {
        // Créez une liste factice de sessions pour le test
        List<Session> sessions = new ArrayList<>();
        // Ajoutez des sessions à la liste...

        // Mockez le comportement de votre service
        Mockito.when(sessionService.findAll()).thenReturn(sessions);

        // Effectuez la requête et vérifiez la réponse
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/session"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateSession() throws Exception {
        // Créez un objet SessionDto factice pour le test
        SessionDto sessionDto = new SessionDto();
        // Initialisez les attributs du SessionDto...

        // Mockez le comportement de votre service
        Mockito.when(sessionService.create(Mockito.any(Session.class))).thenAnswer(invocation -> {
            Session createdSession = invocation.getArgument(0);
            createdSession.setId(1L); // Supposons qu'il y ait une ID générée pour la nouvelle session
            return createdSession;
        });

        // Effectuez la requête POST et vérifiez la réponse
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1)); // Vérifiez l'ID de la session créée
    }

    // Écrivez des tests similaires pour les autres méthodes (update, delete,
    // participate, noLongerParticipate)
}
