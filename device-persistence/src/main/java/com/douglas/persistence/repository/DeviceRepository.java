package com.douglas.persistence.repository;

import com.douglas.persistence.entity.DeviceEntity;
import com.douglas.core.domain.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    List<DeviceEntity> findByNameContainingIgnoreCase(String name);
    List<DeviceEntity> findByBrandContainingIgnoreCase(String brand);
    List<DeviceEntity> findByState(DeviceState state);
}
