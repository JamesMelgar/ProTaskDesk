package com.gyt.seguros.pro.task.desk.service.task_creation;

import com.gyt.seguros.pro.task.desk.model.Project;
import com.gyt.seguros.pro.task.desk.model.Task;
import com.gyt.seguros.pro.task.desk.model.User;

import java.util.List;

public interface TaskFactory {
    List<Task> createDefaultTasks(Project project, User creatorUser);
}
