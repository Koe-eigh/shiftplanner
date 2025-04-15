package com.shiftplanner.solver.core.scheduling.values;

import java.util.Objects;

public class TimetableName {
  private final String value;

  public TimetableName(String value) {
    this.validate(value);
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  private void validate(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Timetable name must not be empty.");
    }
    if (value.length() > 50) {
      throw new IllegalArgumentException("Timetable name must not exceed 50 characters.");
    }
    if (!value.matches("^[a-zA-Z0-9ぁ-んァ-ヶ一-龯ー\\s\\-_()（）]+$")) {
      throw new IllegalArgumentException("Timetable name contains invalid characters.");
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || this.getClass() != obj.getClass())
      return false;
    TimetableName other = (TimetableName) obj;
    return this.value.equals(other.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }

  @Override
  public String toString() {
    return this.value;
  }
}
