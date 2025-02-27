package org.example;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

public class OwnTheme extends FlatLightLaf {
    public OwnTheme() {
        UIManager.put("Button.background", Constants.Colors.BUTTON_BACKGROUND);
        UIManager.put("Panel.background", Constants.Colors.PANEL_BACKGROUND);
        UIManager.put("Button.hoverBackground", Constants.Colors.HOVER_BUTTON);
        UIManager.put("Table.alternateRowColor", Constants.Colors.ALTERNATE_TABLE);
        UIManager.put("Separator.Background", Constants.Colors.BORDER_DIVIDER);
        UIManager.put("Separator.stripeWidth", 20);

    }

    public static boolean setup(){
        return setup(new OwnTheme());
    }

    @Override
    public String getName() {
        return "OwnTheme";
    }
}
