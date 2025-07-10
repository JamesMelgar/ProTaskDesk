package com.gyt.seguros.pro.task.desk.svc.impl;

import com.gyt.seguros.pro.task.desk.dal.repository.ProjectRepository;
import com.gyt.seguros.pro.task.desk.dal.repository.ProjectTypeRepository;
import com.gyt.seguros.pro.task.desk.dal.model.Project;
import com.gyt.seguros.pro.task.desk.dal.model.ProjectType;
import com.gyt.seguros.pro.task.desk.dal.model.User;
import com.gyt.seguros.pro.task.desk.dal.model.enums.ProjectStatus;
import com.gyt.seguros.pro.task.desk.svc.CreateProjectSvc;
import com.gyt.seguros.pro.task.desk.svc.exceptions.DuplicateProjectNameException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.InvalidProjectDataException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.InvalidProjectDatesException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.ProjectCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


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

        String projectName = projectToCreate.getProjectName();
        String description = projectToCreate.getDescription();
        LocalDate startDate = projectToCreate.getStartDate();
        LocalDate endDate = projectToCreate.getEndDate();
        ProjectType projectType = projectToCreate.getProjectType();
        User createdByUser = projectToCreate.getCreatedBy();

        if (!validateProjectData(projectName, description, startDate, endDate, projectType)) {
            throw new InvalidProjectDataException("Datos del proyecto inválidos. Por favor, revise la información.");
        }

        if (createdByUser == null) {
            throw new IllegalArgumentException("El usuario creador es requerido para la creación del proyecto.");
        }

        if (projectNameExists(projectName)) {
            throw new DuplicateProjectNameException("Ya existe un proyecto con el nombre: '" + projectName + "'");
        }

        if (projectToCreate.getStatus() == null) {
            projectToCreate.setStatus(ProjectStatus.ACTIVE);
        }

        try {
            Project savedProject = projectRepository.save(projectToCreate);
            taskCreationService.createDefaultTasksForProject(savedProject, createdByUser);
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

        if (startDate.isBefore(LocalDate.now())) {
            throw new InvalidProjectDatesException("La fecha de inicio no puede ser anterior a la fecha actual.");
        }

        if (endDate.isBefore(startDate)) {
            throw new InvalidProjectDatesException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        return true;
    }

    @Override
    public boolean validateProjectData(String projectName, String description,
                                       LocalDate startDate, LocalDate endDate,
                                       ProjectType projectType) {

        if (projectName == null || projectName.trim().isEmpty()) {
            throw new InvalidProjectDataException("El nombre del proyecto es obligatorio.");
        }

        if (projectName.trim().length() > 255) {
            throw new InvalidProjectDataException("El nombre del proyecto no puede exceder los 255 caracteres.");
        }

        if (description != null && description.trim().length() > 1000) {
            throw new InvalidProjectDataException("La descripción del proyecto no puede exceder los 1000 caracteres.");
        }

        validateProjectDates(startDate, endDate);

        if (projectType == null) {
            throw new InvalidProjectDataException("El tipo de proyecto es obligatorio.");
        }

        return true;
    }
}
