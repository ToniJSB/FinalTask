package org.example.components;

import org.example.models.Cita;
import org.example.service.ServiceCita;
import org.example.service.ServicePaciente;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class Profile extends JPanel {
    private ServicePaciente servicePaciente;
    private ServiceCita serviceCita;

    private String dni;
    private String name;
    private String surname1;
    private String surname2;
    private LocalDate birthdate;


    public Profile(Session session) {
        super();
        servicePaciente = new ServicePaciente(session);
        serviceCita = new ServiceCita(session);
        name = DisplayLayout.pacienteSession.getNombre();
        surname1 = DisplayLayout.pacienteSession.getApellido1();
        surname2 = DisplayLayout.pacienteSession.getApellido2();
        dni = DisplayLayout.pacienteSession.getDni();
        birthdate = DisplayLayout.pacienteSession.getbDate();
        setDisplay();


    }
    private void setDisplay(){
        JPanel container = new JPanel();
        JPanel infoContainer = new JPanel(new GridLayout(2,1));
        infoContainer.setBorder(BorderFactory.createTitledBorder("Información personal"));
        JPanel panelFullName = createPanelFullName();
        JPanel panelBirthdate = new JPanel();
        JLabel labelBirthdate = new JLabel(birthdate.toString());

        panelBirthdate.add(labelBirthdate);

        infoContainer.add(panelFullName);
        infoContainer.add(panelBirthdate);

        container.add(infoContainer);
        add(container);
        add(createCitasPanel());
    }

    private JPanel createPanelFullName(){
        JPanel panelFullName = new JPanel(new GridLayout(2,1));
        JPanel panelName = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelName = new JLabel("Nombre: "+name);

        JPanel panelSurname = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelSurname1 = new JLabel("Apellidos: %s %s".formatted(surname1,surname2));
//        JLabel labelSurname2 = new JLabel(surname2);

        panelName.add(labelName);
        panelSurname.add(labelSurname1);
//        panelSurname.add(labelSurname2);
        panelFullName.add(panelName);
        panelFullName.add(panelSurname);

        return panelFullName;
    }

    private JPanel createCitasPanel(){
        List<Cita> citas = serviceCita.askCitasByPaciente(DisplayLayout.pacienteSession);
        JPanel panelCitas = new JPanel(new GridLayout(citas.size(),1));
        for(Cita cita: citas){
            panelCitas.add(new JLabel(cita.getInfo()));
        }
        return panelCitas;
    }

}
