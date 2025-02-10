package org.example.components;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Calendar extends JPanel {
    private LocalDate fechaActual;
    private CalendarPanel panelCalendarioQ;
    private JPanel fullContainer;
    private DailyPlan dailyPlan;

    public Calendar() {
        createDisplay();
    }


    private void createDisplay(){
        fullContainer = new JPanel(new GridLayout(1,2));

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        fechaActual = LocalDate.now();
        dailyPlan = new DailyPlan(fechaActual);
        panelCalendarioQ = new CalendarPanel(fullContainer,dailyPlan);

        fullContainer.add(panelCalendarioQ, BorderLayout.CENTER);
        fullContainer.add(dailyPlan);
        add(new JLabel("HOLA"));
        add(fullContainer);
        panelCalendarioQ.actualizarCalendario();
    }

}
