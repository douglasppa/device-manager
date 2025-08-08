package com.douglas.api.service;

import com.douglas.api.dto.DevicePatchDto;
import com.douglas.api.dto.DeviceRequestDto;
import com.douglas.api.dto.DeviceResponseDto;
import com.douglas.api.exception.DeviceInUseException;
import com.douglas.api.exception.DeviceNotFoundException;
import com.douglas.api.mapper.DeviceDtoMapper;
import com.douglas.core.domain.Device;
import com.douglas.core.domain.DeviceState;
import com.douglas.persistence.entity.DeviceEntity;
import com.douglas.persistence.mapper.DeviceMapper;
import com.douglas.persistence.repository.DeviceRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/** Service responsible for business logic and device operations. */
@Service
public class DeviceService {

  private final DeviceRepository repository;
  private final DeviceMapper entityMapper;
  private final DeviceDtoMapper dtoMapper;

  /**
   * Constructs a {@code DeviceService} with the required dependencies.
   *
   * @param repository the repository for persisting and retrieving {@link DeviceEntity} objects
   * @param entityMapper the mapper for converting between persistence entities and domain models
   * @param dtoMapper the mapper for converting between domain models and DTO representations
   */
  public DeviceService(
      DeviceRepository repository, DeviceMapper entityMapper, DeviceDtoMapper dtoMapper) {
    this.repository = repository;
    this.entityMapper = entityMapper;
    this.dtoMapper = dtoMapper;
  }

  /**
   * Creates a new device in the system.
   *
   * @param request DTO containing the device's name, brand, and state
   * @return the created device as a {@link DeviceResponseDto}
   */
  public DeviceResponseDto createDevice(DeviceRequestDto request) {
    DeviceEntity entity = new DeviceEntity();
    entity.setId(UUID.randomUUID());
    entity.setName(request.name());
    entity.setBrand(request.brand());
    entity.setState(request.state());
    entity.setCreationTime(Instant.now());

    DeviceEntity savedEntity = repository.save(entity);
    Device savedDevice = entityMapper.toDomain(savedEntity);
    return dtoMapper.toResponseDto(savedDevice);
  }

  /**
   * Retrieves a device by its unique identifier.
   *
   * @param id the device's UUID
   * @return the matching device as a {@link DeviceResponseDto}
   * @throws DeviceNotFoundException if no device is found with the given id
   */
  public DeviceResponseDto getDeviceById(UUID id) {
    DeviceEntity entity =
        repository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));

    Device device = entityMapper.toDomain(entity);
    return dtoMapper.toResponseDto(device);
  }

  /**
   * Retrieves all devices matching the optional filter parameters.
   *
   * @param name optional device name to filter by
   * @param brand optional device brand to filter by
   * @param state optional device state to filter by
   * @return list of devices matching the filters
   */
  public List<DeviceResponseDto> listDevices(String name, String brand, DeviceState state) {
    List<DeviceEntity> entities = repository.findAll();

    return entities.stream()
        .filter(d -> name == null || d.getName().equalsIgnoreCase(name))
        .filter(d -> brand == null || d.getBrand().equalsIgnoreCase(brand))
        .filter(d -> state == null || d.getState() == state)
        .map(entityMapper::toDomain)
        .map(dtoMapper::toResponseDto)
        .collect(Collectors.toList());
  }

  /**
   * Fully updates an existing device's information.
   *
   * @param id the device's UUID
   * @param request DTO containing the updated device data
   * @return the updated device as a {@link DeviceResponseDto}
   * @throws DeviceNotFoundException if no device is found with the given id
   * @throws DeviceInUseException if attempting to change name/brand while device is in use
   */
  public DeviceResponseDto updateDevice(UUID id, DeviceRequestDto request) {
    DeviceEntity entity =
        repository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));

    Device domainDevice = entityMapper.toDomain(entity);

    if (!domainDevice.canChangeNameOrBrand()) {
      if ((request.name() != null && !request.name().equals(domainDevice.name()))
          || (request.brand() != null && !request.brand().equals(domainDevice.brand()))) {
        throw new DeviceInUseException("Cannot change name or brand while device is in use");
      }
    }

    entity.setState(request.state());
    entity.setName(request.name());
    entity.setBrand(request.brand());

    DeviceEntity updatedEntity = repository.save(entity);
    Device updatedDevice = entityMapper.toDomain(updatedEntity);
    return dtoMapper.toResponseDto(updatedDevice);
  }

  /**
   * Partially updates an existing device's fields.
   *
   * @param id the device's UUID
   * @param patch DTO containing the fields to update (only non-null values are applied)
   * @return the updated device as a {@link DeviceResponseDto}
   * @throws DeviceNotFoundException if no device is found with the given id
   * @throws DeviceInUseException if attempting to change name/brand while device is in use
   */
  public DeviceResponseDto patchDevice(UUID id, DevicePatchDto patch) {
    DeviceEntity entity =
        repository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));

    Device domainDevice = entityMapper.toDomain(entity);

    if (!domainDevice.canChangeNameOrBrand()) {
      if ((patch.name() != null && !patch.name().equals(domainDevice.name()))
          || (patch.brand() != null && !patch.brand().equals(domainDevice.brand()))) {
        throw new DeviceInUseException("Cannot change name or brand while device is in use");
      }
    }

    if (patch.name() != null) {
      entity.setName(patch.name());
    }
    if (patch.brand() != null) {
      entity.setBrand(patch.brand());
    }
    if (patch.state() != null) {
      entity.setState(patch.state());
    }

    DeviceEntity updatedEntity = repository.save(entity);
    Device updatedDevice = entityMapper.toDomain(updatedEntity);
    return dtoMapper.toResponseDto(updatedDevice);
  }

  /**
   * Deletes a device by its UUID.
   *
   * @param id the device's UUID
   * @throws DeviceNotFoundException if no device is found with the given id
   * @throws DeviceInUseException if the device is currently in use
   */
  public void deleteDevice(UUID id) {
    DeviceEntity entity =
        repository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));

    Device domainDevice = entityMapper.toDomain(entity);

    if (!domainDevice.canBeDeleted()) {
      throw new DeviceInUseException("Cannot delete device in use");
    }

    repository.delete(entity);
  }
}
