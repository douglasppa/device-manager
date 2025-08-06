package com.douglas.persistence.mapper;

import com.douglas.core.domain.Device;
import com.douglas.persistence.entity.DeviceEntity;

public class DeviceMapper {

    public static DeviceEntity toEntity(Device device) {
        DeviceEntity entity = new DeviceEntity();
        entity.setId(device.getId());
        entity.setName(device.getName());
        entity.setBrand(device.getBrand());
        entity.setState(device.getState());
        entity.setCreationTime(device.getCreationTime());
        return entity;
    }

    public static Device toDomain(DeviceEntity entity) {
        return new Device(
                entity.getId(),
                entity.getName(),
                entity.getBrand(),
                entity.getState(),
                entity.getCreationTime()
        );
    }
}
