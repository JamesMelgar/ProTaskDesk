package com.gyt.seguros.pro.task.desk.ui;

import com.gyt.seguros.pro.task.desk.model.User;
import com.gyt.seguros.pro.task.desk.ui.components.AppMenuBar; // Asegúrate de que esta importación sea correcta
import com.gyt.seguros.pro.task.desk.util.AppConstants;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomeScreen extends JFrame {

    private final User currentUser;
    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JLabel dateTimeLabel;
    private JPanel statsPanel;

    private static final String TITLE_PANEL = "ProTaskDesk - Home";
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color WELCOME_BACKGROUND = new Color(0, 120, 215);
    private static final Color CARD_BACKGROUND = Color.WHITE;

    public HomeScreen(User currentUser) {
        this.currentUser = currentUser;
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel welcomePanel = createWelcomePanel();

        JPanel contentPanel = createContentPanel();

        statsPanel = createStatsPanel();

        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(WELCOME_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        welcomeLabel = new JLabel("¡Bienvenido a ProTaskDesk!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("Hola, " + currentUser.getFullName(), SwingConstants.CENTER);
        userLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 18));
        userLabel.setForeground(Color.WHITE);

        dateTimeLabel = new JLabel(getCurrentDateTime(), SwingConstants.CENTER);
        dateTimeLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        dateTimeLabel.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        textPanel.setBackground(WELCOME_BACKGROUND);
        textPanel.add(welcomeLabel);
        textPanel.add(userLabel);
        textPanel.add(dateTimeLabel);

        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel introLabel = new JLabel(
                "<html><center><h2>Sistema de Gestión de Tareas y Proyectos</h2>" +
                        "<p>Organiza tu trabajo de manera eficiente y colaborativa.</p>" +
                        "<p>Utiliza el menú superior para navegar por las diferentes funciones.</p></center></html>"
        );
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        introLabel.setForeground(Color.DARK_GRAY);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(introLabel, gbc);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Resumen Rápido"
        ));

        panel.add(createStatsCard("Proyectos Activos", "0", new Color(52, 152, 219)));
        panel.add(createStatsCard("Tareas Pendientes", "0", new Color(155, 89, 182)));
        panel.add(createStatsCard("Tareas Completadas", "0", new Color(46, 204, 113)));

        return panel;
    }

    private JPanel createStatsCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        card.setPreferredSize(new Dimension(150, 80));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        titleLabel.setForeground(Color.DARK_GRAY);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 24));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private void setupFrame() {
        AppMenuBar menuBarBuilder = new AppMenuBar(currentUser, this); // Instancia de tu "builder" de menú
        setJMenuBar(menuBarBuilder.getJMenuBar()); // Obtiene el JMenuBar y lo establece en el JFrame

        setContentPane(mainPanel);
        setTitle(TITLE_PANEL);
        setSize(900, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Timer timer = new Timer(60000, e -> {
            if (dateTimeLabel != null) {
                dateTimeLabel.setText(getCurrentDateTime());
            }
        });
        timer.start();
    }

    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy - HH:mm");
        return now.format(formatter);
    }
}
