package com.douglas.api.controller;

import com.douglas.api.dto.DevicePatchDTO;
import com.douglas.api.dto.DeviceRequestDTO;
import com.douglas.api.dto.DeviceResponseDTO;
import com.douglas.core.domain.DeviceState;
import com.douglas.api.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for managing devices.
 */
@RestController
@RequestMapping("/devices")
@Tag(name = "Devices", description = "Operations related to device management")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Operation(summary = "Create a new device", description = "Register a new device in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created successfully",
                    content = @Content(schema = @Schema(implementation = DeviceResponseDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 400,\n" +
                                            "  \"error\": \"Invalid request body\"\n" +
                                            "}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 500,\n" +
                                            "  \"error\": \"Internal server error\"\n" +
                                            "}"
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<DeviceResponseDTO> createDevice(@RequestBody @Valid DeviceRequestDTO request) {
        DeviceResponseDTO response = deviceService.createDevice(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get device by ID", description = "Retrieve a device by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device found",
                    content = @Content(schema = @Schema(implementation = DeviceResponseDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Device not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 404,\n" +
                                            "  \"error\": \"Device not found\"\n" +
                                            "}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid UUID format",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 400,\n" +
                                            "  \"error\": \"Invalid UUID format\"\n" +
                                            "}"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> getDeviceById(@PathVariable UUID id) {
        DeviceResponseDTO response = deviceService.getDeviceById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get all devices",
            description = "Returns a list of all registered devices."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device found",
                    content = @Content(schema = @Schema(implementation = DeviceResponseDTO.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 500,\n" +
                                            "  \"error\": \"Internal server error\"\n" +
                                            "}"
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<DeviceResponseDTO>> listDevices(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) DeviceState state
    ) {
        List<DeviceResponseDTO> response = deviceService.listDevices(name, brand, state);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update device completely", description = "Update all fields of a device by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device updated successfully",
                    content = @Content(schema = @Schema(implementation = DeviceResponseDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Device not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 404,\n" +
                                            "  \"error\": \"Device not found\"\n" +
                                            "}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 400,\n" +
                                            "  \"error\": \"Invalid request data\"\n" +
                                            "}"
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> updateDevice(
            @PathVariable UUID id,
            @RequestBody @Valid DeviceRequestDTO request
    ) {
        DeviceResponseDTO response = deviceService.updateDevice(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Partially update a device", description = "Update one or more fields of a device.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device partially updated successfully",
                    content = @Content(schema = @Schema(implementation = DeviceResponseDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Device not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 404,\n" +
                                            "  \"error\": \"Device not found\"\n" +
                                            "}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 400,\n" +
                                            "  \"error\": \"Invalid request data\"\n" +
                                            "}"
                            )
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> patchDevice(
            @PathVariable UUID id,
            @RequestBody DevicePatchDTO patch
    ) {
        DeviceResponseDTO response = deviceService.patchDevice(id, patch);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete device", description = "Remove a device from the system by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device deleted successfully",
                    content = @Content(schema = @Schema(implementation = DeviceResponseDTO.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Device not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 404,\n" +
                                            "  \"error\": \"Device not found\"\n" +
                                            "}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid UUID format",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "  \"timestamp\": \"2025-08-06T22:59:30.681891832Z\",\n" +
                                            "  \"status\": 400,\n" +
                                            "  \"error\": \"Invalid UUID format\"\n" +
                                            "}"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable UUID id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
