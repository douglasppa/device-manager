package com.douglas.persistence.repository;

import com.douglas.core.domain.DeviceState;
import com.douglas.persistence.entity.DeviceEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link DeviceEntity} records. Extends {@link
 * JpaRepository} to provide CRUD operations and adds custom query methods for filtering devices by
 * name, brand, or state.
 */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {

  /**
   * Finds devices whose name contains the given string (case-insensitive).
   *
   * @param name the substring to search for in the device name
   * @return a list of matching {@link DeviceEntity} objects
   */
  List<DeviceEntity> findByNameContainingIgnoreCase(String name);

  /**
   * Finds devices whose brand contains the given string (case-insensitive).
   *
   * @param brand the substring to search for in the device brand
   * @return a list of matching {@link DeviceEntity} objects
   */
  List<DeviceEntity> findByBrandContainingIgnoreCase(String brand);

  /**
   * Finds devices by their current state.
   *
   * @param state the {@link DeviceState} to filter by
   * @return a list of matching {@link DeviceEntity} objects
   */
  List<DeviceEntity> findByState(DeviceState state);
}
