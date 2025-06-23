package com.gyt.seguros.pro.task.desk.util;

import java.awt.Color;

public final class AppConstants {

    private AppConstants() {
    }

    public static final Color SUCCESS_COLOR = new Color(0, 150, 0);
    public static final Color ERROR_COLOR = Color.RED;

    public static final Color PRIMARY_BLUE = new Color(0, 120, 215);          // Azul principal
    public static final Color LIGHT_BLUE_BACKGROUND = new Color(240, 248, 255); // Fondo azul claro
    public static final Color DARK_BLUE = new Color(25, 118, 210);            // Azul más oscuro
    public static final Color ACCENT_BLUE = new Color(52, 152, 219);          // Azul de acento
    public static final Color CARD_BACKGROUND = Color.WHITE;                   // Fondo de tarjetas
    public static final Color TEXT_DARK_GRAY = Color.DARK_GRAY;               // Texto gris oscuro

    public static final String MESSAGE_ERROR_FIELDS_REQUIRED = "Por favor, complete todos los campos.";
    public static final String MESSAGE_ERROR_UNEXPECTED = "Ocurrió un error inesperado. Contacte a soporte.";

    public static final String DEFAULT_FONT_NAME = "Arial";
    public static final int PANEL_PADDING = 20;
    public static final int GRIDBAG_INSETS_PADDING = 10;

    public static final int BUTTON_HEIGHT = 35;
    public static final int BUTTON_WIDTH = 120;
}
