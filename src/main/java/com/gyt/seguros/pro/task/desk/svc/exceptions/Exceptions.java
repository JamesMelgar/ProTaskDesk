package com.gyt.seguros.pro.task.desk.svc.exceptions;

public class Exceptions {

    private Exceptions(){}

    public static class DuplicateEmailException extends RuntimeException {
        public DuplicateEmailException(String email) {
            super("El email '" + email + "' ya está registrado.");
        }
    }

    public static class InvalidProjectDataException extends RuntimeException {
        public InvalidProjectDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class DuplicateProjectNameException  extends RuntimeException {
        public DuplicateProjectNameException(String message) {super(message); }
    }

    public static class DuplicateUsernameException extends UserRegistrationException {
        public DuplicateUsernameException(String username) {
            super("El nombre de usuario '" + username + "' ya está en uso.");
        }
    }

    public static class InvalidLoginCredentialsException  extends Exception {
        public InvalidLoginCredentialsException(String message) { super(message); }
    }

    public static class InvalidProjectDatesException extends IllegalArgumentException {
        public InvalidProjectDatesException(String message) {
            super(message);
        }
    }

    public static class InvalidRegistrationDataException extends RuntimeException {
        public InvalidRegistrationDataException(String message) { super(message); }
    }

    public static class ProjectCreationException  extends RuntimeException {
        public ProjectCreationException(String message) {
            super(message);
        }
        public ProjectCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class UserRegistrationException extends RuntimeException {
        public UserRegistrationException(String message) {
            super(message);
        }
        public UserRegistrationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
