package com.shiftplanner.solver.core.utils;

import java.util.Objects;
import java.util.UUID;

public class StringId {

  private final String value;

  /**
   * コンストラクタ（バリデーション付き）
   */
  public StringId(String value) {
    this.value = validate(value);
  }

  /**
   * ランダムな ID を生成（UUID ベース）
   */
  public static StringId random() {
    return new StringId(UUID.randomUUID().toString());
  }

  /**
   * ID のバリデーション
   */
  private String validate(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("ID cannot be null or empty.");
    }
    if (!value.matches("^[a-zA-Z0-9_-]+$")) {
      throw new IllegalArgumentException("ID must contain only alphanumeric characters, '-', or '_'.");
    }
    return value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    StringId stringId = (StringId) obj;
    return value.equals(stringId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value;
  }
}
