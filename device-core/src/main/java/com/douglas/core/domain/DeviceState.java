package com.douglas.core.domain;

/** Enumeration representing the possible states of a device. */
public enum DeviceState {

  /** Device is available for use. */
  AVAILABLE,

  /** Device is currently in use and cannot be modified or deleted. */
  IN_USE,

  /** Device is inactive and not in operation. */
  INACTIVE
}
