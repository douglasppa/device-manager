package com.douglas.api.mapper;

import com.douglas.api.dto.DeviceResponseDTO;
import com.douglas.core.domain.Device;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert Device (domain) to DTOs.
 */
@Component
public class DeviceDtoMapper {

    public DeviceResponseDTO toResponseDTO(Device device) {
        return new DeviceResponseDTO(
                device.id(),
                device.name(),
                device.brand(),
                device.state(),
                device.creationTime()
        );
    }
}
