package com.shiftplanner.solver.constraints;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.shiftplanner.solver.domain.Lesson;
import com.shiftplanner.solver.domain.StudentSchedule;
import com.shiftplanner.solver.domain.TeacherSchedule;
import com.shiftplanner.solver.domain.Timeslot;
import com.shiftplanner.solver.domain.Timetable;
import com.shiftplanner.solver.entities.Student;
import com.shiftplanner.solver.entities.Teacher;

import ai.timefold.solver.test.api.score.stream.ConstraintVerifier;

@SpringBootTest
public class ShiftplannerConstraintsProviderTest {

    private final ConstraintVerifier<ShiftplannerConstraintsProvider, Timetable> constraintVerifier =
            ConstraintVerifier.build(new ShiftplannerConstraintsProvider(), Timetable.class, Lesson.class);

    @Test
    void lessonConflict() {
        Timeslot timeslot = new Timeslot("-8057180482603631417", LocalDateTime.of(2024, 7, 30, 12, 40), false);
        Student student1 = new Student("9e59714e-3dcb-4e5e-8393-93477bd4e640", "潮田渚", "中1");
        Student student2 = new Student("9e59714e-3dcb-4e5e-8393-93477bd4e640", "潮田渚", "中1");
        StudentSchedule studentSchedule1 = new StudentSchedule(student1, null, null);
        StudentSchedule studentSchedule2 = new StudentSchedule(student2, null, null);

        Lesson lesson1 = new Lesson("-4805949845357417363", studentSchedule1, "中学数学", new TeacherSchedule(), timeslot, false);
        Lesson lesson2 = new Lesson("-7632942297400979115", studentSchedule2, "中学国語", new TeacherSchedule(), timeslot, false);

        constraintVerifier.verifyThat(ShiftplannerConstraintsProvider::lessonConflict).given(lesson1, lesson2).penalizesBy(1);
    }

    @Test
    void lessonAmountCap() {
        Timeslot timeslot1 = new Timeslot("-8057180482603631417", LocalDateTime.of(2024, 7, 30, 12, 40), false);
        Teacher teacher1 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule1 = new TeacherSchedule("1", teacher1, null);
        Lesson lesson1 = new Lesson("1", new StudentSchedule(), "理科", teacherSchedule1, timeslot1, false);

        Timeslot timeslot2 = new Timeslot("-8057180482603631417", LocalDateTime.of(2024, 7, 30, 12, 40), false);
        Teacher teacher2 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule2 = new TeacherSchedule("1", teacher2, null);
        Lesson lesson2 = new Lesson("2", new StudentSchedule(), "数学", teacherSchedule2, timeslot2, false);

        Timeslot timeslot3 = new Timeslot("-8057180482603631417", LocalDateTime.of(2024, 7, 30, 12, 40), false);
        Teacher teacher3 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule3 = new TeacherSchedule("1", teacher3, null);
        Lesson lesson3 = new Lesson("3", new StudentSchedule(), "英語", teacherSchedule3, timeslot3, false);

        constraintVerifier.verifyThat(ShiftplannerConstraintsProvider::lessonAmountCap).given(lesson1, lesson2, lesson3).penalizesBy(1);
    }

    @Test
    void dayOffReqs() {
        Timeslot timeslot1 = new Timeslot("1", LocalDateTime.of(2024, 8, 1, 12, 40), false);
        Set<Timeslot> dayOffReqs = new HashSet<>();
        dayOffReqs.add(timeslot1);
        Student student = new Student("1", "Turing", "中1");
        StudentSchedule studentSchedule = new StudentSchedule(student, null, dayOffReqs);
        Lesson lesson1 = new Lesson("1", studentSchedule, "中学数学", new TeacherSchedule("1", null, dayOffReqs), timeslot1, false);

        Timeslot timeslot2 = new Timeslot("-8057180482603631417", LocalDateTime.of(2024, 7, 30, 12, 40), false);
        dayOffReqs.add(timeslot2);
        Teacher teacher1 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule1 = new TeacherSchedule("1", teacher1, dayOffReqs);
        Lesson lesson2 = new Lesson("1", new StudentSchedule(), "理科", teacherSchedule1, timeslot1, false);


        constraintVerifier.verifyThat(ShiftplannerConstraintsProvider::dayOffReq).given(lesson1, lesson2).penalizesBy(2);
    }

    @Test
    void maxLessonInOneDay() {
        Timeslot timeslot1 = new Timeslot("-8057180482603631417", LocalDateTime.of(2024, 7, 30, 12, 40), false);
        Teacher teacher1 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule1 = new TeacherSchedule("1", teacher1, null);
        Lesson lesson1 = new Lesson("1", new StudentSchedule(), "理科", teacherSchedule1, timeslot1, false);

        Timeslot timeslot2 = new Timeslot("-8057180482603631418", LocalDateTime.of(2024, 7, 30, 14, 10), false);
        Teacher teacher2 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule2 = new TeacherSchedule("1", teacher2, null);
        Lesson lesson2 = new Lesson("2", new StudentSchedule(), "理科", teacherSchedule2, timeslot2, false);

        Timeslot timeslot3 = new Timeslot("-8057180482603631419", LocalDateTime.of(2024, 7, 30, 15, 40), false);
        Teacher teacher3 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule3 = new TeacherSchedule("1", teacher3, null);
        Lesson lesson3 = new Lesson("3", new StudentSchedule(), "理科", teacherSchedule3, timeslot3, false);

        Timeslot timeslot4 = new Timeslot("-8057180482603631420", LocalDateTime.of(2024, 7, 30, 17,20), false);
        Teacher teacher4 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule4 = new TeacherSchedule("1", teacher4, null);
        Lesson lesson4 = new Lesson("4", new StudentSchedule(), "理科", teacherSchedule4, timeslot4, false);

        Timeslot timeslot5 = new Timeslot("-8057180482603631421", LocalDateTime.of(2024, 7, 30, 18, 50), false);
        Teacher teacher5 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule5 = new TeacherSchedule("1", teacher5, null);
        Lesson lesson5 = new Lesson("5", new StudentSchedule(), "理科", teacherSchedule5, timeslot5, false);

        Timeslot timeslot6 = new Timeslot("-8057180482603631422", LocalDateTime.of(2024, 7, 30, 20, 20), false);
        Teacher teacher6 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule6 = new TeacherSchedule("1", teacher6, null);
        Lesson lesson6 = new Lesson("6", new StudentSchedule(), "理科", teacherSchedule6, timeslot6, false);

        constraintVerifier.verifyThat(ShiftplannerConstraintsProvider::maxLessonInOneDay).given(lesson1, lesson2, lesson3, lesson4, lesson5, lesson6).penalizesBy(1);

    }

    //soft

    @Test
    void teacherInCharge() {
        Student student1 = new Student("9e59714e-3dcb-4e5e-8393-93477bd4e640", "潮田渚", "中1");
        Map<String, Teacher> subjectTeacherMap = new HashMap<>();
        Teacher teacher1 = new Teacher("1", "Einstein");
        Teacher teacher2 = new Teacher("2", "Turing");

        subjectTeacherMap.put("数学", teacher1);
        student1.setSubjectTeacherMap(subjectTeacherMap);
        StudentSchedule studentSchedule = new StudentSchedule(student1, null, null);
        TeacherSchedule teacherSchedule = new TeacherSchedule("1", teacher2, null);
        Lesson lesson = new Lesson("1", studentSchedule, "数学", teacherSchedule, new Timeslot(), false);

        Student student2 = new Student("9e59714e-3dcb-4e5e-8393-93477bd4e641", "Curie", "中1");
        Map<String, Teacher> subjectTeacherMap2 = new HashMap<>();
        Teacher teacher3 = new Teacher("3", "Fyneman");
        Teacher teacher4 = new Teacher("4", "Newton");
        subjectTeacherMap2.put("物理", teacher3);
        subjectTeacherMap2.put("物理", teacher4);
        StudentSchedule studentSchedule2 = new StudentSchedule(student2, null, null);
        TeacherSchedule teacherSchedule2 = new TeacherSchedule("2", teacher3, null);
        TeacherSchedule teacherSchedule3 = new TeacherSchedule("3", teacher4, null);
        Lesson lesson2 = new Lesson("2", studentSchedule2, "物理", teacherSchedule2, null, false);
        Lesson lesson3 = new Lesson("2", studentSchedule2, "物理", teacherSchedule3, null, false);

        constraintVerifier.verifyThat(ShiftplannerConstraintsProvider::teacherInCharge).given(lesson, lesson2, lesson3).penalizesBy(1);
    }

    @Test
    public void oneTeacherConstraint() {
        Student student1 = new Student("9e59714e-3dcb-4e5e-8393-93477bd4e640", "潮田渚", "中1");
        Map<String, Teacher> subjectTeacherMap = new HashMap<>();
        Teacher teacher1 = new Teacher("1", "Einstein");
        Teacher teacher2 = new Teacher("2", "Turing");

        subjectTeacherMap.put("数学", teacher1);
        subjectTeacherMap.put("数学", teacher2);
        student1.setSubjectTeacherMap(subjectTeacherMap);
        StudentSchedule studentSchedule = new StudentSchedule(student1, null, null);
        TeacherSchedule teacherSchedule = new TeacherSchedule("1", teacher2, null);
        TeacherSchedule teacherSchedule2 = new TeacherSchedule("2", teacher1, null);
        Lesson lesson = new Lesson("1", studentSchedule, "数学", teacherSchedule, new Timeslot(), false);
        Lesson lesson2 = new Lesson("2", studentSchedule, "数学", teacherSchedule2, new Timeslot(), false);

        constraintVerifier.verifyThat(ShiftplannerConstraintsProvider::oneTeacherConstraint).given(lesson, lesson2).penalizesBy(2);
    }

    @Test
    void teacherPrefersLaterClass() {
        Timeslot timeslot1 = new Timeslot("-8057180482603631417", LocalDateTime.of(2024, 7, 30, 12, 40), false);
        Teacher teacher1 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule1 = new TeacherSchedule("1", teacher1, null);
        Lesson lesson1 = new Lesson("1", new StudentSchedule(), "理科", teacherSchedule1, timeslot1, false);

        constraintVerifier.verifyThat(ShiftplannerConstraintsProvider::teacherPrefersLaterClass).given(lesson1).penalizesBy(1);
    }

    @Test
    void avoidConsecutiveLessonsForStudent() {
        Timeslot timeslot1 = new Timeslot(Long.toString(UUID.randomUUID().getLeastSignificantBits()), LocalDateTime.of(2024, 7, 30, 12, 40), false);
        Timeslot timeslot2 = new Timeslot(Long.toString(UUID.randomUUID().getLeastSignificantBits()), LocalDateTime.of(2024, 7, 30, 14, 10), false);
        Student student = new Student(UUID.randomUUID().toString(), "Curie", "中2");
        StudentSchedule studentSchedule = new StudentSchedule(student, null, null);
        Lesson lesson1 = new Lesson(Long.toString(UUID.randomUUID().getLeastSignificantBits()), studentSchedule, null, new TeacherSchedule(), timeslot1, false);
        Lesson lesson2 = new Lesson(Long.toString(UUID.randomUUID().getLeastSignificantBits()), studentSchedule, null, new TeacherSchedule(), timeslot2, false);

        constraintVerifier.verifyThat(ShiftplannerConstraintsProvider::avoidConsecutiveLessonsForStudent).given(lesson1, lesson2).penalizesBy(1);

    }

    @Test
    void avoidLongGapsBetweenLessons() {
        Timeslot timeslot1 = new Timeslot(Long.toString(UUID.randomUUID().getLeastSignificantBits()), LocalDateTime.of(2024, 7, 30, 12, 40), false);
        Timeslot timeslot2 = new Timeslot(Long.toString(UUID.randomUUID().getLeastSignificantBits()), LocalDateTime.of(2024, 7, 30, 15, 40), false);
        Student student = new Student(UUID.randomUUID().toString(), "Newton", "中3");
        StudentSchedule studentSchedule = new StudentSchedule(student, null, null);
        Lesson lesson1 = new Lesson(Long.toString(UUID.randomUUID().getLeastSignificantBits()), studentSchedule, "数学", new TeacherSchedule(), timeslot1, false);
        Lesson lesson2 = new Lesson(Long.toString(UUID.randomUUID().getLeastSignificantBits()), studentSchedule, "数学", new TeacherSchedule(), timeslot2, false);

        constraintVerifier.verifyThat(ShiftplannerConstraintsProvider::avoidLongGapsBetweenLessons).given(lesson1, lesson2).penalizesBy(1);
    }

    @Test
    void avoidConsecutiveLessonsForTeacher() {
        Timeslot timeslot1 = new Timeslot(Long.toString(UUID.randomUUID().getLeastSignificantBits()), LocalDateTime.of(2024, 7, 30, 12, 40), false);
        Timeslot timeslot2 = new Timeslot(Long.toString(UUID.randomUUID().getLeastSignificantBits()), LocalDateTime.of(2024, 7, 30, 14, 10), false);
        Timeslot timeslot3 = new Timeslot(Long.toString(UUID.randomUUID().getLeastSignificantBits()), LocalDateTime.of(2024, 7, 30, 15, 40), false);
        Timeslot timeslot4 = new Timeslot(Long.toString(UUID.randomUUID().getLeastSignificantBits()), LocalDateTime.of(2024, 7, 30, 17, 20), false);
        Teacher teacher1 = new Teacher("1", "Einstein");
        TeacherSchedule teacherSchedule1 = new TeacherSchedule("1", teacher1, null);
        Student student1 = new Student("9e59714e-3dcb-4e5e-8393-93477bd4e640", "潮田渚", "中1");
        Student student2 = new Student("9e59714e-3dcb-4e5e-8393-93477bd4e641", "A", "中1");
        StudentSchedule studentSchedule1 = new StudentSchedule(student1, null, null);
        StudentSchedule studentSchedule2 = new StudentSchedule(student2, null, null);
        Student student3 = new Student("9e59714e-3dcb-4e5e-8393-93477bd4e642", "B", "中1");
        Student student4 = new Student("9e59714e-3dcb-4e5e-8393-93477bd4e643", "C", "中1");
        StudentSchedule studentSchedule3 = new StudentSchedule(student3, null, null);
        StudentSchedule studentSchedule4 = new StudentSchedule(student4, null, null);
       Lesson lesson1 = new Lesson(Long.toString(UUID.randomUUID().getLeastSignificantBits()), studentSchedule1, "理科", teacherSchedule1, timeslot1, false);
       Lesson lesson2 = new Lesson(Long.toString(UUID.randomUUID().getLeastSignificantBits()), studentSchedule2, "理科", teacherSchedule1, timeslot2, false);
       Lesson lesson3 = new Lesson(Long.toString(UUID.randomUUID().getLeastSignificantBits()), studentSchedule3, "理科", teacherSchedule1, timeslot3, false);
       Lesson lesson4 = new Lesson(Long.toString(UUID.randomUUID().getLeastSignificantBits()), studentSchedule4, "理科", teacherSchedule1, timeslot4, false);

       constraintVerifier.verifyThat(ShiftplannerConstraintsProvider::avoidConsecutiveLessonsForTeacher).given(lesson1, lesson2, lesson3, lesson4).penalizesBy(3);
    }
}
