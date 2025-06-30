package com.gyt.seguros.pro.task.desk.ui.screen;

import com.gyt.seguros.pro.task.desk.ProTaskDesk;
import com.gyt.seguros.pro.task.desk.svc.dto.UserLoginRequest;
import com.gyt.seguros.pro.task.desk.dal.model.User;
import com.gyt.seguros.pro.task.desk.svc.LoginSvc;
import com.gyt.seguros.pro.task.desk.util.AppConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.WindowConstants;

public class LoginScreen extends JFrame {
    private JPanel mainPanel;
    private JPanel loginFormPanel;
    private JLabel labelUser;
    private JTextField textFieldUser;
    private JLabel labelPassword;
    private JPasswordField passwordField;
    private JButton buttonLogin;
    private JButton buttonRegister;
    private JLabel messageLabel;

    private transient LoginSvc loginService;

    private static final String TITLE_PANEL = "ProTaskDesk - Sistema de Gestión de Tareas";
    private static final String HEADER = "¡Bienvenido a ProTaskDesk!";
    private static final String SUBTITLE = "Inicia sesión para continuar";

    private static final String MESSAGE_LOGIN_INVALID_CREDENTIALS = "Usuario o contraseña incorrectos.";
    private static final String MESSAGE_ERROR_REGISTER_SCREEN = "Error al abrir pantalla de registro: ";

    public LoginScreen() {
        this.loginService = ProTaskDesk.getBean(LoginSvc.class);
        initComponents();

        setContentPane(mainPanel);
        setTitle(TITLE_PANEL);
        setSize(600, 750);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(AppConstants.LIGHT_BLUE_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        loginFormPanel = createLoginForm();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        panel.add(loginFormPanel, gbc);
        return panel;
    }

    private JPanel createLoginForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(AppConstants.CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.PRIMARY_BLUE, 2),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel formTitle = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        formTitle.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 20));
        formTitle.setForeground(AppConstants.PRIMARY_BLUE);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(formTitle, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 15, 15, 15);
        panel.add(new JLabel(""), gbc);

        labelUser = new JLabel("Usuario:");
        labelUser.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 14));
        labelUser.setForeground(AppConstants.TEXT_DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 15, 5, 15);
        panel.add(labelUser, gbc);

        textFieldUser = new JTextField(20);
        textFieldUser.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        textFieldUser.setPreferredSize(new Dimension(280, 30));
        textFieldUser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.PRIMARY_BLUE),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 15, 15, 15);
        panel.add(textFieldUser, gbc);

        labelPassword = new JLabel("Contraseña:");
        labelPassword.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 14));
        labelPassword.setForeground(AppConstants.TEXT_DARK_GRAY);
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 15, 5, 15);
        panel.add(labelPassword, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(280, 30));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppConstants.PRIMARY_BLUE),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 15, 20, 15);
        panel.add(passwordField, gbc);

        buttonLogin = createStyledButton("Iniciar Sesión", AppConstants.PRIMARY_BLUE);
        buttonRegister = createStyledButton("Registrarse", AppConstants.ACCENT_BLUE);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(AppConstants.CARD_BACKGROUND);

        buttonPanel.add(buttonLogin);
        buttonPanel.add(buttonRegister);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 15, 15, 15);
        panel.add(buttonPanel, gbc);


        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 0, 15);
        panel.add(messageLabel, gbc);


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
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        buttonRegister.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    RegisterScreen registerScreen = new RegisterScreen();
                    registerScreen.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LoginScreen.this,
                            MESSAGE_ERROR_REGISTER_SCREEN + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        getRootPane().setDefaultButton(buttonLogin);
    }

    private void handleLogin() {
        String username = textFieldUser.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText(AppConstants.MESSAGE_ERROR_FIELDS_REQUIRED);
            messageLabel.setForeground(AppConstants.ERROR_COLOR);
            return;
        }

        UserLoginRequest loginRequest = new UserLoginRequest(username, password);

        try {
            Optional<User> authenticatedUser = loginService.authenticate(loginRequest);

            if (authenticatedUser.isPresent()) {
                User user = authenticatedUser.get();

                messageLabel.setText("¡Bienvenido, " + user.getFullName() + "!");
                messageLabel.setForeground(AppConstants.SUCCESS_COLOR);

                SwingUtilities.invokeLater(() -> {
                    try {
                        HomeScreen homeScreen = new HomeScreen(user);
                        homeScreen.setVisible(true);
                        this.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(LoginScreen.this,
                                "Error al abrir pantalla principal: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

            } else {
                messageLabel.setText(MESSAGE_LOGIN_INVALID_CREDENTIALS);
                messageLabel.setForeground(AppConstants.ERROR_COLOR);
                passwordField.setText("");
            }
        } catch (Exception ex) {
            messageLabel.setText(AppConstants.MESSAGE_ERROR_UNEXPECTED);
            messageLabel.setForeground(AppConstants.ERROR_COLOR);
        }
    }
}
