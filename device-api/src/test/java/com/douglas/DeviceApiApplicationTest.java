package com.douglas;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

class DeviceApiApplicationTest {

  @Test
  void ctorShouldBeCallable() {
    // cobre a linha da declaração/classe ao chamar o construtor padrão
    DeviceApiApplication app = new DeviceApiApplication();
    assertNotNull(app);
  }

  @Test
  void mainShouldInvokeSpringApplicationRun() {
    try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {
      mocked
          .when(() -> SpringApplication.run(eq(DeviceApiApplication.class), any(String[].class)))
          .thenReturn(null);

      DeviceApiApplication.main(new String[0]);

      mocked.verify(
          () -> SpringApplication.run(eq(DeviceApiApplication.class), any(String[].class)),
          times(1));
    }
  }
}
