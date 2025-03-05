package com.shiftplanner.solver.core.scheduling.valueObjects;

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
    return Subject.values()[id];
  }
}
