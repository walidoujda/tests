package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        List<Teacher> mockTeachers = Arrays.asList(teacher1, teacher2);

        when(teacherRepository.findAll()).thenReturn(mockTeachers);

        List<Teacher> teachers = teacherService.findAll();

        assertEquals(mockTeachers, teachers);
    }

    @Test
    public void testFindById_ExistingId() {
        Teacher mockTeacher = new Teacher();
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(mockTeacher));

        Teacher teacher = teacherService.findById(1L);

        assertEquals(mockTeacher, teacher);
    }

    @Test
    public void testFindById_NonExistingId() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        
        Teacher teacher = teacherService.findById(1L);
        
        assertNull(teacher);
    }
}
