package com.douglas.api.mapper;

import com.douglas.api.dto.DeviceResponseDto;
import com.douglas.core.domain.Device;
import org.springframework.stereotype.Component;

/**
 * Mapper responsible for converting a {@link Device} domain object into its corresponding {@link
 * DeviceResponseDto} representation.
 */
@Component
public class DeviceDtoMapper {

  /**
   * Converts a {@link Device} domain object into a {@link DeviceResponseDto} to be returned in API
   * responses.
   *
   * @param device the domain device to convert
   * @return the corresponding DTO with device data
   */
  public DeviceResponseDto toResponseDto(Device device) {
    return new DeviceResponseDto(
        device.id(), device.name(), device.brand(), device.state(), device.creationTime());
  }
}
