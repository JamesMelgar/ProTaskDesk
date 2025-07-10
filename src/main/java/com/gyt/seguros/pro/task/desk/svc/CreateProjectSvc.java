package com.gyt.seguros.pro.task.desk.svc;

import com.gyt.seguros.pro.task.desk.dal.model.Project;
import com.gyt.seguros.pro.task.desk.dal.model.ProjectType;

import java.time.LocalDate;
import java.util.List;

public interface CreateProjectSvc {

    List<ProjectType> getProjectTypes();

    boolean projectNameExists(String projectName);

    Project createProject(Project projectToCreate);

    boolean validateProjectDates(LocalDate startDate, LocalDate endDate);


    boolean validateProjectData(String projectName, String description,
                                LocalDate startDate, LocalDate endDate,
                                ProjectType projectType);
}