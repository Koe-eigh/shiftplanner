package com.shiftplanner.solver.app.timefold.constraints;

import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;

import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.*;

import java.time.Duration;
import java.time.LocalTime;

import com.shiftplanner.solver.core.scheduling.entities.Lesson;

public class ShiftplannerConstraintsProvider implements ConstraintProvider {

  @Override
  public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
    return new Constraint[] {
        //hard
        lessonConflict(constraintFactory),//一つのタイムスロットに生徒は一人penalty test ok
        lessonAmountCap(constraintFactory),//一つのコマに授業２つまでpenalty test ok
        lessonAvailability(constraintFactory),//授業が利用できない時間帯や講師の利用できない時間帯には授業を割り当てないpenalty test ok
        lessonAssignment(constraintFactory),//全ての授業は誰かに割り当てられるpenalty
        maxLessonInOneDay(constraintFactory),//１日の授業回数の最大penalty test ok
        roomCap(constraintFactory),//校舎のキャパシティpenalty

        //soft
        //TODO なるべく少ない先生で授業をもちきる制約を加える。
        oneTeacherConstraint(constraintFactory),
        teacherInCharge(constraintFactory),//なるべく担当講師が授業するpenalty test ok
        teacherPrefersLaterClass(constraintFactory),//講師は遅めの授業を好むpenalty test ok
        avoidConsecutiveLessonsForStudent(constraintFactory),//生徒の連続した授業を避けるpenalty test ok
        avoidLongGapsBetweenLessons(constraintFactory),//生徒は授業の間隔が空きすぎることを拒むpenalty test ok
        avoidConsecutiveLessonsForTeacher(constraintFactory),
        twoStudentsInOneTimeslot(constraintFactory),//一つのコマでなるべく二人に授業をするpenalty
        enoughGapBetweenLessons(constraintFactory)//生徒は授業間隔を中１日以上持ちたいpenalty
    };
  }

  private Constraint lessonConflict(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .join(Lesson.class,
            Joiners.equal(Lesson::getTimeslot),
            Joiners.equal(Lesson::getStudent))
        .filter((lesson1, lesson2) -> !lesson1.equals(lesson2))
        .penalize(HardSoftScore.ofHard(20))
        .asConstraint("Lesson conflict");
  }

  private Constraint lessonAmountCap(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .groupBy(Lesson::getTeacher, Lesson::getTimeslot, count())
        .filter((teacher, timeslot, count) -> count > 2)
        .penalize(HardSoftScore.ofHard(30))
        .asConstraint("Amount of lessons capacity");
  }

  private Constraint lessonAvailability(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .filter(Lesson::isAssignedAtUnavailableTimeslot)
        .penalize(HardSoftScore.ofHard(100))
        .asConstraint("Lesson availability");
  }

  private Constraint lessonAssignment(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .filter(lesson -> !lesson.isAssigned())
        .penalize(HardSoftScore.ofHard(5))
        .asConstraint("Lesson assignment");
  }

  private Constraint maxLessonInOneDay(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .groupBy(lesson -> lesson.getTeacher(), lesson -> lesson.getTimeslot().getSlot().toLocalDate(),
            countDistinct(Lesson::getTimeslot))
        .filter((teacher, date, num) -> num > 5)
        .penalize(HardSoftScore.ofHard(1))
        .asConstraint("Max of lessons in one day");
  }

  private Constraint roomCap(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .groupBy(Lesson::getTimeslot, countDistinct(Lesson::getTeacher))
        .filter((timeslot, count) -> count > 13)
        .penalize(HardSoftScore.ofHard(1))
        .asConstraint("room capacity");
  }

  private Constraint teacherInCharge(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .filter(lesson -> lesson.getStudent().hasRegularLesson(lesson.getSubject()))
        .filter(lesson -> !lesson.getStudent().isTaughtBy(lesson.getTeacher(), lesson.getSubject()))
        .penalize(HardSoftScore.ofSoft(30))
        .asConstraint("Teacher in charge");
  }

  private Constraint oneTeacherConstraint(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .join(Lesson.class,
            Joiners.equal(Lesson::getStudent),
            Joiners.equal(Lesson::getSubject))
        .filter((lesson1, lesson2) -> {
          return !lesson1.getTeacher().equals(lesson2.getTeacher());
        })
        .penalize(HardSoftScore.ofSoft(5))
        .asConstraint("lesson should be had by one teacher");
  }

  private Constraint teacherPrefersLaterClass(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .filter(lesson -> {
          return lesson.getTimeslot().getSlot().toLocalTime().compareTo(LocalTime.of(15, 40)) < 0;
        })
        .penalize(HardSoftScore.ofSoft(1))
        .asConstraint("Teachers prefer later classes");
  }

  private Constraint avoidConsecutiveLessonsForStudent(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .join(Lesson.class,
            Joiners.equal(lesson -> lesson.getTimeslot().getSlot().toLocalDate()),
            Joiners.equal(Lesson::getStudent))
        .filter((lesson1, lesson2) -> {
          Duration gap = Duration.between(lesson1.getTimeslot().getSlot().toLocalTime().plusMinutes(80),
              lesson2.getTimeslot().getSlot().toLocalTime());
          return !gap.isNegative() && gap.compareTo(Duration.ofMinutes(90)) <= 0;
        })
        .penalize(HardSoftScore.ofSoft(3))
        .asConstraint("Avoid Consecutive lessons for students");
  }

  private Constraint avoidLongGapsBetweenLessons(ConstraintFactory constraintFactory) {
    return constraintFactory
        .forEach(Lesson.class)
        .join(Lesson.class,
            Joiners.equal(Lesson::getStudent),
            Joiners.equal((lesson) -> lesson.getTimeslot().getSlot().toLocalDate()))
        .filter((lesson1, lesson2) -> {
          Duration gap = Duration.between(lesson1.getTimeslot().getSlot().toLocalTime().plusMinutes(80),
              lesson2.getTimeslot().getSlot().toLocalTime());
          return !gap.isNegative() && gap.compareTo(Duration.ofMinutes(90)) > 0;
        })
        .penalize(HardSoftScore.ofSoft(20))
        .asConstraint("Avoid long gap between lessons");
  }

  private Constraint avoidConsecutiveLessonsForTeacher(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .join(Lesson.class,
            Joiners.equal(Lesson::getTeacher),
            Joiners.equal(lesson -> lesson.getTimeslot().getSlot().toLocalDate()))
        .filter((lesson1, lesson2) -> !lesson1.getTimeslot().equals(lesson2.getTimeslot()))
        .filter((lesson1, lesson2) -> {
          Duration gap = Duration.between(lesson1.getTimeslot().getSlot().toLocalTime().plusMinutes(80),
              lesson2.getTimeslot().getSlot().toLocalTime());

          return !gap.isNegative() && gap.compareTo(Duration.ofMinutes(180)) > 0;
        })
        .penalize(HardSoftScore.ofSoft(2), (lesson1, lesson2) -> { // TODO大きな問題において機能するか確かめる必要あり
          Duration gap = Duration.between(lesson1.getTimeslot().getSlot().toLocalTime(),
              lesson2.getTimeslot().getSlot().toLocalTime());
          return (int) gap.dividedBy(80).toMinutes();
        })
        .asConstraint("Avoid Consecutive lessons for teachers");
  }

  private Constraint twoStudentsInOneTimeslot(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .groupBy(Lesson::getTeacher, Lesson::getTimeslot, count())
        .filter((teacher, timeslot, count) -> count == 1)
        .penalize(HardSoftScore.ofSoft(10))
        .asConstraint("A teacher should teach to two students in a timeslot");
  }

  private Constraint enoughGapBetweenLessons(ConstraintFactory cf) {
    return cf
        .forEach(Lesson.class)
        .join(Lesson.class,
            Joiners.equal(Lesson::getStudent))
        .filter((lesson1, lesson2) -> {
          long gap = Duration.between(lesson1.getTimeslot().getSlot().toLocalDate().atStartOfDay(),
              lesson2.getTimeslot().getSlot().toLocalDate().atStartOfDay()).toDays();
          return gap == 1;
        })
        .penalize(HardSoftScore.ofSoft(3))
        .asConstraint("two-day gap between lessons is preferable for students");
  }
}
