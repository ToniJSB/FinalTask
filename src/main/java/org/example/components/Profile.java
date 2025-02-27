package org.example.components;

import org.example.Constants;
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
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.List;

/**
 * The Profile class represents a panel for displaying and managing user profile information.
 */
public class Profile extends JPanel {

    private final String[] COLUMNAS = {"id","Fecha","Hora","Especialidad","Nombre", "Acciones"};
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

    /**
     * Constructs a Profile panel with the specified session.
     *
     * @param session the Hibernate session
     */
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

    /**
     * Sets the display of the Profile panel.
     */
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

    /**
     * Creates a panel for displaying the full name.
     *
     * @return the JPanel containing the full name
     */
    private JPanel createPanelFullName(){
        JPanel panelFullName = new JPanel(new GridLayout(2,1));
        JPanel panelName = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelName = new JLabel("Nombre: "+name);

        JPanel panelSurname = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelSurname1 = new JLabel("Apellidos: %s %s".formatted(surname1,surname2));

        panelName.add(labelName);
        panelSurname.add(labelSurname1);
        panelFullName.add(panelName);
        panelFullName.add(panelSurname);

        return panelFullName;
    }

    /**
     * Creates the panel for displaying appointments.
     */
    private void createCitasPanel(){
        panelCitas = new JPanel();
        initTable();
        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        tablaCitas.setSize(Constants.SCREEN_SIZE.width/3,Constants.SCREEN_SIZE.height/3);
        tablaCitas.setRowHeight(30);
        tablaCitas.getColumn("id").setMinWidth(0);
        tablaCitas.getColumn("id").setMaxWidth(0);
        tablaCitas.getColumn("id").setWidth(0);
        tablaCitas.getColumn("Hora").setMinWidth(50);
        tablaCitas.getColumn("Hora").setMaxWidth(50);
        tablaCitas.getColumn("Hora").setWidth(50);
        tablaCitas.getColumn("Especialidad").setCellEditor(new NonEditableCell());
        tablaCitas.getColumn("Nombre").setCellEditor(new NonEditableCell());
        tablaCitas.getColumn("Hora").setCellEditor(new NonEditableCell());
        tablaCitas.getColumn("Fecha").setCellEditor(new NonEditableCell());

        tablaCitas.getColumn("Acciones").setWidth(200);
        tablaCitas.getColumn("Acciones").setMaxWidth(200);
        tablaCitas.getColumn("Acciones").setMinWidth(200);
        tablaCitas.getColumn("Acciones").setCellEditor(new ButtonEditor(new JCheckBox()));
        tablaCitas.getColumn("Acciones").setCellRenderer(new ButtonRenderer());

        tablaCitas.setDragEnabled(false);
        panelCitas.add(scrollPane);
    }

    /**
     * Initializes the table for displaying appointments.
     */
    private void initTable(){
        List<Cita> citas = serviceCita.askCitasByPaciente(DisplayLayout.pacienteSession);

        DefaultTableModel model = new DefaultTableModel(COLUMNAS, 0);
        for (Cita cita : citas) {
            String fechaCita = Utils.DateFormat.dateAsStringDateF(cita.getFechaCita());

            String horaCita = cita.getHoraCita().toString();
            String especialidad = cita.getMedico().getEspecialidad();
            String nombre = cita.getMedico().getFullName();
            String[] fila = {cita.getIdCita().toString(),fechaCita, horaCita,especialidad, nombre,cita.getIdCita().toString()};

            model.addRow(fila);
        }
        tablaCitas =  new JTable(model);
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton cancelarBtn = new JButton();
            JButton reprogramadoBtn = new JButton();
            JPanel btnsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            Cita cita = serviceCita.askCitaById(Integer.parseInt("1"));
            cancelarBtn.setOpaque(true);
            cancelarBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    cita.setEstado(EstadoCita.CANCELADA);

                    serviceCita.updateCita(cita);
                    remove(panelCitas);
                    repaint();
                    revalidate();
                    createCitasPanel();
                    add(panelCitas);
                }
            });
            reprogramadoBtn.setOpaque(true);
            reprogramadoBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Calendar(serviceCita.getSession(), cita);
                }
            });


            cancelarBtn.setText("Cancelar");
            reprogramadoBtn.setText("Reprogramar");
            btnsPanel.setBackground(Color.WHITE);
            btnsPanel.add(cancelarBtn);
            btnsPanel.add(reprogramadoBtn);
            return btnsPanel;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);


        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            JPanel btnsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton cancelarBtn = new JButton();
            JButton reprogramadoBtn = new JButton();
            Cita cita = serviceCita.askCitaById(Integer.parseInt(label));
            cancelarBtn.setOpaque(false);
            cancelarBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    cita.setEstado(EstadoCita.CANCELADA);

                    serviceCita.updateCita(cita);
                    remove(panelCitas);
                    repaint();
                    revalidate();
                    createCitasPanel();
                    add(panelCitas);

                }
            });
            reprogramadoBtn.setOpaque(false);
            reprogramadoBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Calendar(serviceCita.getSession(), cita);
                }
            });


            cancelarBtn.setText("Cancelar");
            reprogramadoBtn.setText("Reprogramar");
            btnsPanel.setBackground(Color.WHITE);
            btnsPanel.add(cancelarBtn);
            btnsPanel.add(reprogramadoBtn);
            isPushed = true;
            return btnsPanel;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {

            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    static class NonEditableCell implements TableCellEditor {

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return null;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            return false;
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return false;
        }

        @Override
        public boolean stopCellEditing() {
            return false;
        }

        @Override
        public void cancelCellEditing() {

        }

        @Override
        public void addCellEditorListener(CellEditorListener l) {

        }

        @Override
        public void removeCellEditorListener(CellEditorListener l) {

        }
    }

}
