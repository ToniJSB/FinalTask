package org.example.components;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.example.Constants;
import org.example.models.Cita;
import org.example.service.ServiceCita;
import org.example.service.ServiceMedico;
import org.hibernate.Session;


/**
 * The Calendar class represents a calendar component that displays daily plans and allows for reprogramming appointments.
 */
public class Calendar extends JPanel {
    private final LocalDate fechaActual;
    private CalendarPanel panelCalendarioQ;
    private DisplayLayout parentDisplay;
    private JPanel fullContainer;
    private DailyPlan dailyPlan;
    private JTextField medicoField;
    private Medicos medicosComponent;
    private final ServiceCita serviceCita;
    private final ServiceMedico serviceMedico;
    private Cita cita;
    private JFrame window;

    /**
     * Constructs a Calendar object with the specified session and display layout.
     *
     * @param session the Hibernate session
     * @param fullDisplay the display layout
     */
    public Calendar(Session session, DisplayLayout fullDisplay) {
        serviceCita = new ServiceCita(session);
        serviceMedico = new ServiceMedico(session);
        fechaActual = LocalDate.now().plusDays(1);
        parentDisplay = fullDisplay;
        instanceComponents();
        createDisplay();
    }

    /**
     * Constructs a Calendar object with the specified session and appointment.
     * This constructor is used for reprogramming an appointment.
     *
     * @param session the Hibernate session
     * @param cita the appointment to be reprogrammed
     */
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

    /**
     * Returns the parent display layout.
     *
     * @return the parent display layout
     */
    public DisplayLayout getParentDisplay() {
        return parentDisplay;
    }

    /**
     * Initializes the components for reprogramming an appointment.
     */
    private void instanceReprogramarCita(){
        fullContainer = new JPanel(new GridLayout(1,2));
        dailyPlan = new DailyPlan(fechaActual, serviceCita, cita);

//        dailyPlan = new DailyPlan(serviceCita.getSession(), cita.getMedico(), parentDisplay);
        panelCalendarioQ = new CalendarPanel(fullContainer,dailyPlan, serviceCita.getSession(),cita);
    }

    /**
     * Initializes the components for the calendar.
     */
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

    /**
     * Creates the display layout for reprogramming an appointment.
     */
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

    /**
     * Creates the display layout for the calendar.
     */
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

        openMedicoSelector.addActionListener((ActionEvent e) -> {
            ((CardLayout)contenedor.getLayout()).show(contenedor,"MEDICOS");
        });
        add(medicosComponent, "MEDICOS");
        add(fullContainer, "CALENDAR");

        panelCalendarioQ.actualizarCalendario();
    }

}
