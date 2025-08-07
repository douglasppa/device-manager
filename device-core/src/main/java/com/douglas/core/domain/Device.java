package com.douglas.core.domain;

import java.time.Instant;
import java.util.UUID;

public record Device(UUID id, String name, String brand, DeviceState state, Instant creationTime) {

    public boolean isInUse() {
        return DeviceState.IN_USE.equals(this.state);
    }

    public boolean canBeDeleted() {
        return !isInUse();
    }

    public boolean canChangeNameOrBrand() {
        return !isInUse();
    }
}
