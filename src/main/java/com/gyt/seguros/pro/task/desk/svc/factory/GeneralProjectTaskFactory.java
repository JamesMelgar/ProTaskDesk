package com.gyt.seguros.pro.task.desk.svc.factory;


import com.gyt.seguros.pro.task.desk.svc.project.Project;
import com.gyt.seguros.pro.task.desk.svc.task.Task;
import com.gyt.seguros.pro.task.desk.svc.user.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("generalProjectTaskFactory")
public final class GeneralProjectTaskFactory implements TaskFactory {

    @Override
    public List<Task> createDefaultTasks(Project project, User creatorUser) {
        return Collections.emptyList();
    }
}