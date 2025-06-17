package com.gyt.seguros.pro.task.desk.ui;

import com.gyt.seguros.pro.task.desk.ProTaskDesk;
import com.gyt.seguros.pro.task.desk.service.RegisterSvc;
import com.gyt.seguros.pro.task.desk.dto.UserRegistrationRequest;
import com.gyt.seguros.pro.task.desk.util.AppConstants;

import javax.swing.*;
import java.awt.*;

import javax.swing.WindowConstants;

public class RegisterScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel messageLabel;

    private final transient RegisterSvc registerService;

    private static final String TITLE_PANEL = "Sistema de Tareas - Registro de Usuario";
    private static final String TITLE_BUTTON_REGISTER = "Registrar";
    private static final String TITLE_BUTTON_CANCEL = "Cancelar";
    private static final String HEADER_LABEL_TEXT = "Crear Nueva Cuenta";
    private static final String LABEL_FULL_NAME = "Nombre Completo:";
    private static final String LABEL_USERNAME = "Usuario:";
    private static final String LABEL_PASSWORD = "Contraseña:";
    private static final String LABEL_CONFIRM_PASSWORD = "Confirmar Contraseña:";
    private static final String LABEL_EMAIL = "Email:";

    private static final String MESSAGE_REGISTRATION_SUCCESS = "¡Registro exitoso! Ya puedes iniciar sesión.";
    private static final String MESSAGE_REGISTRATION_SUCCESS_DIALOG_TITLE = "Registro Exitoso";
    private static final String MESSAGE_REGISTRATION_GENERIC_ERROR = "Error al registrar. Inténtelo de nuevo o el usuario ya existe.";
    private static final String MESSAGE_PASSWORDS_MISMATCH = "Las contraseñas no coinciden.";
    private static final String MESSAGE_REGISTRATION_RUNTIME_ERROR = "Ocurrió un error inesperado durante el registro: ";
    private static final String MESSAGE_REGISTRATION_GENERAL_ERROR = "Ocurrió un error general. Contacte a soporte.";


    public RegisterScreen() {
        this.registerService = ProTaskDesk.getBean(RegisterSvc.class);

        initComponents();

        setTitle(TITLE_PANEL);
        setContentPane(createMainPanel());
        setSize(450, 600);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setupEventHandlers();
    }

    private void initComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        fullNameField = new JTextField(20);
        emailField = new JTextField(20);

        registerButton = new JButton(TITLE_BUTTON_REGISTER);
        cancelButton = new JButton(TITLE_BUTTON_CANCEL);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(AppConstants.ERROR_COLOR);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                AppConstants.PANEL_PADDING, AppConstants.PANEL_PADDING,
                AppConstants.PANEL_PADDING, AppConstants.PANEL_PADDING
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(
                AppConstants.GRIDBAG_INSETS_PADDING, AppConstants.GRIDBAG_INSETS_PADDING,
                AppConstants.GRIDBAG_INSETS_PADDING, AppConstants.GRIDBAG_INSETS_PADDING
        );
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel(HEADER_LABEL_TEXT);
        titleLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 22));
        titleLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(15, 10, 10, 10);
        mainPanel.add(new JLabel(""), gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel(LABEL_FULL_NAME), gbc);
        gbc.gridy++;
        mainPanel.add(fullNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel(LABEL_USERNAME), gbc);
        gbc.gridy++;
        mainPanel.add(usernameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel(LABEL_PASSWORD), gbc);
        gbc.gridy++;
        mainPanel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel(LABEL_CONFIRM_PASSWORD), gbc);
        gbc.gridy++;
        mainPanel.add(confirmPasswordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel(LABEL_EMAIL), gbc);
        gbc.gridy++;
        mainPanel.add(emailField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        messageLabel.setForeground(AppConstants.ERROR_COLOR); // Asegurarse que el color inicial es el de error.
        mainPanel.add(messageLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        Color blueButtonColor = new Color(0, 120, 215);
        Color whiteTextColor = Color.WHITE;
        Dimension buttonSize = new Dimension(100, 30);

        registerButton.setBackground(blueButtonColor);
        registerButton.setForeground(whiteTextColor);
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(buttonSize);

        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(whiteTextColor);
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setPreferredSize(buttonSize);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        return mainPanel;
    }

    private void setupEventHandlers() {
        registerButton.addActionListener(e -> handleRegistration());

        cancelButton.addActionListener(e -> dispose());
    }

    private void handleRegistration() {
        UserRegistrationRequest request = new UserRegistrationRequest(
                usernameField.getText().trim(),
                new String(passwordField.getPassword()),
                new String(confirmPasswordField.getPassword()),
                fullNameField.getText().trim(),
                emailField.getText().trim()
        );

        if (request.getUsername().isEmpty() || request.getPassword().isEmpty() ||
                request.getConfirmPassword().isEmpty() || request.getFullName().isEmpty() ||
                request.getEmail().isEmpty()) {
            messageLabel.setText(AppConstants.MESSAGE_ERROR_FIELDS_REQUIRED);
            messageLabel.setForeground(AppConstants.ERROR_COLOR);
            return;
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            messageLabel.setText(MESSAGE_PASSWORDS_MISMATCH);
            messageLabel.setForeground(AppConstants.ERROR_COLOR);
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }

        try {
            boolean registrationSuccess = registerService.registerUser(request);

            if (registrationSuccess) {
                messageLabel.setText(MESSAGE_REGISTRATION_SUCCESS);
                messageLabel.setForeground(AppConstants.SUCCESS_COLOR);
                JOptionPane.showMessageDialog(this, "Usuario '" + request.getUsername() + "' registrado con éxito.",
                        MESSAGE_REGISTRATION_SUCCESS_DIALOG_TITLE, JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                messageLabel.setText(MESSAGE_REGISTRATION_GENERIC_ERROR);
                messageLabel.setForeground(AppConstants.ERROR_COLOR);
            }
        } catch (IllegalArgumentException ex) {
            messageLabel.setText(ex.getMessage());
            messageLabel.setForeground(AppConstants.ERROR_COLOR);
            passwordField.setText("");
            confirmPasswordField.setText("");
        } catch (RuntimeException ex) {
            messageLabel.setText(MESSAGE_REGISTRATION_RUNTIME_ERROR + ex.getMessage());
            messageLabel.setForeground(AppConstants.ERROR_COLOR);
        } catch (Exception ex) {
            messageLabel.setText(MESSAGE_REGISTRATION_GENERAL_ERROR);
            messageLabel.setForeground(AppConstants.ERROR_COLOR);
        }
    }
}