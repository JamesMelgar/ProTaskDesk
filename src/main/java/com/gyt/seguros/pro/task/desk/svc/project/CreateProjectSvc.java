package com.gyt.seguros.pro.task.desk.svc.project;

import java.time.LocalDate;
import java.util.List;

public interface CreateProjectSvc {

    List<ProjectType> getProjectTypes();

    boolean projectNameExists(String projectName);

    Project createProject(Project projectToCreate);

    boolean validateProjectDates(LocalDate startDate, LocalDate endDate);

    boolean validateProjectData(Project project);
}