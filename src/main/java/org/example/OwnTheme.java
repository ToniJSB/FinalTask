package org.example;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class OwnTheme extends FlatLightLaf {
    public OwnTheme() {
        UIManager.put("Button.background", new Color(98, 182, 203));
        UIManager.put("Panel.background", new Color(189, 233, 231));
        UIManager.put("Button.hoverBackground", new Color(94,168,211));
        UIManager.put("Table.alternateRowColor", new Color(94,168,211));

    }

    public static boolean setup(){
        return setup(new OwnTheme());
    }

    @Override
    public String getName() {
        return "OwnTheme";
    }
}
