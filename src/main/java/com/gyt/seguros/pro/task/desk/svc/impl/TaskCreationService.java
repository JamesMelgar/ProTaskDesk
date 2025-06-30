package com.gyt.seguros.pro.task.desk.svc.impl;

import com.gyt.seguros.pro.task.desk.dal.repository.TaskRepository;
import com.gyt.seguros.pro.task.desk.dal.model.Project;
import com.gyt.seguros.pro.task.desk.dal.model.Task;
import com.gyt.seguros.pro.task.desk.dal.model.User;
import com.gyt.seguros.pro.task.desk.svc.factory.TaskFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class TaskCreationService {
    private final TaskRepository taskRepository;
    private final TaskFactory developmentTaskFactory;
    private final TaskFactory marketingTaskFactory;
    private final TaskFactory generalProjectTaskFactory;

    @Autowired
    public TaskCreationService(
            TaskRepository taskRepository,
            @Qualifier("developmentTaskFactory") TaskFactory developmentTaskFactory,
            @Qualifier("marketingTaskFactory") TaskFactory marketingTaskFactory,
            @Qualifier("generalProjectTaskFactory") TaskFactory generalProjectTaskFactory) {
        this.taskRepository = taskRepository;
        this.developmentTaskFactory = developmentTaskFactory;
        this.marketingTaskFactory = marketingTaskFactory;
        this.generalProjectTaskFactory = generalProjectTaskFactory;
    }

    public void createDefaultTasksForProject(Project project, User assignedTo) {
        List<Task> defaultTasks = switch (project.getProjectType().getTypeName()) {
            case "Desarrollo de Software" ->
                    developmentTaskFactory.createDefaultTasks(project, assignedTo);
            case "Marketing Digital" ->
                    marketingTaskFactory.createDefaultTasks(project, assignedTo);
            case "Proyecto General" ->
                    generalProjectTaskFactory.createDefaultTasks(project, assignedTo);
            default ->
                    Collections.emptyList();
        };

        if (!defaultTasks.isEmpty()) {
            taskRepository.saveAll(defaultTasks);
        }
    }
}
