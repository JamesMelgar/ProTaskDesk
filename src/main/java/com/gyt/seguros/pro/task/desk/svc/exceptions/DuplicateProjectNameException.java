package com.gyt.seguros.pro.task.desk.svc.exceptions;

public class DuplicateProjectNameException extends Exception {

    public DuplicateProjectNameException(String message) {
        super(message);
    }

    public DuplicateProjectNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
