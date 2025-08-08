package com.douglas.api.dto;

import com.douglas.core.domain.DeviceState;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for partially updating a Device. All fields are optional and only provided ones will be
 * updated.
 */
public record DevicePatchDto(
    @Schema(description = "Device name", example = "Temperature Sensor") String name,
    @Schema(description = "Device brand", example = "Acme Corp") String brand,
    @Schema(description = "Device current state", example = "AVAILABLE") DeviceState state) {}
