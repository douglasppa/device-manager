package com.douglas.persistence.mapper;

import com.douglas.core.domain.Device;
import com.douglas.persistence.entity.DeviceEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper responsible for converting between {@link DeviceEntity} (persistence layer) and {@link
 * Device} (domain model).
 */
@Component
public class DeviceMapper {

  /**
   * Converts a {@link Device} domain object into a {@link DeviceEntity} for persistence.
   *
   * @param device the domain device to be converted
   * @return the corresponding persistence entity
   */
  public static DeviceEntity toEntity(Device device) {
    DeviceEntity entity = new DeviceEntity();
    entity.setId(device.id());
    entity.setName(device.name());
    entity.setBrand(device.brand());
    entity.setState(device.state());
    entity.setCreationTime(device.creationTime());
    return entity;
  }

  /**
   * Converts a {@link DeviceEntity} from persistence into a {@link Device} domain object.
   *
   * @param entity the persistence entity to be converted
   * @return the corresponding domain device
   */
  public Device toDomain(DeviceEntity entity) {
    return new Device(
        entity.getId(),
        entity.getName(),
        entity.getBrand(),
        entity.getState(),
        entity.getCreationTime());
  }
}
