package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class Constants {
    public static final int MAX_DATES_X_DAY = 22;
    public static final int WEEK_DAYS = 7;
    public static final String PDF_PATH = "src/main/resources/pdf/";
    public static final File PDF_FOLDER = new File(PDF_PATH);

    public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";

    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static final Font APP_FONT = new Font("Verdana", Font.BOLD, 25);

    public static final String ROUTE_CUSTOM_FONT = "src/main/resources/fonts/Lato/Lato-Regular.ttf";
    public static final Font APP_FONT_CUSTOM;

    static {
        Font fuente;
        try {
            fuente = Font.createFont(Font.PLAIN, new File(ROUTE_CUSTOM_FONT));
            fuente.deriveFont(25f);
        } catch (FontFormatException | IOException e) {
            fuente = APP_FONT;
        }
        APP_FONT_CUSTOM = fuente;
    }


    // Calcular la posición para centrar el diálogo


    public static class Colors {
        public static final Color BUTTON_BACKGROUND = new Color(0X62b6cb);
        public static final Color BUTTON_ASIDE_BACKGROUND = new Color(0Xd1dfed);
        public static final Color PANEL_BACKGROUND = new Color(0Xbde9e7);
        public static final Color HOVER_BUTTON = new Color(0X5ea8d3);
        public static final Color ALTERNATE_TABLE = new Color(194,208,211);
        public static final Color BODY_BACKGROUND = new Color(0xc6edeb);
        public static final Color ASIDE_BACKGROUND = new Color(28,73,100);
        public static final Color BORDER_DIVIDER = new Color(0X81a7da);

//        public static final Color ASIDE_BACKGROUND = new Color(0xd1dfed);


    }

}
