package com.gyt.seguros.pro.task.desk.svc.factory;

import com.gyt.seguros.pro.task.desk.dal.model.Project;
import com.gyt.seguros.pro.task.desk.dal.model.Task;
import com.gyt.seguros.pro.task.desk.dal.model.User;

import java.util.List;

public interface TaskFactory {
    List<Task> createDefaultTasks(Project project, User creatorUser);
}
