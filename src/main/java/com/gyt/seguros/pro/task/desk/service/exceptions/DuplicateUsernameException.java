package com.gyt.seguros.pro.task.desk.service.exceptions;

public class DuplicateUsernameException extends UserRegistrationException {
    public DuplicateUsernameException(String username) {
        super("El nombre de usuario '" + username + "' ya está en uso.");
    }
}
