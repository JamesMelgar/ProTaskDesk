package com.gyt.seguros.pro.task.desk.svc.factory;

import com.gyt.seguros.pro.task.desk.svc.project.Project;
import com.gyt.seguros.pro.task.desk.svc.task.Task;
import com.gyt.seguros.pro.task.desk.svc.user.User;

import java.util.List;

public interface TaskFactory {
    List<Task> createDefaultTasks(Project project, User creatorUser);
}
