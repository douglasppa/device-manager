package com.douglas.persistence.entity;

import com.douglas.core.domain.DeviceState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * JPA entity representing a device record in the database. Maps to the {@code devices} table and
 * stores details such as name, brand, state, and creation timestamp.
 */
@Entity
@Table(name = "devices")
public class DeviceEntity {

  @Id
  @Column(columnDefinition = "uuid", updatable = false, nullable = false)
  private UUID id;

  private String name;
  private String brand;

  @Enumerated(EnumType.STRING)
  private DeviceState state;

  @Column(name = "creation_time", updatable = false)
  private Instant creationTime;

  /**
   * Returns the unique identifier of the device.
   *
   * @return the device UUID
   */
  public UUID getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the device.
   *
   * @param id the device UUID
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**
   * Returns the name of the device.
   *
   * @return the device name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the device.
   *
   * @param name the device name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the brand of the device.
   *
   * @return the device brand
   */
  public String getBrand() {
    return brand;
  }

  /**
   * Sets the brand of the device.
   *
   * @param brand the device brand
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**
   * Returns the current state of the device.
   *
   * @return the device state
   */
  public DeviceState getState() {
    return state;
  }

  /**
   * Sets the current state of the device.
   *
   * @param state the device state
   */
  public void setState(DeviceState state) {
    this.state = state;
  }

  /**
   * Returns the creation timestamp of the device.
   *
   * @return the creation time
   */
  public Instant getCreationTime() {
    return creationTime;
  }

  /**
   * Sets the creation timestamp of the device.
   *
   * @param creationTime the creation time
   */
  public void setCreationTime(Instant creationTime) {
    this.creationTime = creationTime;
  }
}
