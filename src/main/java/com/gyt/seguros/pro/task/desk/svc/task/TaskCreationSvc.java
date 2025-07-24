package com.gyt.seguros.pro.task.desk.svc.task;

import com.gyt.seguros.pro.task.desk.svc.project.Project;
import com.gyt.seguros.pro.task.desk.svc.user.User;

public interface TaskCreationSvc {
    void createDefaultTasksForProject(Project project, User assignedTo);
}
