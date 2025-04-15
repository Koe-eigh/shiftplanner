package com.shiftplanner.solver.app.seeder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.shiftplanner.solver.core.scheduling.repositories.IUserRepository;
import com.shiftplanner.solver.core.scheduling.entities.Lesson;
import com.shiftplanner.solver.core.scheduling.entities.Student;
import com.shiftplanner.solver.core.scheduling.entities.Teacher;
import com.shiftplanner.solver.core.scheduling.entities.Timetable;
import com.shiftplanner.solver.core.scheduling.entities.User;
import com.shiftplanner.solver.core.scheduling.repositories.IStudentRepository;
import com.shiftplanner.solver.core.scheduling.repositories.ITeacherRepository;
import com.shiftplanner.solver.core.scheduling.repositories.ITimetableRepository;
import com.shiftplanner.solver.core.scheduling.values.StudentGrade;
import com.shiftplanner.solver.core.scheduling.values.StudentId;
import com.shiftplanner.solver.core.scheduling.values.StudentName;
import com.shiftplanner.solver.core.scheduling.values.Subject;
import com.shiftplanner.solver.core.scheduling.values.TeacherId;
import com.shiftplanner.solver.core.scheduling.values.TeacherName;
import com.shiftplanner.solver.core.scheduling.values.Timeslot;
import com.shiftplanner.solver.core.scheduling.values.TimetableName;
import com.shiftplanner.solver.core.shared.values.UserId;

@Profile("dev")
@Component
public class InitialDataSeeder implements CommandLineRunner {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private ITimetableRepository timetableRepository;

  @Autowired
  private ITeacherRepository teacherRepository;

  @Autowired
  private IStudentRepository studentRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    if (timetableRepository.count() == 0) {
      System.out.println("User saving...");
      String testUsername = "test";
      User testUser = userRepository.findByUsername(testUsername)
          .orElseThrow(() -> new IllegalArgumentException("User: " + testUsername + " does not exist."));
      Timetable timetable = new Timetable(new TimetableName("test_timetable"));
      List<Timeslot> timeslots = new ArrayList<Timeslot>() {
        {
          add(new Timeslot(LocalDateTime.of(2025, 8, 3, 12, 40, 0), true));
          add(new Timeslot(LocalDateTime.of(2025, 8, 3, 14, 10, 0), true));
          add(new Timeslot(LocalDateTime.of(2025, 8, 3, 15, 40, 0), true));
          add(new Timeslot(LocalDateTime.of(2025, 8, 3, 17, 20, 0), true));
          add(new Timeslot(LocalDateTime.of(2025, 8, 3, 18, 50, 0), true));
          add(new Timeslot(LocalDateTime.of(2025, 8, 3, 20, 20, 0), true));

          add(new Timeslot(LocalDateTime.of(2025, 8, 4, 12, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 4, 14, 10, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 4, 15, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 4, 17, 20, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 4, 18, 50, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 4, 20, 20, 0), false));

          add(new Timeslot(LocalDateTime.of(2025, 8, 5, 12, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 5, 14, 10, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 5, 15, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 5, 17, 20, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 5, 18, 50, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 5, 20, 20, 0), false));

          add(new Timeslot(LocalDateTime.of(2025, 8, 6, 12, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 6, 14, 10, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 6, 15, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 6, 17, 20, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 6, 18, 50, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 6, 20, 20, 0), false));

          add(new Timeslot(LocalDateTime.of(2025, 8, 7, 12, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 7, 14, 10, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 7, 15, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 7, 17, 20, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 7, 18, 50, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 7, 20, 20, 0), false));

          add(new Timeslot(LocalDateTime.of(2025, 8, 8, 12, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 8, 14, 10, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 8, 15, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 8, 17, 20, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 8, 18, 50, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 8, 20, 20, 0), false));

          add(new Timeslot(LocalDateTime.of(2025, 8, 9, 12, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 9, 14, 10, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 9, 15, 40, 0), false));
          add(new Timeslot(LocalDateTime.of(2025, 8, 9, 17, 20, 0), true));
          add(new Timeslot(LocalDateTime.of(2025, 8, 9, 18, 50, 0), true));
          add(new Timeslot(LocalDateTime.of(2025, 8, 9, 20, 20, 0), true));
        }
      };
      timetable.setTimeslots(timeslots);

      Teacher einstein = new Teacher(
          new TeacherId(1L),
          new TeacherName("Albert", "Einstein"),
          new HashSet<Timeslot>() {
            {
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 15, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 20, 20, 0), false));

              add(new Timeslot(LocalDateTime.of(2025, 8, 5, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 5, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 5, 15, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 5, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 5, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 5, 20, 20, 0), false));
            }
          },
          new HashSet<Subject>() {
            {
              add(Subject.PRIMARY_MATH);
              add(Subject.JUNIOR_ENGLISH);
              add(Subject.JUNIOR_MATH);
              add(Subject.JUNIOR_SCIENCE);
              add(Subject.H_EXAM_ENGLISH);
              add(Subject.H_EXAM_MATH);
              add(Subject.H_EXAM_SCIENCE);
              add(Subject.HIGH_MATH_IA);
              add(Subject.HIGH_MATH_IIB);
              add(Subject.HIGH_MATH_III);
              add(Subject.UNI_EXAM_MATH_IA);
              add(Subject.UNI_EXAM_MATH_IIB);
              add(Subject.UNI_EXAM_MATH_III);
              add(Subject.UNI_EXAM_PHYSICS);
              add(Subject.UNI_EXAM_CHEMISTRY);
            }
          });

      Teacher adam = new Teacher(
          new TeacherId(2L),
          new TeacherName("Adam", "Smith"),
          new HashSet<Timeslot>() {
            {
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 15, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 20, 20, 0), false));

              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 15, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 20, 20, 0), false));
            }
          },
          new HashSet<Subject>() {
            {
              add(Subject.PRIMARY_JAPANESE);
              add(Subject.JUNIOR_ENGLISH);
              add(Subject.JUNIOR_JAPANESE);
              add(Subject.JUNIOR_SOCIAL_STUDIES);
              add(Subject.H_EXAM_ENGLISH);
              add(Subject.H_EXAM_JAPANESE);
              add(Subject.H_EXAM_SOCIAL_STUDIES);
              add(Subject.HIGH_ENGLISH);
              add(Subject.HIGH_JAPANESE);
              add(Subject.HIGH_CLASSICAL_JAPANESE);
              add(Subject.UNI_EXAM_ENGLISH);
              add(Subject.UNI_EXAM_JAPANESE);
              add(Subject.UNI_EXAM_CLASSICAL_JAPANESE);
              add(Subject.UNI_EXAM_JAPANESE_HISTORY);
              add(Subject.UNI_EXAM_WORLD_HISTORY);
            }
          });
      List<Teacher> teachers = new ArrayList<Teacher>() {
        {
          add(einstein);
          add(adam);
        }
      };
      timetable.setTeachers(teachers);
      teacherRepository.saveAll(teachers);

      Student newton = new Student(
          new StudentId(1L),
          new StudentName("Isac", "Newton"),
          StudentGrade.JUNIOR_2,
          new HashSet<Timeslot>() {
            {
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 20, 20, 0), false));

              add(new Timeslot(LocalDateTime.of(2025, 8, 5, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 5, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 5, 20, 20, 0), false));

              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 20, 20, 0), false));

              add(new Timeslot(LocalDateTime.of(2025, 8, 7, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 7, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 7, 20, 20, 0), false));

              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 20, 20, 0), false));

              add(new Timeslot(LocalDateTime.of(2025, 8, 9, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 9, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 9, 15, 40, 0), false));
            }
          },
          new HashMap<Subject, Integer>() {
            {
              put(Subject.JUNIOR_MATH, 3);
              put(Subject.JUNIOR_ENGLISH, 2);
            }
          },
          new HashMap<Teacher, Subject>() {
            {
              put(einstein, Subject.JUNIOR_MATH);
            }
          });

      Student maxwell = new Student(
          new StudentId(2L),
          new StudentName("James", "Maxwell"),
          StudentGrade.HIGH_1,
          new HashSet<Timeslot>() {
            {
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 15, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 20, 20, 0), false));

              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 15, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 20, 20, 0), false));
            }
          },
          new HashMap<Subject, Integer>() {
            {
              put(Subject.HIGH_MATH_IA, 2);
              put(Subject.HIGH_ENGLISH, 3);
            }
          },
          new HashMap<Teacher, Subject>() {
            {
              put(adam, Subject.HIGH_ENGLISH);
            }
          });

      Student boltzmann = new Student(
          new StudentId(3L),
          new StudentName("Ludwig", "Boltzmann"),
          StudentGrade.HIGH_3,
          new HashSet<Timeslot>() {
            {
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 15, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 20, 20, 0), false));

              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 15, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 8, 20, 20, 0), false));
            }
          },
          new HashMap<Subject, Integer>() {
            {
              put(Subject.UNI_EXAM_PHYSICS, 2);
              put(Subject.UNI_EXAM_CHEMISTRY, 3);
            }
          },
          new HashMap<Teacher, Subject>() {
            {
              put(einstein, Subject.UNI_EXAM_PHYSICS);
              put(einstein, Subject.UNI_EXAM_CHEMISTRY);
            }
          });
      Student planck = new Student(
          new StudentId(4L),
          new StudentName("Max", "Planck"),
          StudentGrade.JUNIOR_3,
          new HashSet<Timeslot>() {
            {
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 15, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 4, 20, 20, 0), false));

              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 12, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 14, 10, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 15, 40, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 17, 20, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 18, 50, 0), false));
              add(new Timeslot(LocalDateTime.of(2025, 8, 6, 20, 20, 0), false));
            }
          },
          new HashMap<Subject, Integer>() {
            {
              put(Subject.H_EXAM_MATH, 4);
              put(Subject.H_EXAM_ENGLISH, 2);
            }
          },
          new HashMap<Teacher, Subject>() {
            {
              put(adam, Subject.H_EXAM_ENGLISH);
            }
          });
      List<Student> students = new ArrayList<Student>() {
        {
          add(newton);
          add(maxwell);
          add(boltzmann);
          add(planck);
        }
      };
      studentRepository.saveAll(students);

      List<Lesson> lessons = new ArrayList<Lesson>();
      students.stream().forEach(student -> {
        student.getCourseAmountMap().entrySet().stream().forEach(entry -> {
          for (int i = 0; i < entry.getValue(); i++) {
            lessons.add(new Lesson(student.getId().getValue() * 100L +
                Long.valueOf(i), student,
                entry.getKey(), null, null, false));
          }
        });
      });
      timetable.setLessons(lessons);

      userRepository.save(new User(new UserId(testUser.getId().getValue().toString()), new ArrayList<>() {
        {
          add(timetable);
        }
      },
          teachers,
          students));
    }
  }
}
