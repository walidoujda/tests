package com.openclassrooms.starterjwt.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDtoTest {

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = new UserDto();
    }

    @Test
    public void testGettersAndSetters() {
        Long id = 1L;
        String email = "test@example.com";
        String lastName = "Doe";
        String firstName = "John";
        boolean admin = true;
        String password = "password123";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

        userDto.setId(id);
        userDto.setEmail(email);
        userDto.setLastName(lastName);
        userDto.setFirstName(firstName);
        userDto.setAdmin(admin);
        userDto.setPassword(password);
        userDto.setCreatedAt(createdAt);
        userDto.setUpdatedAt(updatedAt);

        assertEquals(id, userDto.getId());
        assertEquals(email, userDto.getEmail());
        assertEquals(lastName, userDto.getLastName());
        assertEquals(firstName, userDto.getFirstName());
        assertEquals(admin, userDto.isAdmin());
        assertEquals(password, userDto.getPassword());
        assertEquals(createdAt, userDto.getCreatedAt());
        assertEquals(updatedAt, userDto.getUpdatedAt());
    }
}
