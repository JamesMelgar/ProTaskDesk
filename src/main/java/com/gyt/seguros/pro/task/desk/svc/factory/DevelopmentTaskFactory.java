package com.gyt.seguros.pro.task.desk.svc.factory;

import com.gyt.seguros.pro.task.desk.svc.project.Project;
import com.gyt.seguros.pro.task.desk.svc.task.Task;
import com.gyt.seguros.pro.task.desk.svc.task.TaskPriority;
import com.gyt.seguros.pro.task.desk.svc.task.TaskStatus;
import com.gyt.seguros.pro.task.desk.svc.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component("developmentTaskFactory")
public final class DevelopmentTaskFactory implements TaskFactory {

    @Override
    public List<Task> createDefaultTasks(Project project, User creatorUser) {
        LocalDate projectStartDate = project.getStartDate();

        return Arrays.asList(
                Task.builder()
                        .project(project)
                        .taskName("Definición de Requisitos")
                        .description("Recopilar y documentar los requisitos funcionales y no funcionales del software.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.HIGH)
                        .dueDate(projectStartDate.plusDays(7))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build(),

                Task.builder()
                        .project(project)
                        .taskName("Diseño de Arquitectura y Base de Datos")
                        .description("Diseñar la estructura del sistema, los módulos principales y el esquema de la base de datos.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.HIGH)
                        .dueDate(projectStartDate.plusDays(14))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build(),

                Task.builder()
                        .project(project)
                        .taskName("Desarrollo de Módulos Core")
                        .description("Implementar la lógica de negocio central del sistema.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.MEDIUM)
                        .dueDate(projectStartDate.plusDays(28))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build(),

                Task.builder()
                        .project(project)
                        .taskName("Implementación de Interfaz de Usuario (UI)")
                        .description("Desarrollar la parte visual y la interacción del usuario.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.MEDIUM)
                        .dueDate(projectStartDate.plusDays(35))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build(),

                Task.builder()
                        .project(project)
                        .taskName("Pruebas Unitarias y de Integración")
                        .description("Realizar pruebas de cada componente y de la interacción entre ellos.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.HIGH)
                        .dueDate(projectStartDate.plusDays(42))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build(),

                Task.builder()
                        .project(project)
                        .taskName("Preparación para Despliegue")
                        .description("Configurar el entorno y preparar la aplicación para su lanzamiento.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.MEDIUM)
                        .dueDate(projectStartDate.plusDays(50))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build()
        );
    }
}
