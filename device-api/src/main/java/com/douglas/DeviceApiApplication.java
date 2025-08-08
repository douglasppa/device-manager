package com.douglas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Entry point for the Device API application. */
@SpringBootApplication(scanBasePackages = "com.douglas")
public class DeviceApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(DeviceApiApplication.class, args);
  }
}
