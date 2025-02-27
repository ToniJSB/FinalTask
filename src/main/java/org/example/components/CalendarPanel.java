package org.example.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.example.Constants;
import org.example.Utils;
import org.example.models.Cita;
import org.example.service.ServiceCita;
import org.example.service.ServiceMedico;
import org.hibernate.Session;

/**
 * Custom JPanel component for displaying a calendar.
 */
public class CalendarPanel extends JPanel {
    private JComboBox<String> comboMonth;

    private JComboBox<Integer> comboYears;

    private LocalDate fechaActual;
    private JFrame window;
    private JPanel panelCalendario;
    private JPanel calendarContainer;
    private JPanel parent;
    private DailyPlan dailyPlan;
    private JTextField medicoField;
    private JTextField bDateField;
    private ServiceCita serviceCita;
    private ServiceMedico serviceMedico;
    private DisplayLayout appDisplay;
    private Cita cita;

    /**
     * Constructor for CalendarPanel.
     * 
     * @param parent the parent panel.
     * @param dailyPlan the daily plan component.
     * @param session the Hibernate session.
     * @param medicoField the text field for the doctor's information.
     * @param displayLayout the display layout.
     */
    public CalendarPanel(JPanel parent, DailyPlan dailyPlan, Session session, JTextField medicoField, DisplayLayout displayLayout) {
        super();
        this.medicoField = medicoField;
        this.parent = parent;
        this.dailyPlan = dailyPlan;
        serviceCita = new ServiceCita(session);
        serviceMedico = new ServiceMedico(session);
        appDisplay = displayLayout;

        instanceComponents();
        setDisplayDaily();
    }

    /**
     * Constructor for CalendarPanel.
     * 
     * @param parent the parent panel.
     * @param dailyPlan the daily plan component.
     * @param session the Hibernate session.
     * @param cita the appointment information.
     */
    public CalendarPanel(JPanel parent,DailyPlan dailyPlan, Session session, Cita cita) {
        super();
        this.cita = cita;
        this.parent = parent;
        this.dailyPlan = dailyPlan;
        serviceCita = new ServiceCita(session);
        serviceMedico = new ServiceMedico(session);
        instanceComponents();
        setDisplayDaily();
    }

    /**
     * Constructor for CalendarPanel.
     * 
     * @param birtdayDateField the text field for the birthday date.
     */
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

    /**
     * Sets the visibility of the calendar window.
     * 
     * @param isVisible true to make the window visible, false to hide it.
     */
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

    /**
     * Sets the display of the calendar. used to select a birthday date or update the appointment date.
     */
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

    /**
     * Sets the display of the calendar. used to select a date for a appointment using .
     */
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
        panelCalendario.setBackground(Constants.Colors.ASIDE_BACKGROUND.brighter().brighter());
        calendarContainer.add(panelCalendario, BorderLayout.CENTER);
        add(calendarContainer);
        actualizarCalendario();
    }

    /**
     * Updates the calendar display based on the selected month and year.
     */
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
            boton.setEnabled(false); // Deshabilitar botones de días no pertenecientes al mes actual
            panelCalendario.add(boton);
            fechaMesAnterior = fechaMesAnterior.plusDays(1);
        }

        // Llenar los días del mes actual
        LocalDate fechaActual = primerDiaMes;
        while (fechaActual.isBefore(ultimoDiaMes.plusDays(1))) {
            JButton boton = new JButton(String.valueOf(fechaActual.getDayOfMonth()));
            boton.setMargin(new Insets(5,5,5,5));
            if (fechaActual.equals(LocalDate.now().plusDays(1))) {
                boton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            LocalDate finalFechaActual = fechaActual;
            if(dailyPlan == null){
                boton.addActionListener(e -> getBDate(finalFechaActual));
            } else if (cita != null) {
                if (fechaActual.isBefore(LocalDate.now().plusDays(1))) {
                    boton.setForeground(Color.DARK_GRAY); // Días del mes anterior en gris
                    boton.setEnabled(false); // Deshabilitar botones de días no pertenecientes al mes actual
                }
                boton.addActionListener((ActionEvent e) -> {
                    showReprogramaDailyPlan(finalFechaActual);
                });

            } else {
                if (fechaActual.isBefore(LocalDate.now().plusDays(1))) {
                    boton.setForeground(Color.DARK_GRAY); // Días del mes anterior en gris
                    boton.setEnabled(false); // Deshabilitar botones de días no pertenecientes al mes actual
                }
                boton.addActionListener((ActionEvent e) -> {
                    //                        boton.setBackground(Color.GREEN);
                    showDailyPlan(finalFechaActual);
                });
            }

            panelCalendario.add(boton);
            fechaActual = fechaActual.plusDays(1);
        }

        // Llenar los días del mes siguiente que aparecen en la última semana
        int celdasRestantes = 42 - (diasMesAnterior + yearMonthActual.lengthOfMonth()); // 42 celdas en total (6 semanas)
        LocalDate fechaMesSiguiente = ultimoDiaMes.plusDays(1);

        for (int i = 0; i < celdasRestantes; i++) {
            JButton boton = new JButton(String.valueOf(fechaMesSiguiente.getDayOfMonth()));
            boton.setForeground(Color.DARK_GRAY); // Días del mes siguiente en gris
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

    /**
     * Shows the daily plan for a specific date to update the appointment selected prevoiusly.
     * 
     * @param fecha the date for which the daily plan is to be displayed.
     */
    private void showReprogramaDailyPlan(LocalDate fecha) {
        parent.remove(dailyPlan);
        dailyPlan = new DailyPlan(fecha, serviceCita,cita);
        parent.add(dailyPlan);

        revalidate();
        repaint();
    }

    /**
     * Shows the daily plan for a specific date to create an appointment.
     * 
     * @param fecha the date for which the daily plan is to be displayed.
     */
    private void showDailyPlan(LocalDate fecha) {
        parent.remove(dailyPlan);
        dailyPlan = new DailyPlan(fecha, serviceCita,serviceMedico.getMedicoById(Integer.parseInt(medicoField.getText())),appDisplay);
        parent.add(dailyPlan);

        revalidate();
        repaint();
    }

}
