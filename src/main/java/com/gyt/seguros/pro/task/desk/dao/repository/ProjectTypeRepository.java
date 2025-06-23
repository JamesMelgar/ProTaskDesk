package com.gyt.seguros.pro.task.desk.dao.repository;

import com.gyt.seguros.pro.task.desk.model.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Integer>  {
    Optional<ProjectType> findByTypeName(String typeName);

    @Query("SELECT pt FROM ProjectType pt ORDER BY pt.typeName ASC")
    List<ProjectType> findAllOrderByTypeName();

    boolean existsByTypeName(String typeName);
}
