package com.gyt.seguros.pro.task.desk.ui;

import com.gyt.seguros.pro.task.desk.ProTaskDesk;
import com.gyt.seguros.pro.task.desk.model.Project;
import com.gyt.seguros.pro.task.desk.model.ProjectType;
import com.gyt.seguros.pro.task.desk.model.User;
import com.gyt.seguros.pro.task.desk.service.CreateProjectSvc;
import com.gyt.seguros.pro.task.desk.ui.components.AppMenuBar;
import com.gyt.seguros.pro.task.desk.util.AppConstants;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CreateProjectScreen extends JFrame {

    private CreateProjectSvc createProjectSvc;

    private final User currentUser;
    private JPanel mainPanel;
    private JTextField projectNameField;
    private JTextArea descriptionArea;
    private JComboBox<ProjectType> projectTypeComboBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton saveButton;
    private JButton cancelButton;

    private static final String TITLE_PANEL = "ProTaskDesk - Crear Proyecto";
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color HEADER_BACKGROUND = new Color(0, 120, 215);
    private static final Color FORM_BACKGROUND = Color.WHITE;
    private static final Color BUTTON_BACKGROUND = new Color(52, 152, 219);
    private static final Color CANCEL_BUTTON_BACKGROUND = new Color(231, 76, 60);

    public CreateProjectScreen(User currentUser) {
        this.currentUser = currentUser;
        this.createProjectSvc = ProTaskDesk.getBean(CreateProjectSvc.class);
        initComponents();
        setupFrame();
        loadProjectTypes();
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel headerPanel = createHeaderPanel();
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HEADER_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Crear Nuevo Proyecto", SwingConstants.CENTER);
        titleLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Complete la información del proyecto", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setBackground(HEADER_BACKGROUND);
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);

        panel.add(textPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(FORM_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createLabel("Nombre del Proyecto:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        projectNameField = new JTextField(20);
        projectNameField.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        panel.add(projectNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(createLabel("Tipo de Proyecto:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        projectTypeComboBox = new JComboBox<>();
        projectTypeComboBox.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        projectTypeComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProjectType) {
                    setText(((ProjectType) value).getTypeName());
                }
                return component;
            }
        });
        panel.add(projectTypeComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(createLabel("Descripción:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Fecha de Inicio (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        startDateField = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        startDateField.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        panel.add(startDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(createLabel("Fecha de Fin (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        endDateField = new JTextField(LocalDate.now().plusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE));
        endDateField.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        panel.add(endDateField, gbc);

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(BACKGROUND_COLOR);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints wrapperGbc = new GridBagConstraints();
        wrapperGbc.fill = GridBagConstraints.BOTH;
        wrapperGbc.weightx = 1.0;
        wrapperGbc.weighty = 1.0;
        wrapperPanel.add(panel, wrapperGbc);

        return wrapperPanel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        saveButton = new JButton("Guardar Proyecto");
        saveButton.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 12));
        saveButton.setBackground(BUTTON_BACKGROUND);
        saveButton.setForeground(Color.WHITE);
        saveButton.setPreferredSize(new Dimension(150, 35));
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveProject());

        cancelButton = new JButton("Cancelar");
        cancelButton.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 12));
        cancelButton.setBackground(CANCEL_BUTTON_BACKGROUND);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(150, 35));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> cancel());

        panel.add(saveButton);
        panel.add(cancelButton);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(AppConstants.DEFAULT_FONT_NAME, Font.BOLD, 12));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

    private void setupFrame() {
        AppMenuBar menuBarBuilder = new AppMenuBar(currentUser, this);
        setJMenuBar(menuBarBuilder.getJMenuBar());

        setContentPane(mainPanel);
        setTitle(TITLE_PANEL);
        setSize(800, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void loadProjectTypes() {
        try {
            List<ProjectType> projectTypes = createProjectSvc.getAllProjectTypes();

            DefaultComboBoxModel<ProjectType> model = new DefaultComboBoxModel<>();
            for (ProjectType type : projectTypes) {
                model.addElement(type);
            }

            projectTypeComboBox.setModel(model);

            if (projectTypes.isEmpty()) {
                showError("No hay tipos de proyecto disponibles.");
                saveButton.setEnabled(false);
            }
        } catch (Exception ex) {
            showError("Error al cargar tipos de proyecto: " + ex.getMessage());
            saveButton.setEnabled(false);
        }
    }

    private void saveProject() {
        try {
            String projectName = projectNameField.getText().trim();
            if (projectName.isEmpty()) {
                showError("El nombre del proyecto es obligatorio.");
                projectNameField.requestFocus();
                return;
            }

            if (createProjectSvc.projectNameExists(projectName)) {
                showError("Ya existe un proyecto con el nombre: " + projectName);
                projectNameField.requestFocus();
                return;
            }

            String description = descriptionArea.getText().trim();
            if (description.isEmpty()) {
                description = null;
            }

            ProjectType selectedType = (ProjectType) projectTypeComboBox.getSelectedItem();
            if (selectedType == null) {
                showError("Debe seleccionar un tipo de proyecto.");
                projectTypeComboBox.requestFocus();
                return;
            }

            LocalDate startDate;
            LocalDate endDate;

            try {
                String startDateText = startDateField.getText().trim();
                if (startDateText.isEmpty()) {
                    showError("La fecha de inicio es obligatoria.");
                    startDateField.requestFocus();
                    return;
                }
                startDate = LocalDate.parse(startDateText);
            } catch (DateTimeParseException ex) {
                showError("Formato de fecha de inicio inválido. Use el formato YYYY-MM-DD.");
                startDateField.requestFocus();
                return;
            }

            try {
                String endDateText = endDateField.getText().trim();
                if (endDateText.isEmpty()) {
                    showError("La fecha de fin es obligatoria.");
                    endDateField.requestFocus();
                    return;
                }
                endDate = LocalDate.parse(endDateText);
            } catch (DateTimeParseException ex) {
                showError("Formato de fecha de fin inválido. Use el formato YYYY-MM-DD.");
                endDateField.requestFocus();
                return;
            }

            if (!createProjectSvc.validateProjectDates(startDate, endDate)) {
                showError("Las fechas no son válidas. Verifique que:\n" +
                        "- La fecha de inicio no sea anterior a hoy\n" +
                        "- La fecha de fin sea posterior o igual a la fecha de inicio");
                startDateField.requestFocus();
                return;
            }

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            saveButton.setEnabled(false);

            Project createdProject = createProjectSvc.createProject(
                    projectName,
                    description,
                    startDate,
                    endDate,
                    selectedType,
                    currentUser
            );

            if (createdProject != null) {
                showSuccess("Proyecto '" + createdProject.getProjectName() + "' creado exitosamente!");
                clearForm();
            } else {
                showError("Error al crear el proyecto. Inténtelo nuevamente.");
            }

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError("Error inesperado al crear el proyecto: " + ex.getMessage());
        } finally {
            setCursor(Cursor.getDefaultCursor());
            saveButton.setEnabled(true);
        }
    }

    private void cancel() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea cancelar? Se perderán los datos ingresados.",
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            navigateToHome();
        }
    }

    private void navigateToHome() {
        SwingUtilities.invokeLater(() -> {
            try {
                HomeScreen homeScreen = new HomeScreen(currentUser);
                homeScreen.setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                showError("Error al navegar a Home: " + ex.getMessage());
            }
        });
    }

    private void clearForm() {
        projectNameField.setText("");
        descriptionArea.setText("");
        if (projectTypeComboBox.getItemCount() > 0) {
            projectTypeComboBox.setSelectedIndex(0);
        }
        startDateField.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        endDateField.setText(LocalDate.now().plusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
