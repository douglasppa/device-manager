package com.douglas.persistence.mapper;

import com.douglas.core.domain.Device;
import com.douglas.persistence.entity.DeviceEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between DeviceEntity, Domain, and DTO.
 */
@Component
public class DeviceMapper {

    public static DeviceEntity toEntity(Device device) {
        DeviceEntity entity = new DeviceEntity();
        entity.setId(device.id());
        entity.setName(device.name());
        entity.setBrand(device.brand());
        entity.setState(device.state());
        entity.setCreationTime(device.creationTime());
        return entity;
    }

    public Device toDomain(DeviceEntity entity) {
        return new Device(
                entity.getId(),
                entity.getName(),
                entity.getBrand(),
                entity.getState(),
                entity.getCreationTime()
        );
    }
}
