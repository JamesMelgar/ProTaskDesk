package com.gyt.seguros.pro.task.desk.dao.repository;

import com.gyt.seguros.pro.task.desk.model.Project;
import com.gyt.seguros.pro.task.desk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findByProjectName(String projectName);

    List<Project> findByStatus(String status);

    List<Project> findByCreatedBy(User createdBy);

    @Query("SELECT p FROM Project p WHERE p.createdBy.userId = :userId ORDER BY p.createdAt DESC")
    List<Project> findByCreatedByUserIdOrderByCreatedAtDesc(@Param("userId") Integer userId);

    @Query("SELECT p FROM Project p WHERE p.status = :status AND p.createdBy.userId = :userId")
    List<Project> findByStatusAndCreatedByUserId(@Param("status") String status, @Param("userId") Integer userId);

    boolean existsByProjectName(String projectName);

    @Query("SELECT COUNT(p) FROM Project p WHERE p.status = 'ACTIVE'")
    Long countActiveProjects();

    @Query("SELECT COUNT(p) FROM Project p WHERE p.createdBy.userId = :userId AND p.status = 'ACTIVE'")
    Long countActiveProjectsByUserId(@Param("userId") Integer userId);
}
