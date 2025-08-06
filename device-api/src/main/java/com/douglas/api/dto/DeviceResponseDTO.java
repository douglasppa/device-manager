package com.douglas.api.dto;

import com.douglas.core.domain.DeviceState;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for returning Device information.
 */
public record DeviceResponseDTO(
        @Schema(description = "Unique identifier of the device", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(description = "Device name", example = "Temperature Sensor")
        String name,

        @Schema(description = "Device brand", example = "Acme Corp")
        String brand,

        @Schema(description = "Device current state", example = "ACTIVE")
        DeviceState state,

        @Schema(description = "Creation timestamp in UTC", example = "2025-08-06T20:50:33Z")
        Instant creationTime
) {}
