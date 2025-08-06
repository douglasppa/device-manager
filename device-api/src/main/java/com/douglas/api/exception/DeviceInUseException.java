package com.douglas.api.exception;

/**
 * Exception thrown when a device in use is not allowed to be changed or deleted.
 */
public class DeviceInUseException extends RuntimeException {
    public DeviceInUseException(String message) {
        super(message);
    }
}
