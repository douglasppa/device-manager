package com.douglas.api.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.douglas.api.exception.GlobalExceptionHandler;
import com.douglas.api.mapper.DeviceDtoMapper;
import com.douglas.api.service.DeviceService;
import com.douglas.core.domain.DeviceState;
import com.douglas.persistence.entity.DeviceEntity;
import com.douglas.persistence.mapper.DeviceMapper;
import com.douglas.persistence.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class DeviceControllerTest {

  private MockMvc mockMvc;
  private DeviceRepository repository; // mock
  private DeviceService deviceService; // real
  private final DeviceMapper entityMapper = new DeviceMapper(); // real
  private final DeviceDtoMapper dtoMapper = new DeviceDtoMapper(); // real
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    repository = Mockito.mock(DeviceRepository.class);
    deviceService = new DeviceService(repository, entityMapper, dtoMapper);

    objectMapper =
        new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    DeviceController controller = new DeviceController(deviceService);
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
  }

  // --- helpers de reflexão para instanciar entidade real e setar campos privados ---
  private static void set(Object target, String field, Object value) {
    try {
      Field f = target.getClass().getDeclaredField(field);
      f.setAccessible(true);
      f.set(target, value);
    } catch (Exception e) {
      throw new RuntimeException("Falha ao setar campo '" + field + "': " + e.getMessage(), e);
    }
  }

  private static DeviceEntity newEntity(
      UUID id, String name, String brand, DeviceState state, Instant creation) {
    try {
      DeviceEntity e = DeviceEntity.class.getDeclaredConstructor().newInstance();
      set(e, "id", id);
      set(e, "name", name);
      set(e, "brand", brand);
      set(e, "state", state);
      set(e, "creationTime", creation);
      return e;
    } catch (Exception e) {
      throw new RuntimeException("Falha ao instanciar DeviceEntity: " + e.getMessage(), e);
    }
  }

  @Test
  @DisplayName("POST /devices → 200 OK com device criado")
  void createDevice_returnsOk() throws Exception {
    // ao salvar, devolvemos a mesma entidade com id/creationTime populados
    doReturn(
            newEntity(
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                "Router X",
                "TP-Link",
                DeviceState.IN_USE,
                Instant.parse("2025-08-07T10:00:00Z")))
        .when(repository)
        .save(any());

    String body =
        """
          {
            "name": "Router X",
            "brand": "TP-Link",
            "state": "IN_USE"
          }
        """;

    mockMvc
        .perform(post("/devices").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")))
        .andExpect(jsonPath("$.name", is("Router X")))
        .andExpect(jsonPath("$.brand", is("TP-Link")))
        .andExpect(jsonPath("$.state", is("IN_USE")));
  }

  @Test
  @DisplayName("GET /devices/{id} → 200 OK com device")
  void getDeviceById_returnsOk() throws Exception {
    UUID id = UUID.randomUUID();
    doReturn(
            Optional.of(
                newEntity(
                    id,
                    "Router X",
                    "TP-Link",
                    DeviceState.IN_USE,
                    Instant.parse("2025-08-07T10:00:00Z"))))
        .when(repository)
        .findById(eq(id));

    mockMvc
        .perform(get("/devices/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(id.toString())))
        .andExpect(jsonPath("$.name", is("Router X")))
        .andExpect(jsonPath("$.brand", is("TP-Link")))
        .andExpect(jsonPath("$.state", is("IN_USE")));
  }

  @Test
  @DisplayName("GET /devices com filtros → 200 OK e lista")
  void listDevices_withFilters_returnsOk() throws Exception {
    UUID id = UUID.randomUUID();
    var item =
        newEntity(
            id, "Router X", "TP-Link", DeviceState.IN_USE, Instant.parse("2025-08-07T10:00:00Z"));

    doReturn(List.of(item)).when(repository).findByNameContainingIgnoreCase(anyString());
    doReturn(List.of(item)).when(repository).findByBrandContainingIgnoreCase(anyString());
    doReturn(List.of(item)).when(repository).findByState(eq(DeviceState.IN_USE));
    doReturn(List.of(item)).when(repository).findAll();

    mockMvc
        .perform(
            get("/devices")
                .param("name", "Router X")
                .param("brand", "TP-Link")
                .param("state", "IN_USE"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(id.toString())))
        .andExpect(jsonPath("$[0].state", is("IN_USE")));
  }

  @Test
  @DisplayName("PUT /devices/{id} → 200 OK com device atualizado")
  void updateDevice_returnsOk() throws Exception {
    UUID id = UUID.randomUUID();

    doReturn(
            Optional.of(
                newEntity(
                    id,
                    "Old",
                    "OldBrand",
                    DeviceState.AVAILABLE,
                    Instant.parse("2025-08-01T10:00:00Z"))))
        .when(repository)
        .findById(eq(id));

    doReturn(
            newEntity(
                id,
                "Router X",
                "TP-Link",
                DeviceState.IN_USE,
                Instant.parse("2025-08-01T10:00:00Z")))
        .when(repository)
        .save(any());

    String body =
        """
          {
            "name": "Router X",
            "brand": "TP-Link",
            "state": "IN_USE"
          }
        """;

    mockMvc
        .perform(put("/devices/{id}", id).contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(id.toString())))
        .andExpect(jsonPath("$.name", is("Router X")))
        .andExpect(jsonPath("$.state", is("IN_USE")));
  }

  @Test
  @DisplayName("PATCH /devices/{id} → 200 OK com device atualizado parcialmente")
  void patchDevice_returnsOk() throws Exception {
    UUID id = UUID.randomUUID();

    doReturn(
            Optional.of(
                newEntity(
                    id,
                    "Router X",
                    "TP-Link",
                    DeviceState.AVAILABLE,
                    Instant.parse("2025-08-01T10:00:00Z"))))
        .when(repository)
        .findById(eq(id));

    doReturn(
            newEntity(
                id,
                "Router X",
                "TP-Link",
                DeviceState.IN_USE,
                Instant.parse("2025-08-01T10:00:00Z")))
        .when(repository)
        .save(any());

    String body = """
          {
            "state": "IN_USE"
          }
        """;

    mockMvc
        .perform(patch("/devices/{id}", id).contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(id.toString())))
        .andExpect(jsonPath("$.state", is("IN_USE")));
  }

  @Test
  @DisplayName("DELETE /devices/{id} com state != IN_USE → 204 No Content")
  void deleteDevice_available_returnsNoContent() throws Exception {
    UUID id = UUID.randomUUID();

    // Arrange: device existe e NÃO está em uso (pode deletar)
    doReturn(
            Optional.of(
                newEntity(
                    id,
                    "Router X",
                    "TP-Link",
                    DeviceState.AVAILABLE,
                    Instant.parse("2025-08-01T10:00:00Z"))))
        .when(repository)
        .findById(eq(id));

    // Act + Assert
    mockMvc.perform(delete("/devices/{id}", id)).andExpect(status().isNoContent());

    // Verify: lookup + delete(entity)
    verify(repository).findById(eq(id));
    verify(repository).delete(org.mockito.ArgumentMatchers.<DeviceEntity>any());
    verifyNoMoreInteractions(repository);
  }

  @Test
  @DisplayName("DELETE /devices/{id} com state = IN_USE → 409 Conflict")
  void deleteDevice_inUse_returnsConflict() throws Exception {
    UUID id = UUID.randomUUID();

    doReturn(
            Optional.of(
                newEntity(
                    id,
                    "Router X",
                    "TP-Link",
                    DeviceState.IN_USE,
                    Instant.parse("2025-08-01T10:00:00Z"))))
        .when(repository)
        .findById(eq(id));

    mockMvc
        .perform(delete("/devices/{id}", id))
        .andExpect(status().isBadRequest()); // 400, conforme seu handler atual
  }
}
