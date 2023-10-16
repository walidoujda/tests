package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherControllerTest {

    private TeacherController teacherController;
    private TeacherService teacherService = mock(TeacherService.class);
    private TeacherMapper teacherMapper = mock(TeacherMapper.class);

    @BeforeEach
    public void setUp() {
        teacherController = new TeacherController(teacherService, teacherMapper);
    }

    @Test
    public void findById_existingId_shouldReturnTeacher() {
        Teacher mockTeacher = new Teacher();
        // Assuming your Teacher model has an ID field.
        mockTeacher.setId(1L);

        when(teacherService.findById(1L)).thenReturn(mockTeacher);

        TeacherDto mockTeacherDto = new TeacherDto();
        // ... Set mock properties for DTO ...

        when(teacherMapper.toDto(mockTeacher)).thenReturn(mockTeacherDto);

        ResponseEntity<?> response = teacherController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTeacherDto, response.getBody());
    }

    @Test
    public void findById_nonExistingId_shouldReturnNotFound() {
        when(teacherService.findById(1L)).thenReturn(null);

        ResponseEntity<?> response = teacherController.findById("1");
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findById_invalidId_shouldReturnBadRequest() {
        ResponseEntity<?> response = teacherController.findById("invalid_id");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void findAll_shouldReturnListOfTeachers() {
        Teacher mockTeacher = new Teacher();
        mockTeacher.setId(1L);

        when(teacherService.findAll()).thenReturn(Collections.singletonList(mockTeacher));

        TeacherDto mockTeacherDto = new TeacherDto();
        // ... Set mock properties for DTO ...

        when(teacherMapper.toDto(Collections.singletonList(mockTeacher)))
                .thenReturn(Collections.singletonList(mockTeacherDto));

        ResponseEntity<?> response = teacherController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        assertFalse(((List<?>) response.getBody()).isEmpty());
    }
}
