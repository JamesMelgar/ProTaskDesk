package com.gyt.seguros.pro.task.desk.svc.impl;

import com.gyt.seguros.pro.task.desk.dal.repository.ProjectRepository;
import com.gyt.seguros.pro.task.desk.dal.repository.ProjectTypeRepository;
import com.gyt.seguros.pro.task.desk.dal.model.Project;
import com.gyt.seguros.pro.task.desk.dal.model.ProjectType;
import com.gyt.seguros.pro.task.desk.dal.model.User;
import com.gyt.seguros.pro.task.desk.dal.model.enums.ProjectStatus;
import com.gyt.seguros.pro.task.desk.svc.CreateProjectSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CreateProjectSvcImpl implements CreateProjectSvc {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @Autowired
    private TaskCreationService taskCreationService;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectType> getProjectTypes() {
        return projectTypeRepository.findAllOrderByTypeName();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectType getProjectTypeById(Integer projectTypeId) {
        if (projectTypeId == null) {
            return null;
        }
        Optional<ProjectType> projectType = projectTypeRepository.findById(projectTypeId);
        return projectType.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean projectNameExists(String projectName) {
        if (projectName == null || projectName.trim().isEmpty()) {
            return false;
        }
        return projectRepository.existsByProjectName(projectName.trim());
    }

    @Override
    public Project createProject(String projectName, String description,
                                 LocalDate startDate, LocalDate endDate,
                                 ProjectType projectType, User createdByUser) {

        if (!validateProjectData(projectName, description, startDate, endDate, projectType)) {
            throw new IllegalArgumentException("Datos del proyecto inválidos");
        }

        if (createdByUser == null) {
            throw new IllegalArgumentException("El usuario creador es requerido");
        }

        if (projectNameExists(projectName)) {
            throw new IllegalArgumentException("Ya existe un proyecto con el nombre: " + projectName);
        }

        Project project = new Project();
        project.setProjectName(projectName.trim());
        project.setDescription(description != null ? description.trim() : null);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setStatus(ProjectStatus.ACTIVE);
        project.setProjectType(projectType);
        project.setCreatedBy(createdByUser);

        Project savedProject = projectRepository.save(project);

        taskCreationService.createDefaultTasksForProject(savedProject, createdByUser);

        return savedProject;
    }

    @Override
    public boolean validateProjectDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }

        if (startDate.isBefore(LocalDate.now())) {
            return false;
        }

        return !endDate.isBefore(startDate);
    }

    @Override
    public boolean validateProjectData(String projectName, String description,
                                       LocalDate startDate, LocalDate endDate,
                                       ProjectType projectType) {

        if (projectName == null || projectName.trim().isEmpty()) {
            return false;
        }

        if (projectName.trim().length() > 255) {
            return false;
        }

        if (description != null && description.trim().length() > 1000) {
            return false;
        }

        if (!validateProjectDates(startDate, endDate)) {
            return false;
        }

        return projectType != null;
    }
}
