package com.douglas.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

import com.douglas.core.domain.DeviceState;

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

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public DeviceState getState() { return state; }
    public void setState(DeviceState state) { this.state = state; }

    public Instant getCreationTime() { return creationTime; }
    public void setCreationTime(Instant creationTime) { this.creationTime = creationTime; }
}
