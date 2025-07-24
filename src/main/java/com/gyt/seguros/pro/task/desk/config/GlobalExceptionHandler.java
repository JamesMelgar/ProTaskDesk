package com.gyt.seguros.pro.task.desk.config;

import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions;
import com.gyt.seguros.pro.task.desk.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

@Component
public final class GlobalExceptionHandler {

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

    public void handleDuplicateUsernameException(Exceptions.DuplicateUsernameException ex, java.awt.Component parentComponent) {
        logger.warn("Intento de registro con usuario duplicado: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_REGISTRE,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleDuplicateEmailException(Exceptions.DuplicateEmailException ex, java.awt.Component parentComponent) {
        logger.warn("Intento de registro con email duplicado: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_REGISTRE,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleUserRegistrationException(Exceptions.UserRegistrationException ex, java.awt.Component parentComponent) {
        logger.error("Error de lógica de negocio al registrar usuario: {}", ex.getMessage(), ex);
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_REGISTRE,
                JOptionPane.ERROR_MESSAGE
        ));
    }

    public void handleInvalidRegistrationDataException(Exceptions.InvalidRegistrationDataException ex, java.awt.Component parentComponent) {
        logger.warn("Datos de registro inválidos: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_REGISTRE,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleProjectCreationException(Exceptions.ProjectCreationException ex, java.awt.Component parentComponent) {
        logger.error("Error al crear proyecto: {}", ex.getMessage(), ex);
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_PROJECT,
                JOptionPane.ERROR_MESSAGE
        ));
    }

    public void handleDuplicateProjectNameException(Exceptions.DuplicateProjectNameException ex, java.awt.Component parentComponent) {
        logger.warn("Intento de crear proyecto con nombre duplicado: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_PROJECT,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleInvalidProjectDataException(Exceptions.InvalidProjectDataException ex, java.awt.Component parentComponent) {
        logger.warn("Datos de proyecto inválidos: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_PROJECT,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleInvalidProjectDatesException(Exceptions.InvalidProjectDatesException ex, java.awt.Component parentComponent) {
        logger.warn("Fechas de proyecto inválidas: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_PROJECT,
                JOptionPane.WARNING_MESSAGE
        ));
    }

    public void handleInvalidLoginCredentialsException(Exceptions.InvalidLoginCredentialsException ex, java.awt.Component parentComponent) {
        logger.warn("Credenciales de login inválidas: {}", ex.getMessage());
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                parentComponent,
                ex.getMessage(),
                TITLE_ERROR_LOGIN,
                JOptionPane.WARNING_MESSAGE
        ));
    }
}
