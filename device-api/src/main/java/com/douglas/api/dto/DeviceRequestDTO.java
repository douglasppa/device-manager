package com.douglas.api.dto;

import com.douglas.core.domain.DeviceState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for creating or updating a Device.
 */
public record DeviceRequestDTO(
        @Schema(description = "Device name", example = "Temperature Sensor")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Device brand", example = "Acme Corp")
        @NotBlank(message = "Brand is required")
        String brand,

        @Schema(description = "Device current state", example = "ACTIVE")
        @NotNull(message = "State is required")
        DeviceState state
) {}
