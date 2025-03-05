package com.shiftplanner.solver.core.scheduling.valueObjects;

import java.util.Arrays;

public enum StudentGrade {
  PRIMARY_1(1),
  PRIMARY_2(2),
  PRIMARY_3(3),
  PRIMARY_4(4),
  PRIMARY_5(5),
  PRIMARY_6(6),
  JUNIOR_1(7),
  JUNIOR_2(8),
  JUNIOR_3(9),
  HIGH_1(10),
  HIGH_2(11),
  HIGH_3(12),
  OTHER(13);

  private final int id;
  private final String name;

  private StudentGrade(int id) {
    this.id = id;
    if (id == 1) {
      this.name = "小学1年生";
    } else if (id == 2) {
      this.name = "小学2年生";
    } else if (id == 3) {
      this.name = "小学3年生";
    } else if (id == 4) {
      this.name = "小学4年生";
    } else if (id == 5) {
      this.name = "小学5年生";
    } else if (id == 6) {
      this.name = "小学6年生";
    } else if (id == 7) {
      this.name = "中学1年生";
    } else if (id == 8) {
      this.name = "中学2年生";
    } else if (id == 9) {
      this.name = "中学3年生";
    } else if (id == 10) {
      this.name = "高校1年生";
    } else if (id == 11) {
      this.name = "高校2年生";
    } else if (id == 12) {
      this.name = "高校3年生";
    } else {
      this.name = "その他";
    }
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public static StudentGrade fromId(int id) {
    return StudentGrade.values()[id - 1];
  }

  public static StudentGrade fromString(String name) {
    return Arrays.stream(StudentGrade.values()).filter(grade -> grade.getName().equals(name)).findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid student grade: " + name));
  }
}
