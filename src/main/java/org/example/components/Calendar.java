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
    private JComboBox<String> comboMonth;
    private JComboBox<Integer> comboYears;
    private LocalDate fechaActual;
    private JPanel panelCalendario;
    private CalendarPanel panelCalendarioQ;
    private JPanel fullContainer;
    private JPanel calendarContainer;
    private DailyPlan dailyPlan;

    public Calendar() {
        createDisplay();
    }


    private void createBoxYear(){
        comboYears = new JComboBox<>();
        for (int i = fechaActual.getYear() - 40; i <= fechaActual.getYear() + 1; i++) {
            comboYears.addItem(i);
        }
        comboYears.setSelectedItem(fechaActual.getYear());
    }

    private void createBoxMonths(){
        comboMonth = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            comboMonth.addItem(LocalDate.of(2023, i, 1).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        comboMonth.setSelectedIndex(fechaActual.getMonthValue() - 1);
    }

    private void createDisplay(){
        fullContainer = new JPanel(new GridLayout(1,2));
        calendarContainer = new JPanel(new BorderLayout());

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        fechaActual = LocalDate.now();

        dailyPlan = new DailyPlan(fechaActual);
        panelCalendarioQ = new CalendarPanel(fullContainer,dailyPlan);

//        calendarContainer.add(panelCalendario, BorderLayout.CENTER);
//        calendarContainer.add(panelCalendarioQ, BorderLayout.CENTER);
        fullContainer.add(panelCalendarioQ, BorderLayout.CENTER);
//        fullContainer.add(calendarContainer);
        fullContainer.add(dailyPlan);
        add(new JLabel("HOLA"));
        add(fullContainer);
        panelCalendarioQ.actualizarCalendario();
    }

}
