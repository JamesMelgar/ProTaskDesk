package com.gyt.seguros.pro.task.desk.ui.screen;

import com.gyt.seguros.pro.task.desk.ProTaskDesk;
import com.gyt.seguros.pro.task.desk.config.GlobalExceptionHandler;
import com.gyt.seguros.pro.task.desk.svc.register.RegisterSvc;
import com.gyt.seguros.pro.task.desk.svc.dto.UserRegistrationRequest;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.DuplicateEmailException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.DuplicateUsernameException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.InvalidRegistrationDataException;
import com.gyt.seguros.pro.task.desk.svc.exceptions.Exceptions.UserRegistrationException;
import com.gyt.seguros.pro.task.desk.util.AppConstants;

import javax.swing.*;
import java.awt.*;

import javax.swing.WindowConstants;

public class RegisterScreen extends JFrame {

    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel messageLabel;

    private transient RegisterSvc registerService;
    private transient GlobalExceptionHandler exceptionHandler;

    private static final String TITLE_PANEL = "ProTaskDesk - Registro de Usuario";
    private static final String HEADER = "¡Únete a ProTaskDesk!";
    private static final String SUBTITLE = "Crea tu cuenta para comenzar";
    private static final String TITLE_BUTTON_REGISTER = "Registrar";
    private static final String TITLE_BUTTON_CANCEL = "Cancelar";
    private static final String LABEL_FULL_NAME = "Nombre Completo:";
    private static final String LABEL_USERNAME = "Usuario:";
    private static final String LABEL_PASSWORD = "Contraseña:";
    private static final String LABEL_CONFIRM_PASSWORD = "Confirmar Contraseña:";
    private static final String LABEL_EMAIL = "Email:";

    private static final String MESSAGE_REGISTRATION_SUCCESS = "¡Registro exitoso! Ya puedes iniciar sesión.";
    private static final String MESSAGE_REGISTRATION_SUCCESS_DIALOG_TITLE = "Registro Exitoso";
    private static final String MESSAGE_PASSWORDS_MISMATCH = "Las contraseñas no coinciden.";

    public RegisterScreen() {
        this.registerService = ProTaskDesk.getBean(RegisterSvc.class);
        this.exceptionHandler = ProTaskDesk.getBean(GlobalExceptionHandler.class);
        initComponents();

        setContentPane(mainPanel);
        setTitle(TITLE_PANEL);
        setSize(650, 840);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setupEventHandlers();
        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppConstants.LIGHT_BLUE_BACKGROUND);

        JPanel headerPanel = createHeaderPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel footerPanel = createFooterPanel();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppConstants.PRIMARY_BLUE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        JLabel titleLabel = new JLabel(HEADER, SwingConstants.CENTER);
        titleLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(SUBTITLE, SwingConstants.CENTER);
        subtitleLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        textPanel.setBackground(AppConstants.PRIMARY_BLUE);
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);

        panel.add(textPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppConstants.LIGHT_BLUE_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        JPanel registerFormPanel = createRegisterForm();

        JPanel buttonPanel = createButtonPanel();

        JPanel messagePanel = createMessagePanel();

        panel.add(registerFormPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(AppConstants.LIGHT_BLUE_BACKGROUND);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(messagePanel, BorderLayout.SOUTH);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRegisterForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(AppConstants.CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.PRIMARY_BLUE, 2),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel formTitle = new JLabel("Crear Nueva Cuenta", SwingConstants.CENTER);
        formTitle.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 20));
        formTitle.setForeground(AppConstants.PRIMARY_BLUE);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(formTitle, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(15, 15, 10, 15);
        panel.add(new JLabel(""), gbc);
        fullNameField = createStyledTextField();
        addFieldToForm(panel, gbc, LABEL_FULL_NAME, fullNameField, 2);
        usernameField = createStyledTextField();
        addFieldToForm(panel, gbc, LABEL_USERNAME, usernameField, 3);
        emailField = createStyledTextField();
        addFieldToForm(panel, gbc, LABEL_EMAIL, emailField , 4);
        passwordField = createStyledPasswordField();
        addFieldToForm(panel, gbc, LABEL_PASSWORD, passwordField , 5);
        confirmPasswordField = createStyledPasswordField();
        addFieldToForm(panel, gbc, LABEL_CONFIRM_PASSWORD, confirmPasswordField, 6);
        return panel;
    }

    private void addFieldToForm(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int baseRow) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 14));
        label.setForeground(AppConstants.TEXT_DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = baseRow * 2 - 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 15, 5, 15);
        panel.add(label, gbc);

        gbc.gridy = baseRow * 2 - 1;
        gbc.insets = new Insets(0, 15, 10, 15);
        panel.add(field, gbc);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(320, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.PRIMARY_BLUE),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(320, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.PRIMARY_BLUE),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        panel.setBackground(AppConstants.LIGHT_BLUE_BACKGROUND);

        registerButton = createStyledButton(TITLE_BUTTON_REGISTER, AppConstants.PRIMARY_BLUE);
        cancelButton = createStyledButton(TITLE_BUTTON_CANCEL, AppConstants.ACCENT_BLUE);

        panel.add(registerButton);
        panel.add(cancelButton);

        return panel;
    }

    private JPanel createMessagePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(AppConstants.LIGHT_BLUE_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 20));

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 14));
        messageLabel.setPreferredSize(new Dimension(500, 25));

        panel.add(messageLabel);
        return panel;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(130, AppConstants.BUTTON_HEIGHT));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppConstants.LIGHT_BLUE_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel footerLabel = new JLabel("© 2025 ProTaskDesk - Sistema de Gestión de Tareas y Proyectos", SwingConstants.CENTER);
        footerLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 11));
        footerLabel.setForeground(AppConstants.TEXT_DARK_GRAY);

        panel.add(footerLabel, BorderLayout.CENTER);
        return panel;
    }

    private void setupEventHandlers() {
        registerButton.addActionListener(e -> handleRegistration());

        cancelButton.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(registerButton);
    }

    private void handleRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                fullName.isEmpty() || email.isEmpty()) {
            messageLabel.setText(AppConstants.MESSAGE_ERROR_FIELDS_REQUIRED);
            messageLabel.setForeground(AppConstants.ERROR_COLOR);
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText(MESSAGE_PASSWORDS_MISMATCH);
            messageLabel.setForeground(AppConstants.ERROR_COLOR);
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }

        UserRegistrationRequest request = UserRegistrationRequest.builder()
                .username(username)
                .password(password)
                .confirmPassword(confirmPassword)
                .fullName(fullName)
                .email(email)
                .build();

        try {
            boolean registrationSuccess = registerService.registerUser(request);

            if (registrationSuccess) {
                messageLabel.setText(MESSAGE_REGISTRATION_SUCCESS);
                messageLabel.setForeground(AppConstants.SUCCESS_COLOR);

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            "Usuario '" + request.getUsername() + "' registrado con éxito.",
                            MESSAGE_REGISTRATION_SUCCESS_DIALOG_TITLE,
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                });
            } else {
                messageLabel.setText(AppConstants.MESSAGE_ERROR_UNEXPECTED);
                messageLabel.setForeground(AppConstants.ERROR_COLOR);
            }
        } catch (DuplicateUsernameException ex) {
            exceptionHandler.handleDuplicateUsernameException(ex, this);
            clearSensitiveFields();
        } catch (DuplicateEmailException ex) {
            exceptionHandler.handleDuplicateEmailException(ex, this);
            clearSensitiveFields();
        } catch (InvalidRegistrationDataException ex) {
            exceptionHandler.handleInvalidRegistrationDataException(ex, this);
        } catch (UserRegistrationException ex) {
            exceptionHandler.handleUserRegistrationException(ex, this);
        } catch (Exception ex) {
            exceptionHandler.handleException(ex, this, "Error inesperado durante el registro de usuario");
        }
    }

    private void clearSensitiveFields() {
        passwordField.setText("");
        confirmPasswordField.setText("");
        if (messageLabel.getText().contains("usuario")) {
            usernameField.setText("");
        }
        if (messageLabel.getText().contains("email")) {
            emailField.setText("");
        }
    }
}
