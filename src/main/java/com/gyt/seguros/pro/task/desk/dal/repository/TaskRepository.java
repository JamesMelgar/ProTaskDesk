package com.gyt.seguros.pro.task.desk.dal.repository;

import com.gyt.seguros.pro.task.desk.dal.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}