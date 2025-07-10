package com.gyt.seguros.pro.task.desk.svc.exceptions;

public class ProjectCreationException  extends RuntimeException {

    public ProjectCreationException(String message) {
        super(message);
    }

    public ProjectCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
