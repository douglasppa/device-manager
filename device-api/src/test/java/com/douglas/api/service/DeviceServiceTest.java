package com.douglas.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.douglas.api.dto.DevicePatchDto;
import com.douglas.api.dto.DeviceRequestDto;
import com.douglas.api.dto.DeviceResponseDto;
import com.douglas.api.exception.DeviceInUseException;
import com.douglas.api.exception.DeviceNotFoundException;
import com.douglas.api.mapper.DeviceDtoMapper;
import com.douglas.core.domain.DeviceState;
import com.douglas.persistence.entity.DeviceEntity;
import com.douglas.persistence.mapper.DeviceMapper;
import com.douglas.persistence.repository.DeviceRepository;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class DeviceServiceTest {

  @Mock private DeviceRepository repository;

  private final DeviceMapper entityMapper = new DeviceMapper();

  private final DeviceDtoMapper dtoMapper = new DeviceDtoMapper();

  private DeviceService service;

  private UUID id;
  private DeviceEntity entity;
  private DeviceEntity entityA;
  private DeviceEntity entityB;

  @BeforeEach
  void setup() {
    service = new DeviceService(repository, entityMapper, dtoMapper);

    id = UUID.randomUUID();
    UUID idA = UUID.randomUUID();
    UUID idB = UUID.randomUUID();

    entity = new DeviceEntity();
    entity.setId(id);
    entity.setName("Device X");
    entity.setBrand("Brand A");
    entity.setState(DeviceState.AVAILABLE);
    entity.setCreationTime(Instant.now());

    entityA = new DeviceEntity();
    entityA.setId(idA);
    entityA.setName("Device A");
    entityA.setBrand("Brand X");
    entityA.setState(DeviceState.AVAILABLE);
    entityA.setCreationTime(Instant.now());

    entityB = new DeviceEntity();
    entityB.setId(idB);
    entityB.setName("Device B");
    entityB.setBrand("Brand Y");
    entityB.setState(DeviceState.IN_USE);
    entityB.setCreationTime(Instant.now());
  }

  @Test
  void shouldCreateDeviceSuccessfully() {
    DeviceRequestDto request = new DeviceRequestDto("Device X", "Brand A", DeviceState.AVAILABLE);

    when(repository.save(any())).thenReturn(entity);

    DeviceResponseDto expected = dtoMapper.toResponseDto(entityMapper.toDomain(entity));

    DeviceResponseDto result = service.createDevice(request);

    assertNotNull(result);
    assertEquals(expected.name(), result.name());
    assertEquals(expected.brand(), result.brand());
    assertEquals(expected.state(), result.state());
    verify(repository).save(any());
  }

  @Test
  void shouldReturnDeviceById() {
    when(repository.findById(id)).thenReturn(Optional.of(entity));

    DeviceResponseDto expected = dtoMapper.toResponseDto(entityMapper.toDomain(entity));

    DeviceResponseDto result = service.getDeviceById(id);

    assertNotNull(result);
    assertEquals(expected.id(), result.id());
    assertEquals(expected.name(), result.name());
    assertEquals(expected.brand(), result.brand());
    assertEquals(expected.state(), result.state());
    verify(repository).findById(id);
  }

  @Test
  void shouldThrowExceptionWhenDeviceNotFound() {
    when(repository.findById(id)).thenReturn(Optional.empty());

    assertThrows(DeviceNotFoundException.class, () -> service.getDeviceById(id));
  }

  @Test
  void shouldReturnAllDevicesWhenNoFilterApplied() {
    when(repository.findAll()).thenReturn(List.of(entityA, entityB));

    List<DeviceResponseDto> expected =
        List.of(
            dtoMapper.toResponseDto(entityMapper.toDomain(entityA)),
            dtoMapper.toResponseDto(entityMapper.toDomain(entityB)));

    List<DeviceResponseDto> result = service.listDevices(null, null, null);

    assertEquals(expected, result);
    verify(repository).findAll();
  }

  @Test
  void shouldFilterByNameOnly() {
    when(repository.findAll()).thenReturn(List.of(entityA, entityB));

    List<DeviceResponseDto> result = service.listDevices("Device A", null, null);

    assertEquals(1, result.size());
    assertEquals("Device A", result.getFirst().name());

    verify(repository).findAll();
  }

  @Test
  void shouldFilterByBrandOnly() {
    when(repository.findAll()).thenReturn(List.of(entityA, entityB));

    List<DeviceResponseDto> result = service.listDevices(null, "Brand X", null);

    assertEquals(1, result.size());
    assertEquals("Brand X", result.getFirst().brand());

    verify(repository).findAll();
  }

  @Test
  void shouldFilterByStateOnly() {
    when(repository.findAll()).thenReturn(List.of(entityA, entityB));

    List<DeviceResponseDto> result = service.listDevices(null, null, DeviceState.IN_USE);

    assertEquals(1, result.size());
    assertEquals(DeviceState.IN_USE, result.getFirst().state());

    verify(repository).findAll();
  }

  @Test
  void shouldFilterByNameAndBrandAndState() {
    when(repository.findAll()).thenReturn(List.of(entityA, entityB));

    List<DeviceResponseDto> result =
        service.listDevices("Device A", "Brand X", DeviceState.AVAILABLE);

    assertEquals(1, result.size());
    assertEquals("Device A", result.getFirst().name());

    verify(repository).findAll();
  }

  @Test
  void shouldReturnEmptyListWhenNoMatch() {
    when(repository.findAll()).thenReturn(List.of(entityA, entityB));

    List<DeviceResponseDto> result = service.listDevices("Nonexistent", null, null);

    assertTrue(result.isEmpty());
    verify(repository).findAll();
  }

  @Test
  void shouldUpdateDeviceSuccessfully() {
    DeviceRequestDto update = new DeviceRequestDto("Device X", "Brand A", DeviceState.INACTIVE);

    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(repository.save(any())).thenReturn(entity);

    DeviceResponseDto expected = dtoMapper.toResponseDto(entityMapper.toDomain(entity));

    DeviceResponseDto result = service.updateDevice(id, update);

    assertEquals(expected.name(), result.name());
    assertEquals(DeviceState.INACTIVE, result.state());

    verify(repository).save(entity);
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNonexistentDevice() {
    DeviceRequestDto update = new DeviceRequestDto("Any", "Any", DeviceState.AVAILABLE);

    when(repository.findById(id)).thenReturn(Optional.empty());

    assertThrows(DeviceNotFoundException.class, () -> service.updateDevice(id, update));
  }

  @Test
  void shouldAllowUpdateWhenInUseButNameAndBrandAreUnchanged() {
    entity.setState(DeviceState.IN_USE);
    DeviceRequestDto update = new DeviceRequestDto("Device X", "Brand A", DeviceState.IN_USE);

    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(repository.save(any())).thenReturn(entity);

    DeviceResponseDto result = service.updateDevice(id, update);

    assertNotNull(result);
    assertEquals("Device X", result.name());
    assertEquals(DeviceState.IN_USE, result.state());
  }

  @Test
  void shouldThrowWhenTryingToChangeNameInUse() {
    entity.setState(DeviceState.IN_USE);
    DeviceRequestDto update = new DeviceRequestDto("New Name", "Brand A", DeviceState.IN_USE);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    assertThrows(DeviceInUseException.class, () -> service.updateDevice(id, update));
  }

  @Test
  void shouldPatchStateOnly() {
    DevicePatchDto patch = new DevicePatchDto(null, null, DeviceState.INACTIVE);

    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(repository.save(any())).thenReturn(entity);

    DeviceResponseDto result = service.patchDevice(id, patch);

    assertEquals(DeviceState.INACTIVE, result.state());
    verify(repository).save(entity);
  }

  @Test
  void shouldThrowExceptionWhenPatchingNonexistentDevice() {
    DevicePatchDto patch = new DevicePatchDto("Any", "Any", DeviceState.INACTIVE);

    when(repository.findById(id)).thenReturn(Optional.empty());

    assertThrows(DeviceNotFoundException.class, () -> service.patchDevice(id, patch));
  }

  @Test
  void shouldAllowPatchWhenInUseButNameAndBrandAreUnchanged() {
    entity.setState(DeviceState.IN_USE);
    DevicePatchDto patch = new DevicePatchDto("Device X", "Brand A", DeviceState.IN_USE);

    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(repository.save(any())).thenReturn(entity);

    DeviceResponseDto result = service.patchDevice(id, patch);

    assertNotNull(result);
    assertEquals("Device X", result.name());
    assertEquals(DeviceState.IN_USE, result.state());
  }

  @Test
  void shouldThrowWhenPatchingInUseDevice() {
    entity.setState(DeviceState.IN_USE);
    DevicePatchDto patch = new DevicePatchDto("New Name", null, null);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    assertThrows(DeviceInUseException.class, () -> service.patchDevice(id, patch));
  }

  @Test
  void shouldDeleteDevice() {
    when(repository.findById(id)).thenReturn(Optional.of(entity));

    service.deleteDevice(id);

    verify(repository).delete(entity);
  }

  @Test
  void shouldThrowExceptionWhenDeletingNonexistentDevice() {
    when(repository.findById(id)).thenReturn(Optional.empty());

    assertThrows(DeviceNotFoundException.class, () -> service.deleteDevice(id));
  }

  @Test
  void shouldThrowWhenDeletingDeviceInUse() {
    entity.setState(DeviceState.IN_USE);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    assertThrows(DeviceInUseException.class, () -> service.deleteDevice(id));
    verify(repository, never()).delete(any());
  }

  @Test
  void testDeviceNotFoundExceptionMessage() {
    DeviceNotFoundException ex = new DeviceNotFoundException(id);
    assertTrue(ex.getMessage().contains(id.toString()));
  }
}
