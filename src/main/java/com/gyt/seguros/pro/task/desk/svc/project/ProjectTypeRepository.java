package com.gyt.seguros.pro.task.desk.svc.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ProjectTypeRepository extends JpaRepository<ProjectType, Integer>  {

    @Query("SELECT pt FROM ProjectType pt ORDER BY pt.typeName ASC")
    List<ProjectType> get();

}
