package com.shiftplanner.solver.app.adapters.scheduling.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shiftplanner.solver.app.db.scheduling.models.StudentJpaModel;
import com.shiftplanner.solver.app.db.scheduling.repositories.StudentJpaRepository;
import com.shiftplanner.solver.core.scheduling.entities.Student;
import com.shiftplanner.solver.core.scheduling.repositories.IStudentRepository;
import com.shiftplanner.solver.core.scheduling.values.StudentId;

@Repository
@Transactional
public class StudentRepository implements IStudentRepository {
  @Autowired
  private StudentJpaRepository studentJpaRepository;

  @Override
  public Optional<Student> findById(StudentId id) {
    return studentJpaRepository.findById(id.getValue()).stream().map(StudentJpaModel::toCoreObject).findFirst();
  }

  @Override
  public List<Student> findAll() {
    return studentJpaRepository.findAll().stream().map(StudentJpaModel::toCoreObject).collect(Collectors.toList());
  }

  @Override
  public void save(Student obj) {
    studentJpaRepository.save(StudentJpaModel.fromCoreObject(obj));
  }

  @Override
  public void saveAll(List<Student> objs) {
    objs.stream().forEach(obj -> {
      this.save(obj);
    });
  }

  @Override
  public Long count() {
    return studentJpaRepository.count();
  }
}
