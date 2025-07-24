package com.gyt.seguros.pro.task.desk.svc.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
interface ProjectRepository extends JpaRepository<Project, Integer> {
    boolean existsByProjectName(String projectName);
}
