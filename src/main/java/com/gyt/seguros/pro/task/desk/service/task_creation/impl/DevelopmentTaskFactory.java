package com.gyt.seguros.pro.task.desk.service.task_creation.impl;

import com.gyt.seguros.pro.task.desk.model.Project;
import com.gyt.seguros.pro.task.desk.model.Task;
import com.gyt.seguros.pro.task.desk.model.User;
import com.gyt.seguros.pro.task.desk.model.enums.TaskStatus;
import com.gyt.seguros.pro.task.desk.model.enums.TaskPriority;
import com.gyt.seguros.pro.task.desk.service.task_creation.TaskFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

import java.util.List;

@Component("developmentTaskFactory")
public class DevelopmentTaskFactory implements TaskFactory {

    @Override
    public List<Task> createDefaultTasks(Project project, User creatorUser) {
        LocalDate projectStartDate = project.getStartDate();

        return Arrays.asList(
                new Task(project, "Definición de Requisitos", "Recopilar y documentar los requisitos funcionales y no funcionales del software.", TaskStatus.PENDING, TaskPriority.HIGH, projectStartDate.plusDays(7), creatorUser, creatorUser),
                new Task(project, "Diseño de Arquitectura y Base de Datos", "Diseñar la estructura del sistema, los módulos principales y el esquema de la base de datos.", TaskStatus.PENDING, TaskPriority.HIGH, projectStartDate.plusDays(14), creatorUser, creatorUser),
                new Task(project, "Desarrollo de Módulos Core", "Implementar la lógica de negocio central del sistema.", TaskStatus.PENDING, TaskPriority.MEDIUM, projectStartDate.plusDays(28), creatorUser, creatorUser),
                new Task(project, "Implementación de Interfaz de Usuario (UI)", "Desarrollar la parte visual y la interacción del usuario.", TaskStatus.PENDING, TaskPriority.MEDIUM, projectStartDate.plusDays(35), creatorUser, creatorUser),
                new Task(project, "Pruebas Unitarias y de Integración", "Realizar pruebas de cada componente y de la interacción entre ellos.", TaskStatus.PENDING, TaskPriority.HIGH, projectStartDate.plusDays(42), creatorUser, creatorUser),
                new Task(project, "Preparación para Despliegue", "Configurar el entorno y preparar la aplicación para su lanzamiento.", TaskStatus.PENDING, TaskPriority.MEDIUM, projectStartDate.plusDays(50), creatorUser, creatorUser)
        );
    }
}
