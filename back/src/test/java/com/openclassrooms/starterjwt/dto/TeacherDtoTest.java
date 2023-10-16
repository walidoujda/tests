package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherDtoTest {

    private TeacherDto teacherDto;

    @BeforeEach
    public void setUp() {
        teacherDto = new TeacherDto();
    }

    @Test
    public void testGettersAndSetters() {
        Long id = 1L;
        String lastName = "Smith";
        String firstName = "John";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

        teacherDto.setId(id);
        teacherDto.setLastName(lastName);
        teacherDto.setFirstName(firstName);
        teacherDto.setCreatedAt(createdAt);
        teacherDto.setUpdatedAt(updatedAt);

        assertEquals(id, teacherDto.getId());
        assertEquals(lastName, teacherDto.getLastName());
        assertEquals(firstName, teacherDto.getFirstName());
        assertEquals(createdAt, teacherDto.getCreatedAt());
        assertEquals(updatedAt, teacherDto.getUpdatedAt());
    }
}
