package com.gyt.seguros.pro.task.desk.svc.exceptions;

public class InvalidLoginCredentialsException  extends Exception {

    public InvalidLoginCredentialsException(String message) {
        super(message);
    }

    public InvalidLoginCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}

