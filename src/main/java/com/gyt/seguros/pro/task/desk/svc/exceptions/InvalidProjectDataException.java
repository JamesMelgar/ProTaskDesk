package com.gyt.seguros.pro.task.desk.svc.exceptions;

public class InvalidProjectDataException extends Exception {

    public InvalidProjectDataException(String message) {
        super(message);
    }

    public InvalidProjectDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

