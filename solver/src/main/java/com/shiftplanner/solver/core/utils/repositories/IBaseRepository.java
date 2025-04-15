package com.shiftplanner.solver.core.utils.repositories;

import java.util.Optional;
import java.util.List;

public interface IBaseRepository<T, ID> {
  List<T> findAll();
  Optional<T> findById(ID id);
  void save(T obj);
  void saveAll(List<T> objs);
  Long count();
}
