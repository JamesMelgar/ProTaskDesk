package com.gyt.seguros.pro.task.desk.ui.components;

import com.gyt.seguros.pro.task.desk.model.User;
import com.gyt.seguros.pro.task.desk.ui.LoginScreen;
import com.gyt.seguros.pro.task.desk.ui.HomeScreen;
import com.gyt.seguros.pro.task.desk.util.AppConstants;

import javax.swing.*;
import java.awt.*;

public class AppMenuBar {
    private final User currentUser;
    private final JFrame parentFrame;
    private JMenuBar menuBar;

    public AppMenuBar(User currentUser, JFrame parentFrame) {
        this.currentUser = currentUser;
        this.parentFrame = parentFrame;
        initializeMenuBar();
    }

    private void initializeMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 120, 215));

        JMenu fileMenu = createMenu("Archivo");

        JMenuItem homeItem = createMenuItem("Home");
        homeItem.addActionListener(e -> navigateToHome());
        fileMenu.add(homeItem);

        fileMenu.addSeparator();

        JMenuItem logoutItem = createMenuItem("Cerrar Sesión");
        logoutItem.addActionListener(e -> logout());
        fileMenu.add(logoutItem);

        JMenu projectMenu = createMenu("Proyectos");

        JMenuItem dashboardItem = createMenuItem("Dashboard");
        dashboardItem.addActionListener(e -> showMessage("Dashboard - Próximamente"));
        projectMenu.add(dashboardItem);

        projectMenu.addSeparator();

        JMenuItem createProjectItem = createMenuItem("Crear Proyecto");
        createProjectItem.addActionListener(e -> showMessage("Crear Proyecto - Próximamente"));
        projectMenu.add(createProjectItem);

        JMenuItem createTaskItem = createMenuItem("Crear Tarea");
        createTaskItem.addActionListener(e -> showMessage("Crear Tarea - Próximamente"));
        projectMenu.add(createTaskItem);

        menuBar.add(fileMenu);
        menuBar.add(projectMenu);

        menuBar.add(Box.createHorizontalGlue());

        JLabel userLabel = new JLabel("Bienvenido, " + currentUser.getFullName());
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 12));
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        menuBar.add(userLabel);
    }

    public JMenuBar getJMenuBar() {
        return this.menuBar;
    }

    private JMenu createMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 12));
        return menu;
    }

    private JMenuItem createMenuItem(String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 11));
        return menuItem;
    }

    private void navigateToHome() {
        SwingUtilities.invokeLater(() -> {
            try {
                HomeScreen homeScreen = new HomeScreen(currentUser);
                homeScreen.setVisible(true);
                parentFrame.dispose();
            } catch (Exception ex) {
                showError("Error al navegar a Home: " + ex.getMessage());
            }
        });
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                parentFrame,
                "¿Está seguro que desea cerrar sesión?",
                "Confirmar Cierre de Sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                try {
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.setVisible(true);
                    parentFrame.dispose();
                } catch (Exception ex) {
                    showError("Error al cerrar sesión: " + ex.getMessage());
                }
            });
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(
                parentFrame,
                message,
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                parentFrame,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
