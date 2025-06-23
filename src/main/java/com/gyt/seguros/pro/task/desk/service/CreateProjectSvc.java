package com.gyt.seguros.pro.task.desk.service;

import com.gyt.seguros.pro.task.desk.model.Project;
import com.gyt.seguros.pro.task.desk.model.ProjectType;
import com.gyt.seguros.pro.task.desk.model.User;

import java.time.LocalDate;
import java.util.List;

public interface CreateProjectSvc {

    List<ProjectType> getAllProjectTypes();

    ProjectType getProjectTypeById(Integer projectTypeId);

    boolean projectNameExists(String projectName);

    Project createProject(String projectName, String description,
                          LocalDate startDate, LocalDate endDate,
                          ProjectType projectType, User createdByUser);

    boolean validateProjectDates(LocalDate startDate, LocalDate endDate);


    boolean validateProjectData(String projectName, String description,
                                LocalDate startDate, LocalDate endDate,
                                ProjectType projectType);
}