package com.gyt.seguros.pro.task.desk.svc.factory.impl;


import com.gyt.seguros.pro.task.desk.dal.model.Project;
import com.gyt.seguros.pro.task.desk.dal.model.Task;
import com.gyt.seguros.pro.task.desk.dal.model.User;
import com.gyt.seguros.pro.task.desk.svc.factory.TaskFactory;
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