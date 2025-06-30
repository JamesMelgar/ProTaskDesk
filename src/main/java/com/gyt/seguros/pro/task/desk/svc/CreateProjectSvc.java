package com.gyt.seguros.pro.task.desk.svc;

import com.gyt.seguros.pro.task.desk.dal.model.Project;
import com.gyt.seguros.pro.task.desk.dal.model.ProjectType;
import com.gyt.seguros.pro.task.desk.dal.model.User;

import java.time.LocalDate;
import java.util.List;

public interface CreateProjectSvc {

    List<ProjectType> getProjectTypes();

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