package com.douglas.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.douglas.core.domain.Device;
import com.douglas.core.domain.DeviceState;
import com.douglas.persistence.entity.DeviceEntity;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DeviceMapperTest {

  private final DeviceMapper mapper = new DeviceMapper();

  @Test
  void shouldConvertDomainToEntity() {
    UUID id = UUID.randomUUID();
    Instant now = Instant.now();
    Device domain = new Device(id, "Device X", "Brand A", DeviceState.IN_USE, now);

    DeviceEntity entity = DeviceMapper.toEntity(domain);

    assertNotNull(entity);
    assertEquals(domain.id(), entity.getId());
    assertEquals(domain.name(), entity.getName());
    assertEquals(domain.brand(), entity.getBrand());
    assertEquals(domain.state(), entity.getState());
    assertEquals(domain.creationTime(), entity.getCreationTime());
  }

  @Test
  void shouldConvertEntityToDomain() {
    UUID id = UUID.randomUUID();
    Instant now = Instant.now();
    DeviceEntity entity = new DeviceEntity();
    entity.setId(id);
    entity.setName("Device X");
    entity.setBrand("Brand A");
    entity.setState(DeviceState.AVAILABLE);
    entity.setCreationTime(now);

    Device domain = mapper.toDomain(entity);

    assertNotNull(domain);
    assertEquals(entity.getId(), domain.id());
    assertEquals(entity.getName(), domain.name());
    assertEquals(entity.getBrand(), domain.brand());
    assertEquals(entity.getState(), domain.state());
    assertEquals(entity.getCreationTime(), domain.creationTime());
  }
}
