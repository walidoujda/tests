package com.openclassrooms.starterjwt.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BadRequestExceptionTest {

    @Test
    public void testExceptionCanBeThrown() {
        assertThrows(BadRequestException.class, () -> {
            throw new BadRequestException();
        });
    }

    /*
     * @Test
     * public void testExceptionWithCustomMessage() {
     * Exception exception = assertThrows(BadRequestException.class, () -> {
     * throw new BadRequestException("Custom error message");
     * });
     * 
     * assertTrue(exception.getMessage().contains("Custom error message"));
     * }
     */
}