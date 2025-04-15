package com.shiftplanner.solver.app.adapters.scheduling.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shiftplanner.solver.app.db.scheduling.models.StudentJpaModel;
import com.shiftplanner.solver.app.db.scheduling.models.TeacherJpaModel;
import com.shiftplanner.solver.app.db.scheduling.models.TimetableJpaModel;
import com.shiftplanner.solver.app.db.shared.exceptions.UserJpaModelNotFoundException;
import com.shiftplanner.solver.app.db.shared.models.UserJpaModel;
import com.shiftplanner.solver.app.db.shared.repositories.UserJpaRepository;
import com.shiftplanner.solver.core.scheduling.entities.User;
import com.shiftplanner.solver.core.scheduling.repositories.IUserRepository;
import com.shiftplanner.solver.core.shared.values.UserId;
import com.shiftplanner.solver.core.scheduling.entities.Timetable;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepository implements IUserRepository {

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Override
  public List<User> findAll() {
    return userJpaRepository.findAll().stream()
        .map(model -> new User(new UserId(model.getId()),
            model.getTimetables().stream().map(TimetableJpaModel::toCoreObject).collect(Collectors.toList()),
            model.getTeachers().stream().map(TeacherJpaModel::toCoreObject).collect(Collectors.toList()),
            model.getStudents().stream().map(StudentJpaModel::toCoreObject).collect(Collectors.toList())))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<User> findById(UserId id) {
    return userJpaRepository.findById(id.getValue().toString()).stream()
        .map(model -> new User(new UserId(model.getId()),
            model.getTimetables().stream().map(TimetableJpaModel::toCoreObject).collect(Collectors.toList()),
            model.getTeachers().stream().map(TeacherJpaModel::toCoreObject).collect(Collectors.toList()),
            model.getStudents().stream().map(StudentJpaModel::toCoreObject).collect(Collectors.toList())))
        .findFirst();
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userJpaRepository.findByUsername(username).stream()
        .map(model -> new User(new UserId(model.getId()),
            model.getTimetables() != null
                ? model.getTimetables().stream().map(TimetableJpaModel::toCoreObject).collect(Collectors.toList())
                : null,
            model.getTeachers() != null
                ? model.getTeachers().stream().map(TeacherJpaModel::toCoreObject).collect(Collectors.toList())
                : null,
            model.getStudents() != null
                ? model.getStudents().stream().map(StudentJpaModel::toCoreObject).collect(Collectors.toList())
                : null))
        .findFirst();
  }

  @Override
  public void save(User user) {
    UserJpaModel model = userJpaRepository.findById(user.getId().getValue().toString())
        .orElseThrow(UserJpaModelNotFoundException::new);

    // リストの参照は変えず、中身だけ入れ替える
    model.getTimetables().clear();
    for (Timetable timetable : user.getTimetables()) {
      TimetableJpaModel timetableJpa = TimetableJpaModel.fromCoreObject(timetable);
      model.getTimetables().add(timetableJpa);
    }

    userJpaRepository.save(model);
  }

  @Override
  public void saveAll(List<User> objs) {
    objs.stream().forEach(obj -> {
      this.save(obj);
    });
  }

  @Override
  public Long count() {
    return userJpaRepository.count();
  }
}
