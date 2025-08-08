package com.douglas.api.controller;

import com.douglas.api.dto.DevicePatchDto;
import com.douglas.api.dto.DeviceRequestDto;
import com.douglas.api.dto.DeviceResponseDto;
import com.douglas.api.service.DeviceService;
import com.douglas.core.domain.DeviceState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller responsible for managing device resources. Provides endpoints for creating,
 * retrieving, updating, partially updating and deleting devices.
 */
@RestController
@RequestMapping("/devices")
@Tag(name = "Devices", description = "Operations related to device management")
public class DeviceController {

  private final DeviceService deviceService;

  /**
   * Constructs a new {@code DeviceController} with the given {@link DeviceService}.
   *
   * @param deviceService service layer used for device operations
   */
  public DeviceController(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  /**
   * Creates a new device.
   *
   * @param request DTO containing the new device data
   * @return the created device
   */
  @Operation(summary = "Create a new device", description = "Register a new device in the system.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Device created successfully",
            content = @Content(schema = @Schema(implementation = DeviceResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request body"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping
  public ResponseEntity<DeviceResponseDto> createDevice(
      @RequestBody @Valid DeviceRequestDto request) {
    DeviceResponseDto response = deviceService.createDevice(request);
    return ResponseEntity.ok(response);
  }

  /**
   * Retrieves a device by its unique ID.
   *
   * @param id the UUID of the device
   * @return the device data
   */
  @Operation(summary = "Get device by ID", description = "Retrieve a device by its unique ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Device found"),
        @ApiResponse(responseCode = "404", description = "Device not found"),
        @ApiResponse(responseCode = "400", description = "Invalid UUID format")
      })
  @GetMapping("/{id}")
  public ResponseEntity<DeviceResponseDto> getDeviceById(@PathVariable UUID id) {
    DeviceResponseDto response = deviceService.getDeviceById(id);
    return ResponseEntity.ok(response);
  }

  /**
   * Retrieves a list of devices optionally filtered by name, brand or state.
   *
   * @param name optional name filter (case-insensitive)
   * @param brand optional brand filter (case-insensitive)
   * @param state optional device state filter
   * @return list of matching devices
   */
  @Operation(summary = "Get all devices", description = "Returns a list of all registered devices.")
  @GetMapping
  public ResponseEntity<List<DeviceResponseDto>> listDevices(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String brand,
      @RequestParam(required = false) DeviceState state) {
    List<DeviceResponseDto> response = deviceService.listDevices(name, brand, state);
    return ResponseEntity.ok(response);
  }

  /**
   * Updates all fields of a device by its ID.
   *
   * @param id the UUID of the device to update
   * @param request DTO containing the updated device data
   * @return the updated device
   */
  @Operation(
      summary = "Update device completely",
      description = "Update all fields of a device by ID.")
  @PutMapping("/{id}")
  public ResponseEntity<DeviceResponseDto> updateDevice(
      @PathVariable UUID id, @RequestBody @Valid DeviceRequestDto request) {
    DeviceResponseDto response = deviceService.updateDevice(id, request);
    return ResponseEntity.ok(response);
  }

  /**
   * Partially updates fields of a device by its ID.
   *
   * @param id the UUID of the device to update
   * @param patch DTO containing the fields to be updated
   * @return the updated device
   */
  @Operation(
      summary = "Partially update a device",
      description = "Update one or more fields of a device.")
  @PatchMapping("/{id}")
  public ResponseEntity<DeviceResponseDto> patchDevice(
      @PathVariable UUID id, @RequestBody DevicePatchDto patch) {
    DeviceResponseDto response = deviceService.patchDevice(id, patch);
    return ResponseEntity.ok(response);
  }

  /**
   * Deletes a device by its ID.
   *
   * @param id the UUID of the device to delete
   * @return HTTP 204 No Content if deletion is successful
   */
  @Operation(summary = "Delete device", description = "Remove a device from the system by ID.")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDevice(@PathVariable UUID id) {
    deviceService.deleteDevice(id);
    return ResponseEntity.noContent().build();
  }
}
