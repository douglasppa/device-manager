package com.douglas.core.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DeviceTest {

  private final UUID id = UUID.randomUUID();
  private final Instant now = Instant.now();

  @Test
  void shouldReturnTrueWhenDeviceIsInUse() {
    Device device = new Device(id, "Device A", "Brand X", DeviceState.IN_USE, now);

    assertTrue(device.isInUse());
  }

  @Test
  void shouldReturnFalseWhenDeviceIsNotInUse() {
    Device device = new Device(id, "Device A", "Brand X", DeviceState.AVAILABLE, now);

    assertFalse(device.isInUse());
  }

  @Test
  void canBeDeletedShouldReturnFalseWhenInUse() {
    Device device = new Device(id, "Device A", "Brand X", DeviceState.IN_USE, now);

    assertFalse(device.canBeDeleted());
  }

  @Test
  void canBeDeletedShouldReturnTrueWhenNotInUse() {
    Device device = new Device(id, "Device A", "Brand X", DeviceState.INACTIVE, now);

    assertTrue(device.canBeDeleted());
  }

  @Test
  void canChangeNameOrBrandShouldReturnFalseWhenInUse() {
    Device device = new Device(id, "Device A", "Brand X", DeviceState.IN_USE, now);

    assertFalse(device.canChangeNameOrBrand());
  }

  @Test
  void canChangeNameOrBrandShouldReturnTrueWhenNotInUse() {
    Device device = new Device(id, "Device A", "Brand X", DeviceState.AVAILABLE, now);

    assertTrue(device.canChangeNameOrBrand());
  }
}
