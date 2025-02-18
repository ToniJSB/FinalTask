package org.example.components;

import org.example.Constants;
import org.example.Utils;
import org.example.models.Medico;
import org.example.service.ServiceCita;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class CalendarPanel extends JPanel {
    private JComboBox<String> comboMonth;

    private JComboBox<Integer> comboYears;

    private LocalDate fechaActual;
    private JFrame window;
    private JPanel panelCalendario;
    private JPanel calendarContainer;
    private JPanel parent;
    private DailyPlan dailyPlan;
    private JTextField bDateField;
    private ServiceCita serviceCita;

    public CalendarPanel(JPanel parent, DailyPlan dailyPlan, Session session) {
        super();
        this.parent = parent;
        this.dailyPlan = dailyPlan;
        serviceCita = new ServiceCita(session);
        instanceComponents();
        setDisplayDaily();

    }

    public CalendarPanel(JTextField birtdayDateField) {
        super();
        window = new JFrame("Birtday Date");
        bDateField = birtdayDateField;
        instanceComponents();
        setDisplay();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(600,400);
        window.add(calendarContainer);
        setVisible(true);
    }
    public void setVisible(boolean isVisible){
        window.setVisible(isVisible);
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

    private void instanceComponents(){
        calendarContainer = new JPanel(new BorderLayout());
        panelCalendario = new JPanel(new GridLayout(0, 7));

    }

    private void setDisplay(){
        fechaActual = LocalDate.now();

        // Panel superior para seleccionar mes y año
        JPanel panelSuperior = new JPanel();
        createBoxMonths();
        panelSuperior.add(comboMonth);
        createBoxYear();
        panelSuperior.add(comboYears);

        JButton botonActualizar = new JButton("Cargar fecha");
        botonActualizar.addActionListener(e -> actualizarCalendario());
        panelSuperior.add(botonActualizar);

        calendarContainer.add(panelSuperior, BorderLayout.NORTH);

        // Panel del calendario
        panelCalendario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCalendario.setBackground(Color.CYAN);
        calendarContainer.add(panelCalendario, BorderLayout.CENTER);
        add(calendarContainer);
        actualizarCalendario();
    }

    private void setDisplayDaily(){
        fechaActual = LocalDate.now();

        // Panel superior para seleccionar mes y año
        JPanel panelSuperior = new JPanel();
        createBoxMonths();
        panelSuperior.add(comboMonth);
        createBoxYear();
        panelSuperior.add(comboYears);

        JButton botonActualizar = new JButton("Cargar fecha");
        botonActualizar.addActionListener(e -> actualizarCalendario());
        panelSuperior.add(botonActualizar);

        calendarContainer.add(panelSuperior, BorderLayout.NORTH);

        // Panel del calendario
        panelCalendario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCalendario.setBackground(Color.CYAN);
        calendarContainer.add(panelCalendario, BorderLayout.CENTER);
        add(calendarContainer);
        actualizarCalendario();
    }

    public void actualizarCalendario() {
        panelCalendario.removeAll();

        // Llenar los días de la semana
        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : diasSemana) {
            JLabel label = new JLabel(dia, SwingConstants.CENTER);
            panelCalendario.add(label);
        }

        // Obtener el mes y año seleccionados
        int monthSelected = comboMonth.getSelectedIndex() + 1;
        int yearSelected = (int) comboYears.getSelectedItem();
        YearMonth yearMonthActual = YearMonth.of(yearSelected, monthSelected);
        LocalDate primerDiaMes = yearMonthActual.atDay(1);
        LocalDate ultimoDiaMes = yearMonthActual.atEndOfMonth();

        // Llenar los días del mes anterior que aparecen en la primera semana
        DayOfWeek primerDiaSemana = primerDiaMes.getDayOfWeek();
        int diasMesAnterior = primerDiaSemana.getValue() - 1; // Ajuste para que Lunes sea 1
        LocalDate fechaMesAnterior = primerDiaMes.minusDays(diasMesAnterior);

        for (int i = 0; i < diasMesAnterior; i++) {
            JButton boton = new JButton(String.valueOf(fechaMesAnterior.getDayOfMonth()));
            boton.setForeground(Color.GRAY); // Días del mes anterior en gris
            boton.setEnabled(false); // Deshabilitar botones de días no pertenecientes al mes actual
            panelCalendario.add(boton);
            fechaMesAnterior = fechaMesAnterior.plusDays(1);
        }

        // Llenar los días del mes actual
        LocalDate fechaActual = primerDiaMes;
        while (fechaActual.isBefore(ultimoDiaMes.plusDays(1))) {
            JButton boton = new JButton(String.valueOf(fechaActual.getDayOfMonth()));
            if (fechaActual.equals(LocalDate.now())) {
                boton.setBackground(Color.YELLOW); // Resaltar el día actual
            }
            LocalDate finalFechaActual = fechaActual;
            if(dailyPlan == null){
                boton.addActionListener(e -> getBDate(finalFechaActual));
            }else {
                boton.addActionListener(e -> showDailyPlan(finalFechaActual));
            }

            panelCalendario.add(boton);
            fechaActual = fechaActual.plusDays(1);
        }

        // Llenar los días del mes siguiente que aparecen en la última semana
        int celdasRestantes = 42 - (diasMesAnterior + yearMonthActual.lengthOfMonth()); // 42 celdas en total (6 semanas)
        LocalDate fechaMesSiguiente = ultimoDiaMes.plusDays(1);

        for (int i = 0; i < celdasRestantes; i++) {
            JButton boton = new JButton(String.valueOf(fechaMesSiguiente.getDayOfMonth()));
            boton.setForeground(Color.GRAY); // Días del mes siguiente en gris
            boton.setEnabled(false); // Deshabilitar botones de días no pertenecientes al mes actual
            panelCalendario.add(boton);
            fechaMesSiguiente = fechaMesSiguiente.plusDays(1);
        }

        panelCalendario.revalidate();
        panelCalendario.repaint();
    }

    private void getBDate(LocalDate fecha) {

        bDateField.setText(fecha.format(Utils.getDayFormatter(Constants.SIMPLE_DATE_FORMAT)));
        setVisible(false);
        window.dispose();
    }
    private void showDailyPlan(LocalDate fecha) {
        parent.remove(dailyPlan);
        Date date = Utils.DateFormat.asDate(fecha);
        dailyPlan = new DailyPlan(date, serviceCita.askCitasByDay(Utils.DateFormat.asDate(fechaActual)),serviceCita);
        parent.add(dailyPlan);

        revalidate();
        repaint();
    }
}
