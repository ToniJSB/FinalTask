package org.example.components;

import org.example.Utils;
import org.example.models.Cita;
import org.example.models.EstadoCita;
import org.example.models.Medico;
import org.example.service.ServiceCita;
import org.example.service.ServicePaciente;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.List;

public class Profile extends JPanel {

    private final String[] COLUMNAS = {"Fecha","Hora","Especialidad","Nombre", "Acciones"};
    private ServicePaciente servicePaciente;
    private ServiceCita serviceCita;

    private String dni;
    private String name;
    private String surname1;
    private String surname2;
    private LocalDate birthdate;
    private SignIn editableForm;
    private JPanel panelCitas;
    private JTable tablaCitas;
    private JPanel panelCita;


    public Profile(Session session) {
        super();
        servicePaciente = new ServicePaciente(session);
        serviceCita = new ServiceCita(session);
        name = DisplayLayout.pacienteSession.getNombre();
        surname1 = DisplayLayout.pacienteSession.getApellido1();
        surname2 = DisplayLayout.pacienteSession.getApellido2();
        dni = DisplayLayout.pacienteSession.getDni();
        birthdate = DisplayLayout.pacienteSession.getbDate();
        editableForm = new SignIn(session);
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

//        container.add(infoContainer);
        container.add(editableForm);
        add(container);
        createCitasPanel();
        add(panelCitas);
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

    private void createCitasPanel(){
        panelCitas = new JPanel();
        initTable();
        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        panelCitas.add(scrollPane);
    }

    private void initTable(){
        List<Cita> citas = serviceCita.askCitasByPaciente(DisplayLayout.pacienteSession);

        DefaultTableModel model = new DefaultTableModel(COLUMNAS, 0);
        for (Cita cita : citas) {
            String fechaCita = Utils.DateFormat.dateAsStringDateF(cita.getFechaCita());

            String horaCita = cita.getHoraCita().toString();
            String especialidad = cita.getMedico().getEspecialidad();
            String nombre = cita.getMedico().getFullName();
            String[] fila = {fechaCita, horaCita,especialidad, nombre,"Acciones"};

            model.addRow(fila);
        }
        tablaCitas =  new JTable(model);
    }
    private ActionListener cancelarCita(Cita cita){
        return e -> {
            cita.setEstado(EstadoCita.CANCELADA);
            serviceCita.updateCita(cita);
            remove(panelCitas);
            repaint();
            revalidate();
            createCitasPanel();
            add(panelCitas);
        };
    }
    private ActionListener reprogramarCita(Cita cita){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Calendar(serviceCita.getSession(), cita);
//                cita.setEstado(EstadoCita.REPROGRAMADA);
//                serviceCita.actualizarCita(cita);
            }
        };
    }
}
