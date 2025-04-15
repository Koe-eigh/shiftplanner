package com.shiftplanner.solver.core.scheduling.values;

/**
 * 科目を管理するクラス
 */
public enum Subject {
  // 小学科目
  PRIMARY_MATH(1),
  PRIMARY_JAPANESE(2),

  // 中受科目
  J_EXAM_MATH(3),
  J_EXAM_JAPANESE(4),
  J_EXAM_SOCIAL_STUDIES(5),
  J_EXAM_SCIENCE(6),

  // 中学科目
  JUNIOR_MATH(7),
  JUNIOR_JAPANESE(8),
  JUNIOR_ENGLISH(9),
  JUNIOR_SOCIAL_STUDIES(10),
  JUNIOR_SCIENCE(11),

  // 高受科目
  H_EXAM_MATH(12),
  H_EXAM_JAPANESE(13),
  H_EXAM_ENGLISH(14),
  H_EXAM_SOCIAL_STUDIES(15),
  H_EXAM_SCIENCE(16),

  // 高校科目
  HIGH_MATH_IA(17),
  HIGH_MATH_IIB(18),
  HIGH_MATH_III(19),
  HIGH_JAPANESE(20),
  HIGH_CLASSICAL_JAPANESE(21),
  HIGH_ENGLISH(22),

  // 大学受験科目
  UNI_EXAM_MATH_IA(23),
  UNI_EXAM_MATH_IIB(24),
  UNI_EXAM_MATH_III(25),
  UNI_EXAM_JAPANESE(26),
  UNI_EXAM_CLASSICAL_JAPANESE(27),
  UNI_EXAM_ENGLISH(28),
  UNI_EXAM_JAPANESE_HISTORY(29),
  UNI_EXAM_WORLD_HISTORY(30),
  UNI_EXAM_PHYSICS(31),
  UNI_EXAM_CHEMISTRY(32),
  UNI_EXAM_BIOLOGY(33);

  private final int id;

  private Subject(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public static Subject getSubjectById(int id) {
    return Subject.values()[id-1];
  }

  @Override
  public String toString() {
    switch (this.id) {
      case 1:
        return "PRIMARY_MATH";
      case 2:
        return "PRIMARY_JAPANESE";
      case 3:
        return "J_EXAM_MATH";
      case 4:
        return "J_EXAM_JAPANESE";
      case 5:
        return "J_EXAM_SOCIAL_STUDIES";
      case 6:
        return "J_EXAM_SCIENCE";
      case 7:
        return "JUNIOR_MATH";
      case 8:
        return "JUNIOR_JAPANESE";
      case 9:
        return "JUNIOR_ENGLISH";
      case 10:
        return "JUNIOR_SOCIAL_STUDIES";
      case 11:
        return "JUNIOR_SCIENCE";
      case 12:
        return "H_EXAM_MATH";
      case 13:
        return "H_EXAM_JAPANESE";
      case 14:
        return "H_EXAM_ENGLISH";
      case 15:
        return "H_EXAM_SOCIAL_STUDIES";
      case 16:
        return "H_EXAM_SCIENCE";
      case 17:
        return "HIGH_MATH_IA";
      case 18:
        return "HIGH_MATH_IIB";
      case 19:
        return "HIGH_MATH_III";
      case 20:
        return "HIGH_JAPANESE";
      case 21:
        return "HIGH_CLASSICAL_JAPANESE";
      case 22:
        return "HIGH_ENGLISH";
      case 23:
        return "UNI_EXAM_MATH_IA";
      case 24:
        return "UNI_EXAM_MATH_IIB";
      case 25:
        return "UNI_EXAM_MATH_III";
      case 26:
        return "UNI_EXAM_JAPANESE";
      case 27:
        return "UNI_EXAM_CLASSICAL_JAPANESE";
      case 28:
        return "UNI_EXAM_ENGLISH";
      case 29:
        return "UNI_EXAM_JAPANESE_HISTORY";
      case 30:
        return "UNI_EXAM_WORLD_HISTORY";
      case 31:
        return "UNI_EXAM_PHYSICS";
      case 32:
        return "UNI_EXAM_CHEMISTRY";
      case 33:
        return "UNI_EXAM_BIOLOGY";
      default:
        return null;
    }
  }
}
