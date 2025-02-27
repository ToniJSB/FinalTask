package org.example;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class OwnTheme extends FlatLightLaf {
    public OwnTheme() {
        UIManager.put("Button.background", Constants.Colors.BUTTON_BACKGROUND);
        UIManager.put("Panel.background", Constants.Colors.PANEL_BACKGROUND);
        UIManager.put("Button.hoverBackground", Constants.Colors.HOVER_BUTTON);
        UIManager.put("Table.alternateRowColor", Constants.Colors.ALTERNATE_TABLE);

    }

    public static boolean setup(){
        return setup(new OwnTheme());
    }

    @Override
    public String getName() {
        return "OwnTheme";
    }
}
