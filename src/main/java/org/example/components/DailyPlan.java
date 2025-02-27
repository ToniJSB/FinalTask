package org.example.components;

import org.example.Constants;
import org.example.Utils;
import org.example.models.Cita;
import org.example.models.EstadoCita;
import org.example.models.Medico;
import org.example.models.TipoCita;
import org.example.service.ServiceCita;
import org.example.service.ServiceMedico;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.*;

/**
 * The DailyPlan class represents a panel for displaying the daily plan of appointments.
 */
public class DailyPlan extends JPanel {
    private LocalDate dayDate;
    private DisplayLayout fullAppDisplay;
    private List<Cita> citasByDayDate;
    private ServiceCita serviceCita;
    private ServiceMedico serviceMedico;
    private Medico medicoOfDay;
    private Cita cita;
    private JPanel container;

    /**
     * Constructs a DailyPlan panel with the specified day, service, doctor, and display layout.
     *
     * @param day the day date
     * @param serviceCita the service for managing appointments
     * @param medico the doctor
     * @param appDisplay the display layout
     */
    public DailyPlan(LocalDate day,ServiceCita serviceCita, Medico medico, DisplayLayout appDisplay) {
        setLayout(new BorderLayout());// Usar BorderLayout para expandir el scroll
        this.dayDate = day;
        this.serviceCita = serviceCita;
        fullAppDisplay = appDisplay;
        medicoOfDay = medico;
        citasByDayDate = serviceCita.askCitasByDayWithMedicoAllowed(Utils.DateFormat.asDate(dayDate), medicoOfDay);
        setDisplay();
    }

    /**
     * Constructs a DailyPlan panel with the specified day, service, and appointment.
     *
     * @param day the day date
     * @param serviceCita the service for managing appointments
     * @param cita the appointment
     */
    public DailyPlan(LocalDate day,ServiceCita serviceCita, Cita cita) {
        setLayout(new BorderLayout());// Usar BorderLayout para expandir el scroll
        this.dayDate = day;
        this.serviceCita = serviceCita;
        this.cita = cita;
        medicoOfDay = cita.getMedico();
        citasByDayDate = serviceCita.askCitasByDayWithMedicoAllowed(Utils.DateFormat.asDate(dayDate), medicoOfDay);
        setDisplay();
    }

    /**
     * Sets the doctor of the day.
     *
     * @param medicoOfDay the doctor of the day
     */
    public void setMedicoOfDay(Medico medicoOfDay) {
        this.medicoOfDay = medicoOfDay;
        citasByDayDate = serviceCita.askCitasByDayWithMedicoAllowed(Utils.DateFormat.asDate(dayDate), medicoOfDay);
        revalidate();
        repaint();
    }

    /**
     * Sets the display of the DailyPlan panel.
     */
    private void setDisplay() {
        // Panel que contendrá el título y el scroll
        container = new JPanel(new BorderLayout());
        String fecha = Utils.DateFormat.dateAsStringDateF(Utils.DateFormat.asDate(dayDate));
        container.setBorder(
                BorderFactory.createTitledBorder(
                        "%s - %s".formatted(fecha, medicoOfDay.simpleInfo()))); //tamal

        JPanel gridDay = new JPanel();
        gridDay.setLayout(new BoxLayout(gridDay, BoxLayout.Y_AXIS));

        DiaryManager diaryManager = new DiaryManager();
        for (LocalTime interval : diaryManager.generateAllTimeSlots()){
            JPanel cell = new JPanel();
            // Filtrar citas en el mismo intervalo temporal

            List<Cita> citasEnIntervalo = new ArrayList<>();
            for (Cita cita : citasByDayDate){
                if (cita.getHoraCita().equals(interval)){
                    citasEnIntervalo.add(cita);
                }
            }
            Instant instant = interval.atDate(dayDate).
                    atZone(ZoneId.systemDefault()).toInstant();
            Date dateCita = Date.from(instant);

            if(citasEnIntervalo.isEmpty()){
                JButton buttonSetterCita = new JButton("CITA LIBRE: " + interval);
                if (cita != null){

                    buttonSetterCita.addActionListener(setActionBtnCita(dateCita,interval));
                }else {
                    buttonSetterCita.addActionListener(setActionBtnCita(dateCita,interval));
                }
                cell.add(buttonSetterCita);
                cell.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda
                gridDay.add(cell);
                gridDay.add(Box.createVerticalStrut(10)); // Espacio vertical entre celdas
            }else{
                JButton buttonSetterCita = new JButton("CITA OCUPADA");
                cell.add(buttonSetterCita);
                cell.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda
                gridDay.add(cell);
                gridDay.add(Box.createVerticalStrut(10)); // Espacio vertical entre celdas
            }
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

    /**
     * Sets the action for the appointment button.
     *
     * @param date the appointment date
     * @param time the appointment time
     * @return the ActionListener for the button
     */
    private ActionListener setActionBtnCita(Date date, LocalTime time){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog noti = new JDialog();
                JLabel label = new JLabel();
                if (date.after(Utils.DateFormat.asDate(LocalDate.now().plusDays(1)))){
                    Cita exist = serviceCita.askCitaByDateTimeMedico(date,time, medicoOfDay);
                    if (exist == null){
                        label.setText("Cita concedida.");
                        if (cita !=null) {
                            cita.setEstado(EstadoCita.PROGRAMADA);
                            cita.setFechaCita(date);
                            cita.setHoraCita(time);
                            serviceCita.updateCita(cita);
                            JFrame window = Utils.obtenerFrameDesdeComponente(container);
                            window.dispose();
                        }else{
                            serviceCita.createCita(date,medicoOfDay,time, TipoCita.PRESENCIAL);
                            fullAppDisplay.getBody().repaint();
                            fullAppDisplay.getBody().revalidate();
                        }
//                        ((CardLayout)fullAppDisplay.getBody().getLayout()).show(fullAppDisplay.getBody(),"PERFIL");
                    }else {
                        label.setText("Cita inaccesible");

                    }
                }else {
                    label.setText("Cita inaccesible");
                }
                noti.add(label);
                noti.setVisible(true);
                noti.setSize(150,150);
                int x = (Constants.SCREEN_SIZE.width - noti.getWidth()) / 2;
                int y = (Constants.SCREEN_SIZE.height - noti.getHeight()) / 2;

                // Establecer la posición del diálogo
                noti.setLocation(x, y);
                citasByDayDate = serviceCita.askCitasByDayWithMedicoAllowed(date, medicoOfDay);
                remove(container);
                setDisplay();
            }
        };
    }

}