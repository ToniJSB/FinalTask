package org.example.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class OwnBorder implements Border {
    private int thickness;

    public OwnBorder(int thickness) {
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawRoundRect(x, y, width - 1, height - 1, 20, 20); // Bordes redondeados
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
