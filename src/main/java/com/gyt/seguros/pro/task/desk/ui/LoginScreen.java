package com.gyt.seguros.pro.task.desk.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JPanel LoginPanel;
    private JLabel labelUser;
    private JTextField textFieldUser;
    private JLabel labelPassword;
    private JPasswordField passwordField;
    private JButton buttonLogin;
    private JButton buttonRegister;
    private JLabel messageLabel;

    private static final String TITLE_PANEL = "Sistema Gestion de Tareas";
    private static final String HEADER = "Login Swing";
    private static final String FONT_TITLE = "Arial";

    public LoginScreen() {
        initComponents();

        setContentPane(LoginPanel);
        setTitle(TITLE_PANEL);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setupEventHandlers();
        setVisible(true);
    }

    private void initComponents() {
        LoginPanel = new JPanel();
        LoginPanel.setLayout(new GridBagLayout());
        LoginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel(HEADER);
        titleLabel.setFont(new Font(FONT_TITLE, Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        LoginPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        LoginPanel.add(new JLabel(""), gbc);

        labelUser = new JLabel("Username");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        LoginPanel.add(labelUser, gbc);

        textFieldUser = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        LoginPanel.add(textFieldUser, gbc);

        labelPassword = new JLabel("Password");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        LoginPanel.add(labelPassword, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        LoginPanel.add(passwordField, gbc);

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
        LoginPanel.add(buttonPanel, gbc);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        LoginPanel.add(messageLabel, gbc);
    }

    private void setupEventHandlers() {
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textFieldUser.getText().trim();
                String password = new String(passwordField.getPassword());

                if (usuario.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Por favor, complete todos los campos.");
                    messageLabel.setForeground(Color.RED);
                    return;
                }

                if ("admin".equals(usuario) && "123456".equals(password)) {
                    messageLabel.setText("¡Bienvenido, " + usuario + "!");
                    messageLabel.setForeground(new Color(0, 150, 0));
                    JOptionPane.showMessageDialog(LoginScreen.this,
                            "¡Bienvenido, " + usuario + "!",
                            "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    messageLabel.setText("Usuario o contraseña incorrectos.");
                    messageLabel.setForeground(Color.RED);
                    passwordField.setText("");
                }
            }
        });

        buttonRegister.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    RegisterScreen registerScreen = new RegisterScreen();
                    registerScreen.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LoginScreen.this,
                            "Error al abrir pantalla de registro: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
        });

        getRootPane().setDefaultButton(buttonLogin);
    }
}
