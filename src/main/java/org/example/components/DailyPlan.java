package org.example.components;

import org.example.Constants;
import org.example.Utils;
import org.example.models.Cita;
import org.example.models.Medico;
import org.example.service.ServiceCita;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class DailyPlan extends JPanel {
    private final int maxDateByDay = Constants.MAX_DATES_X_DAY;
    private Date dayDate;
    private List<Cita> citasByDayDate;
    private ServiceCita serviceCita;
    private Medico medicoOfDay;

    public DailyPlan(Date day, List<Cita> citas, ServiceCita serviceCita) {
        super(new BorderLayout()); // Usar BorderLayout para expandir el scroll
        this.dayDate = day;
        citasByDayDate = citas;
        this.serviceCita = serviceCita;

        setDisplay();
    }

    public void setMedicoOfDay(Medico medicoOfDay) {
        this.medicoOfDay = medicoOfDay;
    }

    private void setDisplay() {
        // Panel que contendrá el título y el scroll
        JPanel container = new JPanel(new BorderLayout());
        String dateFormat = String.format("EEEE - %s",Constants.SIMPLE_DATE_FORMAT);
        container.setBorder(
                BorderFactory.createTitledBorder(
                        Utils.DateFormat.asLocalDate(dayDate).format(Utils.getDayFormatter(dateFormat))));

        // Panel interno con celdas (usará BoxLayout para mejor manejo de tamaño)
        JPanel gridDay = new JPanel();
        gridDay.setLayout(new BoxLayout(gridDay, BoxLayout.Y_AXIS));

        // Considerando que empiezan a tomar cita a partir de las 9, con una duración aproximada de 30 minutos
        boolean odd = true;
        Date initHour = new Date();
        Date intervalo = new Date();
        intervalo.setMinutes(30);
        initHour.setHours(9);
        List<Date> horasCitas =  citasByDayDate.stream().map(Cita::getFechaCita).collect(Collectors.toList());
        DiaryManager diaryManager = new DiaryManager();
        for (LocalTime interval : diaryManager.generateAllTimeSlots()){
//        for (int i = 0; i < maxDateByDay; i++) {
            JPanel cell = new JPanel();
            List<Integer> filteredDate = horasCitas.stream().map(Date::getHours).filter(e-> e.intValue() == initHour.getHours()).toList();
            if(filteredDate.isEmpty()){

                JButton buttonSetterCita = new JButton("CITA LIBRE: " + interval);
                Instant instant = interval.atDate(Utils.DateFormat.asLocalDate(dayDate)).
                        atZone(ZoneId.systemDefault()).toInstant();
                Date time = Date.from(instant);
                buttonSetterCita.addActionListener(setActionBtnCita(time));
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
            initHour.after(intervalo);

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

    private ActionListener setActionBtnCita(Date time){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                dayDate.setTime(time.);
                 serviceCita.createCita(time,medicoOfDay);
                 setDisplay();
            }
        };
    }

//    private ActionListener setCita(){
//    }
}