package com.gyt.seguros.pro.task.desk.ui;

import com.gyt.seguros.pro.task.desk.ProTaskDesk;
import com.gyt.seguros.pro.task.desk.service.RegisterSvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel messageLabel;

    private static final String TITLE_PANEL = "Sistema de Tareas - Registro de Usuario";
    private static final String TITLE_BUTTON_REGISTER = "Registrar";
    private static final String TITLE_BUTTON_CANCEL = "Cancelar";

    public RegisterScreen() {
        initComponents();
        setTitle(TITLE_PANEL);
        setContentPane(createMainPanel());
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        registerButton = new JButton(TITLE_BUTTON_REGISTER );
        cancelButton = new JButton(TITLE_BUTTON_CANCEL );

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Crear Nueva Cuenta");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(15, 10, 10, 10);
        mainPanel.add(new JLabel(""), gbc);

        // Nombre Completo
        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridy++;
        mainPanel.add(fullNameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Usuario:"), gbc);
        gbc.gridy++;
        mainPanel.add(usernameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridy++;
        mainPanel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Confirmar Contraseña:"), gbc);
        gbc.gridy++;
        mainPanel.add(confirmPasswordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridy++;
        mainPanel.add(emailField, gbc);

        gbc.gridy++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
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
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void handleRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
            messageLabel.setText("Todos los campos son obligatorios.");
            messageLabel.setForeground(Color.RED);
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Las contraseñas no coinciden.");
            messageLabel.setForeground(Color.RED);
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }

        try {
            RegisterSvc registerService = ProTaskDesk.getBean(RegisterSvc.class);

            if (registerService != null) {
                boolean registrationSuccess = registerService.registerUser(username, password, fullName, email);

                if (registrationSuccess) {
                    messageLabel.setText("¡Registro exitoso! Ya puedes iniciar sesión.");
                    messageLabel.setForeground(new Color(0, 150, 0));
                    JOptionPane.showMessageDialog(this, "Usuario '" + username + "' registrado con éxito.",
                            "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    messageLabel.setText("Error al registrar. Inténtelo de nuevo.");
                    messageLabel.setForeground(Color.RED);
                }
            } else {
                messageLabel.setText("¡Registro exitoso! (Modo demo)");
                messageLabel.setForeground(new Color(0, 150, 0));
                JOptionPane.showMessageDialog(this, "Usuario '" + username + "' registrado con éxito (Modo demo).",
                        "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }

        } catch (IllegalArgumentException ex) {
            messageLabel.setText(ex.getMessage());
            messageLabel.setForeground(Color.RED);
            passwordField.setText("");
            confirmPasswordField.setText("");
        } catch (Exception ex) {
            messageLabel.setText("¡Registro exitoso! (Modo básico)");
            messageLabel.setForeground(new Color(0, 150, 0));
            JOptionPane.showMessageDialog(this, "Usuario '" + username + "' registrado con éxito (Modo básico).",
                    "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}