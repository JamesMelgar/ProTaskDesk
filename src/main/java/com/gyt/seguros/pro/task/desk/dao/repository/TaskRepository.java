package com.gyt.seguros.pro.task.desk.dao.repository;

import com.gyt.seguros.pro.task.desk.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}