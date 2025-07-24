package com.gyt.seguros.pro.task.desk.svc.project;

import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.DuplicateProjectNameException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.InvalidProjectDataException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.InvalidProjectDatesException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.ProjectCreationException;
import com.gyt.seguros.pro.task.desk.svc.task.TaskCreationSvc;
import com.gyt.seguros.pro.task.desk.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
class CreateProjectSvcImpl implements CreateProjectSvc {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @Autowired
    private TaskCreationSvc taskCreationService;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectType> getProjectTypes() {
        return projectTypeRepository.get();
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
    public Project createProject(Project projectToCreate) {
        if (projectToCreate == null) {
            throw new IllegalArgumentException("El objeto Project no puede ser nulo.");
        }
        if (projectToCreate.getCreatedBy() == null) {
            throw new IllegalArgumentException("El usuario creador es requerido para la creación del proyecto.");
        }

        try {
            validateProjectData(projectToCreate);
        } catch (IllegalArgumentException e) {
            throw new InvalidProjectDataException(e.getMessage(), e);
        }

        if (projectNameExists(projectToCreate.getProjectName())) {
            throw new DuplicateProjectNameException("Ya existe un proyecto con el nombre: '" + projectToCreate.getProjectName() + "'");
        }

        if (projectToCreate.getStatus() == null) {
            projectToCreate.setStatus(ProjectStatus.ACTIVE);
        }

        try {
            Project savedProject = projectRepository.save(projectToCreate);
            taskCreationService.createDefaultTasksForProject(savedProject, projectToCreate.getCreatedBy());
            return savedProject;
        } catch (Exception e) {
            throw new ProjectCreationException("Error interno al persistir el proyecto: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean validateProjectDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidProjectDatesException("Las fechas de inicio y fin son obligatorias.");
        }
        ValidationUtil.validateDateNotBeforeCurrent(startDate, "fecha de inicio");
        ValidationUtil.validateStartDateBeforeEndDate(startDate, endDate);
        return true;
    }


    @Override
    public boolean validateProjectData(Project project) {

        ValidationUtil.validateNotNullOrEmpty(project, "projectName", "projectType", "startDate", "endDate");

        ValidationUtil.validateStringMaxLength(project.getProjectName(), 255, "nombre del proyecto");
        ValidationUtil.validateStringMaxLength(project.getDescription(), 1000, "descripción del proyecto");

        validateProjectDates(project.getStartDate(), project.getEndDate());

        return true;
    }


}
