package com.shiftplanner.solver.app.adapters.scheduling.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shiftplanner.solver.app.db.scheduling.repositories.TeacherJpaRepository;
import com.shiftplanner.solver.app.db.scheduling.models.TeacherJpaModel;
import com.shiftplanner.solver.core.scheduling.entities.Teacher;
import com.shiftplanner.solver.core.scheduling.repositories.ITeacherRepository;
import com.shiftplanner.solver.core.scheduling.values.TeacherId;

@Repository
@Transactional
public class TeacherRepository implements ITeacherRepository {
  @Autowired
  private TeacherJpaRepository teacherJpaRepository;

  @Override
  public List<Teacher> findAll() {
    return teacherJpaRepository.findAll().stream().map(TeacherJpaModel::toCoreObject).collect(Collectors.toList());
  }

  @Override
  public Optional<Teacher> findById(TeacherId id) {
    return teacherJpaRepository.findById(id.getValue()).stream().map(TeacherJpaModel::toCoreObject).findFirst();
  }

  @Override
  public void save(Teacher obj) {
    teacherJpaRepository.save(TeacherJpaModel.fromCoreObject(obj));
  }

  @Override
  public void saveAll(List<Teacher> objs) {
    objs.stream().forEach(obj -> {
      this.save(obj);
    });
  }

  @Override
  public Long count() {
    return teacherJpaRepository.count();
  }
}
