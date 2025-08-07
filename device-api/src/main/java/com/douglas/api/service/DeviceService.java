package com.douglas.api.service;

import com.douglas.api.dto.DevicePatchDTO;
import com.douglas.api.dto.DeviceRequestDTO;
import com.douglas.api.dto.DeviceResponseDTO;
import com.douglas.api.exception.DeviceInUseException;
import com.douglas.api.exception.DeviceNotFoundException;
import com.douglas.api.mapper.DeviceDtoMapper;
import com.douglas.core.domain.Device;
import com.douglas.core.domain.DeviceState;
import com.douglas.persistence.entity.DeviceEntity;
import com.douglas.persistence.mapper.DeviceMapper;
import com.douglas.persistence.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service responsible for business logic and device operations.
 */
@Service
public class DeviceService {

    private final DeviceRepository repository;
    private final DeviceMapper entityMapper;
    private final DeviceDtoMapper dtoMapper;

    public DeviceService(DeviceRepository repository,
                         DeviceMapper entityMapper,
                         DeviceDtoMapper dtoMapper) {
        this.repository = repository;
        this.entityMapper = entityMapper;
        this.dtoMapper = dtoMapper;
    }

    public DeviceResponseDTO createDevice(DeviceRequestDTO request) {
        DeviceEntity entity = new DeviceEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(request.name());
        entity.setBrand(request.brand());
        entity.setState(request.state());
        entity.setCreationTime(Instant.now());

        DeviceEntity savedEntity = repository.save(entity);
        Device savedDevice = entityMapper.toDomain(savedEntity);
        return dtoMapper.toResponseDTO(savedDevice);
    }

    public DeviceResponseDTO getDeviceById(UUID id) {
        DeviceEntity entity = repository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        Device device = entityMapper.toDomain(entity);
        return dtoMapper.toResponseDTO(device);
    }

    public List<DeviceResponseDTO> listDevices(String name, String brand, DeviceState state) {
        List<DeviceEntity> entities = repository.findAll();

        return entities.stream()
                .filter(d -> name == null || d.getName().equalsIgnoreCase(name))
                .filter(d -> brand == null || d.getBrand().equalsIgnoreCase(brand))
                .filter(d -> state == null || d.getState() == state)
                .map(entityMapper::toDomain)
                .map(dtoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DeviceResponseDTO updateDevice(UUID id, DeviceRequestDTO request) {
        DeviceEntity entity = repository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        // Convert to domain model
        Device domainDevice = entityMapper.toDomain(entity);

        // Business rules
        if (!domainDevice.canChangeNameOrBrand()) {
            if ((request.name() != null && !request.name().equals(domainDevice.name())) ||
                    (request.brand() != null && !request.brand().equals(domainDevice.brand()))) {
                throw new DeviceInUseException("Cannot change name or brand while device is in use");
            }
        }

        entity.setState(request.state());
        entity.setName(request.name());
        entity.setBrand(request.brand());

        DeviceEntity updatedEntity = repository.save(entity);
        Device updatedDevice = entityMapper.toDomain(updatedEntity);
        return dtoMapper.toResponseDTO(updatedDevice);
    }

    public DeviceResponseDTO patchDevice(UUID id, DevicePatchDTO patch) {
        DeviceEntity entity = repository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        // Convert to domain model
        Device domainDevice = entityMapper.toDomain(entity);

        // Business rules
        if (!domainDevice.canChangeNameOrBrand()) {
            if ((patch.name() != null && !patch.name().equals(domainDevice.name())) ||
                    (patch.brand() != null && !patch.brand().equals(domainDevice.brand()))) {
                throw new DeviceInUseException("Cannot change name or brand while device is in use");
            }
        }

        // Apply only provided fields
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
        return dtoMapper.toResponseDTO(updatedDevice);
    }

    public void deleteDevice(UUID id) {
        DeviceEntity entity = repository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        // Convert to domain model
        Device domainDevice = entityMapper.toDomain(entity);

        // Business rules
        if (!domainDevice.canBeDeleted()) {
            throw new DeviceInUseException("Cannot delete device in use");
        }

        repository.delete(entity);
    }
}
