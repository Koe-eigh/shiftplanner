package com.shiftplanner.solver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiftplanner.solver.domain.Timetable;
import com.shiftplanner.solver.entities.ApplicationUser;
import com.shiftplanner.solver.entities.Student;
import com.shiftplanner.solver.entities.Teacher;
import com.shiftplanner.solver.entities.relations.UserToStudent;
import com.shiftplanner.solver.entities.relations.UserToTeacher;
import com.shiftplanner.solver.entities.relations.UserToTimetable;
import com.shiftplanner.solver.repository.StudentRepository;
import com.shiftplanner.solver.repository.TeacherRepository;
import com.shiftplanner.solver.repository.TimetableRepository;
import com.shiftplanner.solver.repository.relations.UserToStudentRepo;
import com.shiftplanner.solver.repository.relations.UserToTeacherRepo;
import com.shiftplanner.solver.repository.relations.UserToTimetableRepo;

@Service
public class AppService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserToStudentRepo studentRelation;
    @Autowired
    private UserToTeacherRepo teacherRelation;
    @Autowired
    private TimetableRepository timetableRepository;
    @Autowired
    private UserToTimetableRepo timetableRelation;

    //student crud
    public Student registerStudent(ApplicationUser user, Student student) {

        UserToStudent relation = studentRelation.findById(user.getUserId()).orElseThrow(() -> new IllegalStateException("this user does not have students yet"));
        List<String> studentIds = relation.getStudentIds();
        if(!studentIds.contains(student.getId())) studentIds.add(student.getId());
        UserToStudent userToStudent = new UserToStudent();
        userToStudent.setUserId(user.getUserId());
        userToStudent.setStudentIds(studentIds);
        studentRelation.save(userToStudent);

        return studentRepository.save(student);
    }

    public List<Student> loadStudents(ApplicationUser user) {
        UserToStudent relation = studentRelation.findById(user.getUserId()).get();
        List<Student> students = new ArrayList<>();
        relation.getStudentIds().stream().forEach(studentId -> {
            students.add(studentRepository.findById(studentId).get());
        });
        return students;
    }

    public void deleteStudents(ApplicationUser user, List<Student> students) {

        UserToStudent relation = studentRelation.findById(user.getUserId()).orElseThrow(() -> new IllegalStateException("this user does not have students yet"));
        List<String> studentIds = relation.getStudentIds();
        if(studentIds.containsAll(students.stream().map(student -> student.getId()).toList())) studentIds.removeAll(students.stream().map(student -> student.getId()).toList());
        UserToStudent userToStudent = new UserToStudent();
        userToStudent.setUserId(user.getUserId());
        userToStudent.setStudentIds(studentIds);
        studentRelation.save(userToStudent);

    }

    //teacher crud
    public Teacher registerTeacher(ApplicationUser user, Teacher teacher) {
        UserToTeacher relation = teacherRelation.findById(user.getUserId()).get();
        List<String> teacherIds = relation.getTeacherIds();
        if(!teacherIds.contains(teacher.getId())) teacherIds.add(teacher.getId());
        UserToTeacher userToTeacher = new UserToTeacher();
        userToTeacher.setUserId(user.getUserId());
        userToTeacher.setTeacherIds(teacherIds);
        teacherRelation.save(userToTeacher);

        return teacherRepository.save(teacher);
    }

    public List<Teacher> loadTeachers(ApplicationUser user) {
        UserToTeacher relation = teacherRelation.findById(user.getUserId()).get();
        List<Teacher> teachers = new ArrayList<>();
        relation.getTeacherIds().stream().forEach(teacherid -> {
            teachers.add(teacherRepository.findById(teacherid).get());
        });
        return teachers;
    }

    public void deleteTeachers(ApplicationUser user, List<String> teacherIds) {
        UserToTeacher relation = teacherRelation.findById(user.getUserId()).orElseThrow(() -> new IllegalStateException("this user does not have teachers yet"));
        List<String> ids = relation.getTeacherIds();
        if(ids.containsAll(teacherIds)) ids.removeAll(teacherIds);
        UserToTeacher userToTeacher = new UserToTeacher();
        userToTeacher.setUserId(user.getUserId());
        userToTeacher.setTeacherIds(ids);
        teacherRelation.save(userToTeacher);
    }

    public Timetable registerTimetable(ApplicationUser user, Timetable timetable) {
        UserToTimetable relation = timetableRelation.findById(user.getUserId()).get();
        List<String> timetableIds = relation.getTimetableIds();
        if(!timetableIds.contains(timetable.getId())) timetableIds.add(timetable.getId());
        UserToTimetable userToTimetable = new UserToTimetable();
        userToTimetable.setUserId(user.getUserId());
        userToTimetable.setTimetableIds(timetableIds);
        timetableRelation.save(userToTimetable);

        return timetableRepository.save(timetable);
    }

    public List<Timetable> getTimetables(ApplicationUser user) {
        List<Timetable> timetables = new ArrayList<>();
        UserToTimetable relation = timetableRelation.findById(user.getUserId()).get();
        relation.getTimetableIds().forEach(projectId -> {
            timetables.add(timetableRepository.findById(projectId).get());
        });
        return timetables;
    }

    public Timetable findTimetableById(String problemId) {
        return timetableRepository.findById(problemId).orElseThrow(() -> new IllegalStateException("timetable not found"));
    }

    public void saveTimetable(Timetable timetable) {
        timetable.setScore(null);
        timetableRepository.save(timetable);
    }

    public void verify(String userId, String problemId) {
        UserToTimetable relation = timetableRelation.findById(userId).get();
        if(!relation.getTimetableIds().contains(problemId)) throw new IllegalStateException("the given problemId is not the user's one");
    }

}
