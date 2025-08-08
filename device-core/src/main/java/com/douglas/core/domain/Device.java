package com.douglas.core.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain model representing a device with its attributes and business rules.
 *
 * @param id unique identifier of the device
 * @param name name of the device
 * @param brand brand of the device
 * @param state current {@link DeviceState} of the device
 * @param creationTime timestamp when the device was created
 */
public record Device(UUID id, String name, String brand, DeviceState state, Instant creationTime) {

  /**
   * Checks if the device is currently in use.
   *
   * @return {@code true} if the device state is {@link DeviceState#IN_USE}, otherwise {@code false}
   */
  public boolean isInUse() {
    return DeviceState.IN_USE.equals(this.state);
  }

  /**
   * Determines whether the device can be deleted.
   *
   * @return {@code true} if the device is not in use, otherwise {@code false}
   */
  public boolean canBeDeleted() {
    return !isInUse();
  }

  /**
   * Determines whether the device's name or brand can be changed.
   *
   * @return {@code true} if the device is not in use, otherwise {@code false}
   */
  public boolean canChangeNameOrBrand() {
    return !isInUse();
  }
}
