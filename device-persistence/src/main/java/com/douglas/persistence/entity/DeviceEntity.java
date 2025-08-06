package com.douglas.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import com.douglas.core.domain.DeviceState;

@Entity
@Table(name = "devices")
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;

    @Enumerated(EnumType.STRING)
    private DeviceState state;

    @Column(name = "creation_time", updatable = false)
    private Instant creationTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public DeviceState getState() { return state; }
    public void setState(DeviceState state) { this.state = state; }

    public Instant getCreationTime() { return creationTime; }
    public void setCreationTime(Instant creationTime) { this.creationTime = creationTime; }
}
