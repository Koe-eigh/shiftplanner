package com.shiftplanner.solver.core.utils.values;

import java.util.Objects;

public class LongId {
  private final Long value;
  public LongId(Long value) {
    this.value = validation(value);
  }

  public Long getValue() {
    return value;
  }

  private Long validation(Long value) {
    // 型チェックのため何もしない。
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    LongId other = (LongId) obj;
    return this.value.equals(other.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return Long.toString(this.value);
  }
}
