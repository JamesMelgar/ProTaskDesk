package com.gyt.seguros.pro.task.desk.config;

import com.gyt.seguros.pro.task.desk.svc.exceptions.*;
import com.gyt.seguros.pro.task.desk.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

@Component
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String TITLE_ERROR_REGISTRE = "Error de Registro";
    private static final String TITLE_ERROR_LOGIN = "Error de Login";
    private static final String TITLE_ERROR_PROJECT = "Error de Proyecto";

    public void handleException(Throwable ex, java.awt.Component parentComponent, String customMessage) {
        logger.error("Error inesperado en la aplicación: {}", ex.getMessage(), ex);
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                customMessage != null && !customMessage.isEmpty() ? customMessage : AppConstants.MESSAGE_ERROR_UNEXPECTED,
                "Error",
                JOptionPane.ERROR_MESSAGE
        ));
    }

    public void handleDuplicateUsernameException(DuplicateUsernameException ex, java.awt.Component parentComponent) {
        logger.warn("Intento de registro con usuario duplicado: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_REGISTRE,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleDuplicateEmailException(DuplicateEmailException ex, java.awt.Component parentComponent) {
        logger.warn("Intento de registro con email duplicado: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_REGISTRE,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleUserRegistrationException(UserRegistrationException ex, java.awt.Component parentComponent) {
        logger.error("Error de lógica de negocio al registrar usuario: {}", ex.getMessage(), ex);
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_REGISTRE,
                JOptionPane.ERROR_MESSAGE
        ));
    }

    public void handleInvalidRegistrationDataException(InvalidRegistrationDataException ex, java.awt.Component parentComponent) {
        logger.warn("Datos de registro inválidos: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_REGISTRE,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleProjectCreationException(ProjectCreationException ex, java.awt.Component parentComponent) {
        logger.error("Error al crear proyecto: {}", ex.getMessage(), ex);
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_PROJECT,
                JOptionPane.ERROR_MESSAGE
        ));
    }

    public void handleDuplicateProjectNameException(DuplicateProjectNameException ex, java.awt.Component parentComponent) {
        logger.warn("Intento de crear proyecto con nombre duplicado: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_PROJECT,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleInvalidProjectDataException(InvalidProjectDataException ex, java.awt.Component parentComponent) {
        logger.warn("Datos de proyecto inválidos: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_PROJECT,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleInvalidLoginCredentialsException(InvalidLoginCredentialsException ex, java.awt.Component parentComponent) {
        logger.warn("Credenciales de login inválidas: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_LOGIN,
                JOptionPane.WARNING_MESSAGE
        ));
    }
}
