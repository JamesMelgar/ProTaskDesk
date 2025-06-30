
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
                new Task(project, "Análisis de Audiencia y Competencia", "Realizar investigación para entender al público objetivo y el panorama competitivo.", TaskStatus.PENDING, TaskPriority.HIGH, projectStartDate.plusDays(5), creatorUser, creatorUser),
                new Task(project, "Desarrollo de Estrategia de Contenido", "Definir los temas, formatos y canales para el contenido.", TaskStatus.PENDING, TaskPriority.HIGH, projectStartDate.plusDays(10), creatorUser, creatorUser),
                new Task(project, "Creación de Creatividades y Mensajes", "Diseñar gráficos, videos y escribir textos para las campañas.", TaskStatus.PENDING, TaskPriority.MEDIUM, projectStartDate.plusDays(15), creatorUser, creatorUser),
                new Task(project, "Configuración y Lanzamiento de Campañas", "Configurar anuncios en plataformas (Google Ads, Facebook Ads, etc.) y lanzarlos.", TaskStatus.PENDING, TaskPriority.HIGH, projectStartDate.plusDays(20), creatorUser, creatorUser),
                new Task(project, "Monitoreo, Análisis y Optimización", "Seguir el rendimiento de las campañas y realizar ajustes para mejorar resultados.", TaskStatus.PENDING, TaskPriority.MEDIUM, projectStartDate.plusDays(30), creatorUser, creatorUser)
        );
    }
}
