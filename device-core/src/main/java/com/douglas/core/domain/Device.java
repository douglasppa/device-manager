package com.douglas.core.domain;

import java.time.Instant;
import java.util.Objects;

public class Device {

    private final Long id;
    private final String name;
    private final String brand;
    private final DeviceState state;
    private final Instant creationTime;

    public Device(Long id, String name, String brand, DeviceState state, Instant creationTime) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.state = state;
        this.creationTime = creationTime;
    }

    public Long getId() { return id; }
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
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
