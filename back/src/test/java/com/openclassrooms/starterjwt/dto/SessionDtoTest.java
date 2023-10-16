package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionDtoTest {

    private SessionDto sessionDto;

    @BeforeEach
    public void setUp() {
        sessionDto = new SessionDto();
    }

    @Test
    public void testGettersAndSetters() {
        Long id = 1L;
        String name = "Session 1";
        Date date = new Date();
        Long teacherId = 2L;
        String description = "Description";
        List<Long> users = Arrays.asList(3L, 4L);

        sessionDto.setId(id);
        sessionDto.setName(name);
        sessionDto.setDate(date);
        sessionDto.setTeacher_id(teacherId);
        sessionDto.setDescription(description);
        sessionDto.setUsers(users);

        assertEquals(id, sessionDto.getId());
        assertEquals(name, sessionDto.getName());
        assertEquals(date, sessionDto.getDate());
        assertEquals(teacherId, sessionDto.getTeacher_id());
        assertEquals(description, sessionDto.getDescription());
        assertEquals(users, sessionDto.getUsers());
    }

    // Additional tests could be made for createdAt and updatedAt in a similar
    // fashion.
}
