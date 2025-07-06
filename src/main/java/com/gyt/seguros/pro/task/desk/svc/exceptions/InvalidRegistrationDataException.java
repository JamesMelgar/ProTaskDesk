package com.gyt.seguros.pro.task.desk.svc.exceptions;

public class InvalidRegistrationDataException extends RuntimeException {

    public InvalidRegistrationDataException(String message) {
        super(message);
    }

    public InvalidRegistrationDataException(String message, Throwable cause) {
        super(message, cause);
    }
}