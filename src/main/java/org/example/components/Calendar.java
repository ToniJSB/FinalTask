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
        panelCalendario = new JPanel(new GridLayout(0, 7));
        panelCalendario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCalendario.setBackground(Color.CYAN);
        calendarContainer.add(panelCalendario, BorderLayout.CENTER);
        fullContainer.add(calendarContainer);
        dailyPlan = new DailyPlan(fechaActual);
        fullContainer.add(dailyPlan);
        add(new JLabel("HOLA"));
        add(fullContainer);
        actualizarCalendario();
    }


    private void actualizarCalendario() {
        panelCalendario.removeAll();

        // Llenar los días de la semana
        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : diasSemana) {
            JLabel label = new JLabel(dia, SwingConstants.CENTER);
            panelCalendario.add(label);
        }

        // Obtener el mes y año seleccionados
        int mesSeleccionado = comboMonth.getSelectedIndex() + 1;
        int anioSeleccionado = (int) comboYears.getSelectedItem();
        YearMonth añoMesActual = YearMonth.of(anioSeleccionado, mesSeleccionado);
        LocalDate primerDiaMes = añoMesActual.atDay(1);
        LocalDate ultimoDiaMes = añoMesActual.atEndOfMonth();

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

            boton.addActionListener(e -> mostrarMensaje(finalFechaActual));
            panelCalendario.add(boton);
            fechaActual = fechaActual.plusDays(1);
        }

        // Llenar los días del mes siguiente que aparecen en la última semana
        int celdasRestantes = 42 - (diasMesAnterior + añoMesActual.lengthOfMonth()); // 42 celdas en total (6 semanas)
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

    private void mostrarMensaje(LocalDate fecha) {
        fullContainer.remove(dailyPlan);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE");

//        JOptionPane.showMessageDialog(this, "Fecha seleccionada: " + fecha.toString());
        System.out.println(fecha.toString());
        System.out.println(fecha);
        dailyPlan = new DailyPlan(fecha);
        fullContainer.add(dailyPlan);

        revalidate();
        repaint();
    }

}
