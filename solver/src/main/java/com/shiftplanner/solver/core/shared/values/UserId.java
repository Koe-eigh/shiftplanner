package com.shiftplanner.solver.core.shared.values;

import java.util.UUID;

public class UserId {
  private final UUID value;

  public UserId(String value) {
    this.value = UUID.fromString(value);
  }

  public UUID getValue() {
    return value;
  }
}
