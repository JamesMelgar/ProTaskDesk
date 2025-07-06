package com.gyt.seguros.pro.task.desk.svc.factory.impl;

import com.gyt.seguros.pro.task.desk.dal.model.Project;
import com.gyt.seguros.pro.task.desk.dal.model.Task;
import com.gyt.seguros.pro.task.desk.dal.model.User;
import com.gyt.seguros.pro.task.desk.dal.model.enums.TaskPriority;
import com.gyt.seguros.pro.task.desk.dal.model.enums.TaskStatus;
import com.gyt.seguros.pro.task.desk.svc.factory.TaskFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component("marketingTaskFactory")
public class MarketingTaskFactory implements TaskFactory {

    @Override
    public List<Task> createDefaultTasks(Project project, User creatorUser) {
        LocalDate projectStartDate = project.getStartDate();

        return Arrays.asList(
                Task.builder()
                        .project(project)
                        .taskName("Análisis de Audiencia y Competencia")
                        .description("Realizar investigación para entender al público objetivo y el panorama competitivo.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.HIGH)
                        .dueDate(projectStartDate.plusDays(5))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build(),

                Task.builder()
                        .project(project)
                        .taskName("Desarrollo de Estrategia de Contenido")
                        .description("Definir los temas, formatos y canales para el contenido.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.HIGH)
                        .dueDate(projectStartDate.plusDays(10))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build(),

                Task.builder()
                        .project(project)
                        .taskName("Creación de Creatividades y Mensajes")
                        .description("Diseñar gráficos, videos y escribir textos para las campañas.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.MEDIUM)
                        .dueDate(projectStartDate.plusDays(15))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build(),

                Task.builder()
                        .project(project)
                        .taskName("Configuración y Lanzamiento de Campañas")
                        .description("Configurar anuncios en plataformas (Google Ads, Facebook Ads, etc.) y lanzarlos.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.HIGH)
                        .dueDate(projectStartDate.plusDays(20))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build(),

                Task.builder()
                        .project(project)
                        .taskName("Monitoreo, Análisis y Optimización")
                        .description("Seguir el rendimiento de las campañas y realizar ajustes para mejorar resultados.")
                        .status(TaskStatus.PENDING)
                        .priority(TaskPriority.MEDIUM)
                        .dueDate(projectStartDate.plusDays(30))
                        .assignedTo(creatorUser)
                        .createdBy(creatorUser)
                        .build()
        );
    }
}
