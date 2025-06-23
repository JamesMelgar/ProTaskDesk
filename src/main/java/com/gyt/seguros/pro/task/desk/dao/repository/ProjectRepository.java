package com.gyt.seguros.pro.task.desk.dao.repository;

import com.gyt.seguros.pro.task.desk.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsByProjectName(String projectName);

}
