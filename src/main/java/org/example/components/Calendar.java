package org.example.components;

import org.example.Constants;
import org.example.Utils;
import org.example.models.Cita;
import org.example.models.Medico;
import org.example.service.ServiceCita;
import org.example.service.ServiceMedico;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
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
    private Cita cita;
    private JFrame window;

    public Calendar(Session session, DisplayLayout fullDisplay) {
        serviceCita = new ServiceCita(session);
        serviceMedico = new ServiceMedico(session);
        fechaActual = LocalDate.now().plusDays(1);
        parentDisplay = fullDisplay;
        instanceComponents();
        createDisplay();
    }
    public Calendar(Session session, Cita cita) {
        serviceCita = new ServiceCita(session);
        serviceMedico = new ServiceMedico(session);
        fechaActual = LocalDate.now().plusDays(1);
        this.cita = cita;
        window = new JFrame("Reprogramar cita");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        instanceReprogramarCita();
        createReprogramarDisplay();

        window.setSize(1095,350);
        int x = (Constants.SCREEN_SIZE.width - window.getWidth()) / 2;
        int y = (Constants.SCREEN_SIZE.height - window.getHeight()) / 2;
        window.setLocation(x,y);
        window.add(fullContainer);

        window.setVisible(true);

    }

    public DisplayLayout getParentDisplay() {
        return parentDisplay;
    }

    private void instanceReprogramarCita(){
        fullContainer = new JPanel(new GridLayout(1,2));
        dailyPlan = new DailyPlan(fechaActual, serviceCita, cita);

//        dailyPlan = new DailyPlan(serviceCita.getSession(), cita.getMedico(), parentDisplay);
        panelCalendarioQ = new CalendarPanel(fullContainer,dailyPlan, serviceCita.getSession(),cita);
    }

    private void instanceComponents(){
        fullContainer = new JPanel(new GridLayout(1,2));
        medicoField = new JTextField();
        medicosComponent = new Medicos(serviceCita.getSession(),medicoField);
        dailyPlan = new DailyPlan(fechaActual, serviceCita, serviceMedico.getMedicoById(1), parentDisplay);
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

    private void createReprogramarDisplay(){
//        setLayout(new CardLayout());
        JPanel calendarMedicoContainer = new JPanel(new BorderLayout());
        JPanel medicoSelectorContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));

        calendarMedicoContainer.add(medicoSelectorContainer, BorderLayout.NORTH);
        calendarMedicoContainer.add(panelCalendarioQ, BorderLayout.CENTER);
        fullContainer.add(calendarMedicoContainer, BorderLayout.CENTER);
        fullContainer.add(dailyPlan);
        add(fullContainer);

        panelCalendarioQ.actualizarCalendario();
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
