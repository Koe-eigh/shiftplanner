package com.shiftplanner.solver.core.utils;

import java.util.Locale;
import java.util.Objects;

public class PersonName {

  private static final int MIN_LENGTH = 2;
  private static final int MAX_LENGTH = 50;

  private final String firstName;
  private final String lastName;

  public PersonName(String firstName, String lastName) {
    this.firstName = validateName(firstName, "First name");
    this.lastName = validateName(lastName, "Last name");
  }

  private String validateName(String name, String fieldName) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
    }
    if (name.contains(" ")) {
      throw new IllegalArgumentException(fieldName + " cannot contain spaces.");
    }
    if (!name.matches("^[A-Za-zぁ-んァ-ヶ一-龠ー]+$")) {
      throw new IllegalArgumentException(fieldName + " must only contain letters.");
    }
    if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          fieldName + " must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters.");
    }
    return name;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  /**
   * フルネームを取得する
   * - 日本語なら「姓 名」
   * - それ以外（デフォルト: 欧米）は「名 姓」
   */
  public String getFullName(Locale locale) {
    if (locale.equals(Locale.JAPAN) || locale.equals(Locale.JAPANESE)) {
      return this.lastName + " " + this.firstName;
    }
    return this.firstName + " " + this.lastName;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    PersonName that = (PersonName) obj;
    return this.firstName.equals(that.firstName) && this.lastName.equals(that.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.firstName, this.lastName);
  }

  @Override
  public String toString() {
    return getFullName(Locale.getDefault());
  }
}
