package com.shiftplanner.solver.core.scheduling.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.shiftplanner.solver.core.utils.values.PersonName;

import java.util.Locale;

class PersonNameTest {
  @Test
  void validNameShouldBeCreated() {
    PersonName name = new PersonName("John", "Doe");
    assertEquals("John Doe", name.getFullName(Locale.US));
    assertEquals("Doe John", name.getFullName(Locale.JAPAN));
  }

  @Test
  void emptyFirstNameShouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new PersonName("", "Doe"));
  }

  @Test
  void emptyLastNameShouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new PersonName("John", ""));
  }

  @Test
  void nameWithSpacesShouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new PersonName("John ", "Doe"));
  }

  @Test
  void nameWithNumbersShouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new PersonName("John123", "Doe"));
  }

  @Test
  void nameWithSpecialCharactersShouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new PersonName("John@", "Doe"));
  }

  @Test
  void namesWithSameValueShouldBeEqual() {
    PersonName name1 = new PersonName("John", "Doe");
    PersonName name2 = new PersonName("John", "Doe");
    assertEquals(name1, name2);
  }

  @Test
  void toStringShouldReturnCorrectFullName() {
    PersonName name = new PersonName("John", "Doe");
    assertEquals("John Doe", name.toString());
  }
}
