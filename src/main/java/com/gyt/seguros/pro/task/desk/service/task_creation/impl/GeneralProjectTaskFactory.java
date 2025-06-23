package com.gyt.seguros.pro.task.desk.service.task_creation.impl;

import com.gyt.seguros.pro.task.desk.model.Project;
import com.gyt.seguros.pro.task.desk.model.Task;
import com.gyt.seguros.pro.task.desk.model.User;
import com.gyt.seguros.pro.task.desk.service.task_creation.TaskFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("generalProjectTaskFactory")
public class GeneralProjectTaskFactory implements TaskFactory {

    @Override
    public List<Task> createDefaultTasks(Project project, User creatorUser) {
        return Collections.emptyList();
    }
}