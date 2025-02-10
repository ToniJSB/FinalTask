package org.example.components;

import org.example.Constants;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailyPlan extends JPanel {
    private final int maxDateByDay = Constants.MAX_DATES_X_DAY;
    private LocalDate dayDate;

    public DailyPlan(LocalDate day) {
        super(new BorderLayout()); // Usar BorderLayout para expandir el scroll
        this.dayDate = day;
        setDisplay();
    }

    private DateTimeFormatter getDayFormatter(){
        return DateTimeFormatter.ofPattern("EEEE - dd/MM/yyyy");

    }

    private void setDisplay() {
        // Panel que contendrá el título y el scroll
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createTitledBorder(dayDate.format(getDayFormatter())));

        // Panel interno con celdas (usará BoxLayout para mejor manejo de tamaño)
        JPanel gridDay = new JPanel();
        gridDay.setLayout(new BoxLayout(gridDay, BoxLayout.Y_AXIS));

        // Agregar celdas al panel
        for (int i = 0; i < maxDateByDay; i++) {
            JPanel cell = new JPanel();
            cell.add(new JLabel("CITA LIBRE " + i));
            cell.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda
            gridDay.add(cell);
            gridDay.add(Box.createVerticalStrut(10)); // Espacio vertical entre celdas
        }

        // Envolver el panel gridDay en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(gridDay);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Añadir el scroll al contenedor con borde
        container.add(scrollPane, BorderLayout.CENTER);

        // Añadir todo al DailyPlan
        add(container, BorderLayout.CENTER);
    }
}