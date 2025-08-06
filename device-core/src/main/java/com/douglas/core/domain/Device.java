package com.douglas.core.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Device {

    private final UUID id;
    private final String name;
    private final String brand;
    private final DeviceState state;
    private final Instant creationTime;

    public Device(UUID id, String name, String brand, DeviceState state, Instant creationTime) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.state = state;
        this.creationTime = creationTime;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public DeviceState getState() { return state; }
    public Instant getCreationTime() { return creationTime; }

    public boolean isInUse() {
        return DeviceState.IN_USE.equals(this.state);
    }

    public boolean canBeDeleted() {
        return !isInUse();
    }

    public boolean canChangeNameOrBrand() {
        return !isInUse();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device device)) return false;
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
