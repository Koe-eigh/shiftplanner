package com.shiftplanner.solver.app.adapters.scheduling.repositories;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shiftplanner.solver.app.db.scheduling.models.TimetableJpaModel;
import com.shiftplanner.solver.app.db.scheduling.repositories.TimetableJpaRepository;
import com.shiftplanner.solver.core.scheduling.entities.Timetable;
import com.shiftplanner.solver.core.scheduling.repositories.ITimetableRepository;
import com.shiftplanner.solver.core.scheduling.values.TimetableId;

@Repository
@Transactional
public class TimetableRepository implements ITimetableRepository {
  @Autowired
  private TimetableJpaRepository timetableJpaRepository;

  @Override
  public List<Timetable> findAll() {
    return timetableJpaRepository.findAll().stream().map(TimetableJpaModel::toCoreObject).toList();
  }

  @Override
  public Optional<Timetable> findById(TimetableId id) {
    return timetableJpaRepository.findById(id.getValue()).stream().map(TimetableJpaModel::toCoreObject).findFirst();
  }

  @Override
  public void save(Timetable timetable) {
    this.timetableJpaRepository.save(TimetableJpaModel.fromCoreObject(timetable));
  }

  @Override
  public void saveAll(List<Timetable> objs) {
    this.timetableJpaRepository.saveAll(objs.stream().map(TimetableJpaModel::fromCoreObject).collect(Collectors.toList()));
  }

  @Override
  public Long count() {
    return this.timetableJpaRepository.count();
  }
}
