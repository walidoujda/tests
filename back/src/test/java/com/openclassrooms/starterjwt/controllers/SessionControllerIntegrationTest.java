package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.SessionService;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private TeacherRepository teacherRepository;
        @Autowired
        private SessionService sessionService;
        @Autowired
        private JwtUtils jwtUtils;
        @Autowired
        private AuthenticationManager authenticationManager;
        private String jwt;

        @BeforeEach
        public void setup() {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken("walidzerrifi@example.com",
                                                "test!1234"));
                // Générer le JWT
                this.jwt = jwtUtils.generateJwtToken(authentication);
        }

        @Test
        public void testFindAllSessions() throws Exception {
                mockMvc.perform(get("/api/session")
                                .header("Authorization", "Bearer " + this.jwt))
                                .andExpect(status().isOk());
        }

        @Test
        public void testFindSessions() throws Exception {
                mockMvc.perform(get("/api/session/1")
                                .header("Authorization", "Bearer " + this.jwt))
                                .andExpect(status().isOk());
        }

        @Test
        public void testCreateSession() throws Exception {
                String str = "{\"name\": \"session 100\", \"date\": \"2012-01-01\",\"teacher_id\": 5,\"users\": null,\"description\": \"my description\"}";
                mockMvc.perform(post("/api/session")
                                .header("Authorization", "Bearer " + this.jwt)
                                .contentType("application/json")
                                .content(str))
                                .andExpect(status().isOk());
        }

        @Test
        public void testUpdateSession() throws Exception {
                String str = "{\"name\": \"session 200\", \"date\": \"2012-01-01\",\"teacher_id\": 5,\"users\": null,\"description\": \"my description\"}";
                mockMvc.perform(put("/api/session/2")
                                .header("Authorization", "Bearer " + this.jwt)
                                .contentType("application/json")
                                .content(str))
                                .andExpect(status().isOk());
        }
}
