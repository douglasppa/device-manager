package com.douglas.api.exception;

import java.util.UUID;

/**
 * Exception thrown when a device with the given ID is not found.
 */
public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(UUID id) {
        super("Device not found with ID: " + id);
    }
}
