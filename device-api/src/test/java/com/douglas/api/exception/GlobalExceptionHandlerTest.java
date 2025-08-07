package com.douglas.api.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleNotFoundException() {
        UUID id = UUID.randomUUID();
        DeviceNotFoundException ex = new DeviceNotFoundException(id);

        ResponseEntity<Map<String, Object>> response = handler.handleNotFound(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().get("error").toString().contains(id.toString()));
    }

    @Test
    void shouldHandleInUseException() {
        DeviceInUseException ex = new DeviceInUseException("Device is in use");

        ResponseEntity<Map<String, Object>> response = handler.handleInUse(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Device is in use", response.getBody().get("error"));
    }

    @Test
    void shouldHandleGenericException() {
        Exception ex = new RuntimeException("Unexpected crash");

        ResponseEntity<Map<String, Object>> response = handler.handleGeneric(ex);

        assertEquals(500, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().get("error").toString().contains("Unexpected error:"));
    }
}
