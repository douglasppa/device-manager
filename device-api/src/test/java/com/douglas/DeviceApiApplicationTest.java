package com.douglas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class DeviceApiApplicationTest {

  @Test
  void mainShouldRunWithWebAppDisabled() {
    assertDoesNotThrow(
        () -> {
          DeviceApiApplication.main(new String[] {"--spring.main.web-application-type=none"});
        });
  }
}
