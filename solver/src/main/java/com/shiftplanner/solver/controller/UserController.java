package com.shiftplanner.solver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftplanner.solver.domain.Lesson;
import com.shiftplanner.solver.domain.StudentSchedule;
import com.shiftplanner.solver.domain.TeacherSchedule;
import com.shiftplanner.solver.domain.Timeslot;
import com.shiftplanner.solver.domain.Timetable;
import com.shiftplanner.solver.entities.ApplicationUser;
import com.shiftplanner.solver.entities.Student;
import com.shiftplanner.solver.entities.Teacher;
import com.shiftplanner.solver.entities.dto.TimetableIdAndNameDTO;
import com.shiftplanner.solver.service.AppService;
import com.shiftplanner.solver.service.UserService;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.SolverManager;
import ai.timefold.solver.core.api.solver.SolverStatus;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppService appService;

    // @Autowired
    // private SolverService solverService;
    @Autowired
    private SolverManager<Timetable, String> solverManager;
    @Autowired
    private SolutionManager<Timetable, HardSoftScore> solutionManager;

    @GetMapping("/")
    public String helloUser() {
        return "user level access";
    }

    @PostMapping("{id}/students")
    public ResponseEntity<Student> registerStudents(@PathVariable("id") String userId,
            @RequestBody List<Student> students, Authentication auth) {
        try {
            verify(auth, userId);
            ApplicationUser user = userService.loadUserById(userId);
            students.stream().forEach(student -> {
                student.setId(UUID.randomUUID().toString());
                appService.registerStudent(user, student);
            });
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{id}/students")
    public List<Student> getStudents(@PathVariable("id") String userId, Authentication auth) {
        try {
            verify(auth, userId);
            ApplicationUser user = userService.loadUserById(userId);
            return appService.loadStudents(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/{id}/students")
    public ResponseEntity<List<Student>> deleteStudents(@PathVariable("id") String userId,
            @RequestBody List<Student> students, Authentication auth) {
        try {
            verify(auth, userId);
            ApplicationUser user = userService.loadUserById(userId);
            appService.deleteStudents(user, students);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/teachers")
    public ResponseEntity<Teacher> registerTeachers(@PathVariable("id") String userId,
            @RequestBody List<Teacher> teachers, Authentication auth) {
        try {
            verify(auth, userId);
            ApplicationUser user = userService.loadUserById(userId);
            teachers.stream().forEach(teacher -> {
                teacher.setId(UUID.randomUUID().toString());
                appService.registerTeacher(user, teacher);
            });
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/teachers")
    public List<Teacher> getTeachers(@PathVariable("id") String userId, Authentication auth) {
        try {
            verify(auth, userId);
            ApplicationUser user = userService.loadUserById(userId);
            return appService.loadTeachers(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/{id}/teachers")
    public ResponseEntity<List<Teacher>> deleteTeachers(@PathVariable("id") String userId,
            @RequestBody List<String> teacherIds, Authentication auth) {
        try {
            verify(auth, userId);
            ApplicationUser user = userService.loadUserById(userId);
            appService.deleteTeachers(user, teacherIds);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}/timetables")
    public ResponseEntity<List<TimetableIdAndNameDTO>> getTimetables(@PathVariable("userId") String userId, Authentication auth) {
        try {
            verify(auth, userId);
            ApplicationUser user = userService.loadUserById(userId);

            List<Timetable> timetables = appService.getTimetables(user);

            List<TimetableIdAndNameDTO> timetableIdAndNameDTOs = new ArrayList<>();
            timetables.forEach(timetable -> {
                TimetableIdAndNameDTO timetableIdAndNameDTO = new TimetableIdAndNameDTO();
                timetableIdAndNameDTO.setProjectId(timetable.getId());
                timetableIdAndNameDTO.setName(timetable.getName());
                timetableIdAndNameDTOs.add(timetableIdAndNameDTO);
            });

            return ResponseEntity.ok(timetableIdAndNameDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}/timetables/{problemId}")
    public ResponseEntity<Timetable> getTimetable(@PathVariable("userId") String userId,
            @PathVariable("problemId") String problemId, Authentication auth) {
        try {
            verify(auth, userId);
            appService.verify(userId, problemId);
            SolverStatus status = getSolverStatus(problemId);
            Timetable timetable = appService.findTimetableById(problemId);
            solutionManager.update(timetable);
            timetable.setSolverStatus(status);
            System.out.println(solutionManager.explain(timetable));
            return ResponseEntity.ok(timetable);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{userId}/timetables")
    public ResponseEntity<Timetable> createTimetable(@PathVariable("userId") String userId, @RequestBody String name,
            Authentication auth) {
        try {
            verify(auth, userId);
            ApplicationUser user = userService.loadUserById(userId);
            List<Lesson> lessons = new ArrayList<>();
            List<TeacherSchedule> teacherSchedules = new ArrayList<>();
            List<Timeslot> timeslots = new ArrayList<>();
            Timetable timetable = new Timetable(UUID.randomUUID().toString(), name, lessons, timeslots,
                    teacherSchedules);
            appService.registerTimetable(user, timetable);
            return ResponseEntity.ok(timetable);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{userId}/timetables/{problemId}/timeslots")
    public ResponseEntity<List<Timeslot>> addTimeslots(@PathVariable("userId") String userId,
            @PathVariable("problemId") String problemId, @RequestBody List<Timeslot> timeslots, Authentication auth) {
        try {
            verify(auth, userId);
            appService.verify(userId, problemId);
            Timetable timetable = appService.findTimetableById(problemId);
            timeslots.forEach(timeslot -> timeslot.setId(Long.toString(UUID.randomUUID().getLeastSignificantBits())));
            List<Timeslot> newTimeslot = new ArrayList<>();
            newTimeslot.addAll(timetable.getTimeslots());
            newTimeslot.addAll(timeslots);
            timetable.setTimeslots(newTimeslot);
            appService.saveTimetable(timetable);

            return ResponseEntity.ok(timeslots);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{userId}/timetables/{problemId}/timeslots")
    public ResponseEntity<List<Timeslot>> deleteTimeslots(@PathVariable("userId") String userId,
            @PathVariable("problemId") String problemId, @RequestBody List<Timeslot> timeslots, Authentication auth) {
        try {
            verify(auth, userId);
            appService.verify(userId, problemId);
            Timetable timetable = appService.findTimetableById(problemId);
            List<Timeslot> newTimeslots = timetable.getTimeslots().stream()
                    .filter(timeslot -> !timeslots.contains(timeslot)).toList();
            timetable.setTimeslots(newTimeslots);
            appService.saveTimetable(timetable);
            return ResponseEntity.ok(timeslots);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{userId}/timetables/{problemId}/teacherSchedules")
    public ResponseEntity<List<TeacherSchedule>> addTeacherSchedule(@PathVariable("userId") String userId,
            @PathVariable("problemId") String problemId, @RequestBody List<TeacherSchedule> teacherSchedules,
            Authentication auth) {
        try {
            verify(auth, userId);
            appService.verify(userId, problemId);
            Timetable timetable = appService.findTimetableById(problemId);
            teacherSchedules
                    .forEach(teacherSchedule -> teacherSchedule.setId(Long.toString(UUID.randomUUID().getLeastSignificantBits())));
            List<TeacherSchedule> newTeacherSchedules = new ArrayList<>();
            newTeacherSchedules.addAll(timetable.getTeacherSchedules());
            newTeacherSchedules.addAll(teacherSchedules);
            timetable.setTeacherSchedules(newTeacherSchedules);
            appService.saveTimetable(timetable);
            return ResponseEntity.ok(teacherSchedules);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{userId}/timetables/{problemId}/teacherSchedules")
    public ResponseEntity<List<TeacherSchedule>> deleteTeacherSchedules(@PathVariable("userId") String userId,
            @PathVariable("problemId") String problemId, @RequestBody List<String> teacherScheduleIds,
            Authentication auth) {
        try {
            verify(auth, userId);
            appService.verify(userId, problemId);
            Timetable timetable = appService.findTimetableById(problemId);
            System.out.println(teacherScheduleIds);
            System.out.println(timetable.getTeacherSchedules().get(0).getId());
            List<TeacherSchedule> newTeacherSchedules = timetable.getTeacherSchedules().stream()
                    .filter(teacherSchedule -> !teacherScheduleIds.contains(teacherSchedule.getId())).toList();
            timetable.setTeacherSchedules(newTeacherSchedules);
            appService.saveTimetable(timetable);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{userId}/timetables/{problemId}/studentSchedules")
    public ResponseEntity<List<StudentSchedule>> addStudentSchedules(@PathVariable("userId") String userId,
            @PathVariable("problemId") String problemId, @RequestBody List<StudentSchedule> studentSchedules,
            Authentication auth) {
        try {
            verify(auth, userId);
            appService.verify(userId, problemId);
            Timetable timetable = appService.findTimetableById(problemId);
            List<Lesson> lessons = new ArrayList<>();
            lessons.addAll(timetable.getLessons());
            for (StudentSchedule studentSchedule : studentSchedules) {
                studentSchedule.getCourseMap().entrySet().stream().forEach(entrySet -> {
                    String subject = entrySet.getKey();
                    int num = entrySet.getValue();
                    for (int i = 0; i < num; i++) {
                        Lesson lesson = new Lesson();
                        lesson.setSubject(subject);
                        lesson.setStudentSchedule(studentSchedule);
                        lesson.setId(Long.toString(UUID.randomUUID().getLeastSignificantBits()));
                        lessons.add(lesson);
                    }
                });
            }
            timetable.setLessons(lessons);
            appService.saveTimetable(timetable);
            return ResponseEntity.ok(studentSchedules);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    //TODO studentSchedule の deleteメソッド追加

    public void verify(Authentication auth, String userId) throws IllegalStateException {
        ApplicationUser user = userService.loadUserById(userId);
        if (!user.getUsername().equals(auth.getName()))
            throw new IllegalStateException("The given id is not the user's one");
    }

    public SolverStatus getSolverStatus(String id) {
        return solverManager.getSolverStatus(id);
    }

}