package com.douglas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DeviceApiApplicationTest {

    @Test
    void mainShouldRunWithWebAppDisabled() {
        assertDoesNotThrow(() -> {
            DeviceApiApplication.main(new String[] {
                    "--spring.main.web-application-type=none"
            });
        });
    }

}
