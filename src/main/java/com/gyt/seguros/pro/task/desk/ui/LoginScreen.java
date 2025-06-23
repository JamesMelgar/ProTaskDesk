package com.gyt.seguros.pro.task.desk.ui;

import com.gyt.seguros.pro.task.desk.ProTaskDesk;
import com.gyt.seguros.pro.task.desk.dto.UserLoginRequest;
import com.gyt.seguros.pro.task.desk.model.User;
import com.gyt.seguros.pro.task.desk.service.LoginSvc;
import com.gyt.seguros.pro.task.desk.util.AppConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.WindowConstants;

public class LoginScreen extends JFrame {
    private JPanel loginPanel;
    private JLabel labelUser;
    private JTextField textFieldUser;
    private JLabel labelPassword;
    private JPasswordField passwordField;
    private JButton buttonLogin;
    private JButton buttonRegister;
    private JLabel messageLabel;

    private transient LoginSvc loginService;

    private static final String TITLE_PANEL = "Sistema Gestion de Tareas";
    private static final String HEADER = "Login Swing";

    private static final String MESSAGE_LOGIN_INVALID_CREDENTIALS = "Usuario o contraseña incorrectos.";
    private static final String MESSAGE_ERROR_REGISTER_SCREEN = "Error al abrir pantalla de registro: ";


    public LoginScreen() {
        this.loginService = ProTaskDesk.getBean(LoginSvc.class);

        initComponents();

        setContentPane(loginPanel);
        setTitle(TITLE_PANEL);
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setupEventHandlers();
        setVisible(true);
    }

    private void initComponents() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(
                AppConstants.PANEL_PADDING, AppConstants.PANEL_PADDING,
                AppConstants.PANEL_PADDING, AppConstants.PANEL_PADDING
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(
                AppConstants.GRIDBAG_INSETS_PADDING, AppConstants.GRIDBAG_INSETS_PADDING,
                AppConstants.GRIDBAG_INSETS_PADDING, AppConstants.GRIDBAG_INSETS_PADDING
        );

        JLabel titleLabel = new JLabel(HEADER);
        titleLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        loginPanel.add(new JLabel(""), gbc);

        labelUser = new JLabel("Username");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(labelUser, gbc);

        textFieldUser = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(textFieldUser, gbc);

        labelPassword = new JLabel("Password");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        loginPanel.add(labelPassword, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonLogin = new JButton("Login");
        buttonRegister = new JButton("Register");

        Color blueButtonColor = new Color(0, 120, 215);
        Color whiteTextColor = Color.WHITE;

        buttonLogin.setBackground(blueButtonColor);
        buttonLogin.setForeground(whiteTextColor);
        buttonLogin.setOpaque(true);
        buttonLogin.setBorderPainted(false);

        buttonRegister.setBackground(blueButtonColor);
        buttonRegister.setForeground(whiteTextColor);
        buttonRegister.setOpaque(true);
        buttonRegister.setBorderPainted(false);

        Dimension buttonSize = new Dimension(120, 35);
        buttonLogin.setPreferredSize(buttonSize);
        buttonRegister.setPreferredSize(buttonSize);

        buttonPanel.add(buttonLogin);
        buttonPanel.add(buttonRegister);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(buttonPanel, gbc);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(AppConstants.ERROR_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(messageLabel, gbc);
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
