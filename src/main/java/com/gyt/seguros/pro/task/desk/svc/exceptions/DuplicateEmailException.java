package com.gyt.seguros.pro.task.desk.svc.exceptions;

public class DuplicateEmailException extends UserRegistrationException {
    public DuplicateEmailException(String email) {
        super("El email '" + email + "' ya está registrado.");
    }
}
