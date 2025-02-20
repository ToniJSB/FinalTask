package org.example.components;

import org.example.Utils;
import org.example.models.Medico;
import org.example.service.ServiceCita;
import org.example.service.ServiceMedico;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;

public class Calendar extends JPanel {
    private LocalDate fechaActual;
    private CalendarPanel panelCalendarioQ;
    private DisplayLayout parentDisplay;
    private JPanel fullContainer;
    private DailyPlan dailyPlan;
    private JTextField medicoField;
    private Medicos medicosComponent;
    private ServiceCita serviceCita;
    private ServiceMedico serviceMedico;

    public Calendar(Session session, DisplayLayout fullDisplay) {
        serviceCita = new ServiceCita(session);
        serviceMedico = new ServiceMedico(session);
        fechaActual = LocalDate.now();
        parentDisplay = fullDisplay;
        instanceComponents();
        createDisplay();
    }

    public DisplayLayout getParentDisplay() {
        return parentDisplay;
    }

    private void instanceComponents(){
        fullContainer = new JPanel(new GridLayout(1,2));
        medicoField = new JTextField();
        medicosComponent = new Medicos(serviceCita.getSession(),medicoField);
        dailyPlan = new DailyPlan(fechaActual, serviceMedico, serviceCita, serviceMedico.getMedicoById(1), parentDisplay);
        panelCalendarioQ = new CalendarPanel(fullContainer,dailyPlan, serviceCita.getSession(), medicoField, parentDisplay );
        medicoField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                dailyPlan.setMedicoOfDay(serviceMedico.getMedicoById(Integer.parseInt(medicoField.getText())));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                dailyPlan.setMedicoOfDay(serviceMedico.getMedicoById(Integer.parseInt(medicoField.getText())));
            }
        });

        medicoField.setVisible(false);

    }

    private void createDisplay(){
        setLayout(new CardLayout());
        JPanel contenedor = this;
        JPanel calendarMedicoContainer = new JPanel(new BorderLayout());
        JPanel medicoSelectorContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton openMedicoSelector = new JButton("Abrir selector de medico");
        medicoSelectorContainer.add(openMedicoSelector);
        medicoSelectorContainer.add(medicoField);
        calendarMedicoContainer.add(medicoSelectorContainer, BorderLayout.NORTH);
        calendarMedicoContainer.add(panelCalendarioQ, BorderLayout.CENTER);
        fullContainer.add(calendarMedicoContainer, BorderLayout.CENTER);
        fullContainer.add(dailyPlan);

        openMedicoSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout)contenedor.getLayout()).show(contenedor,"MEDICOS");
            }
        });
        add(medicosComponent, "MEDICOS");
        add(fullContainer, "CALENDAR");

        panelCalendarioQ.actualizarCalendario();
    }

}
