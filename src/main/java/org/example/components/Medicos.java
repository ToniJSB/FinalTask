package org.example.components;

import org.example.Utils;
import org.example.models.Medico;
import org.example.service.ServiceMedico;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;

/**
 * The Medicos class represents a panel for displaying and managing doctors.
 */
public class Medicos extends JPanel {
    private JPanel superContainer;
    private JPanel container;
    private JTextField txtNameMedico;
    private ServiceMedico serviceMedico;
    private JTable tabla;
    private JTextField name;
    private JTextField apellidos;
    private JTextField especialidad;
    private List<Medico> medicosList;

    private final String[] COLUMNAS = {"id","Especialización", "Nombre", "Apellidos", "Acciones"};

    /**
     * Constructs a Medicos panel with the specified session and name field.
     *
     * @param session the Hibernate session
     * @param nameMedicoField the text field for the doctor's name
     */
    public Medicos(Session session, JTextField nameMedicoField) {
        super();
        serviceMedico = new ServiceMedico(session);
        txtNameMedico = nameMedicoField;
        setDisplay();
    }

    /**
     * Sets the display of the Medicos panel. and adds listeners to the name, apellidos, and especialidad fields.
     */
    private void setDisplay() {
        superContainer = new JPanel();
        medicosList = serviceMedico.getAllMedicos();
        container = new JPanel();
        name = new JTextField(15);
        apellidos = new JTextField(15);
        especialidad = new JTextField(15);

        setupLayout();
        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLayout();
                name.requestFocus();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLayout();
                name.requestFocus();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLayout();
                name.requestFocus();

            }
        });
        apellidos.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLayout();
                apellidos.requestFocus();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLayout();
                apellidos.requestFocus();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLayout();
                apellidos.requestFocus();

            }
        });
        especialidad.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLayout();
                especialidad.requestFocus();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLayout();
                especialidad.requestFocus();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLayout();
                especialidad.requestFocus();

            }
        });


    }

    /**
     * Sets up the layout of the Medicos panel.
     */
    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        tabla = initTable();
        tabla.setAutoCreateRowSorter(true);
        tabla.getColumn("id").setMinWidth(0);
        tabla.getColumn("id").setMaxWidth(0);
        tabla.getColumn("id").setWidth(0);
        tabla.getColumn("Especialización").setCellEditor(new NonEditableCell());
        tabla.getColumn("Nombre").setCellEditor(new NonEditableCell());
        tabla.getColumn("Apellidos").setCellEditor(new NonEditableCell());

        tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("Acciones").setCellEditor(new ButtonEditor(tabla, new JCheckBox()));
        JScrollPane scrollPane = new JScrollPane(tabla);
        container.add(scrollPane);
        add(container, BorderLayout.CENTER);

        JPanel searchContainer = new JPanel();
        searchContainer.setLayout(new GridBagLayout());

        int row=0;
        addField(gbc, row++, "Nombre del doctor: ", name, searchContainer);
        addField(gbc, row++, "Apellidos", apellidos, searchContainer);
        addField(gbc, row++, "Especialidad", especialidad, searchContainer);

        superContainer.add(searchContainer);
        superContainer.add(container);
        add(superContainer);

    }

    /**
     * Initializes the table for displaying doctors.
     *
     * @return the JTable containing the doctors
     */
    private JTable initTable(){
        DefaultTableModel model = new DefaultTableModel(COLUMNAS, 0);
        for (Medico medico: medicosList) {
            String medicoId = String.valueOf(medico.getIdMedico());
            String especializacion = medico.getEspecialidad();
            String nombre = medico.getNombre();
            String apellidos = medico.getApellidos();
            String[] fila = {medicoId,especializacion, nombre,apellidos,"Seleccionar"};
            model.addRow(fila);
        }
        return new JTable(model);
    }

    /**
     * Adds a field to the specified GridBagConstraints.
     *
     * @param gbc the GridBagConstraints
     * @param row the row index
     * @param label the label text
     * @param field the field component
     * @param container the container panel
     */
    private void addField(GridBagConstraints gbc, int row, String label, JComponent field, JPanel container) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        container.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(field, gbc);

    }

    /**
     * Updates the layout of the Medicos panel.
     */
    private void updateLayout(){
        medicosList = serviceMedico.getMedicosByEspecialidadPlusName(Utils.capitalizeFirtsLetter(especialidad.getText()),Utils.capitalizeFirtsLetter(name.getText()),Utils.capitalizeFirtsLetter(apellidos.getText()));
        superContainer.removeAll();
        container = new JPanel();
        tabla = new JTable();
        repaint();
        revalidate();
        setupLayout();
    }

    /**
     * The ButtonRenderer class represents a button renderer. used to create a button in a cell.
     */
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    /**
     * The ButtonEditor class represents a button editor. used to create a button with action in a cell.
     */
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;

        public ButtonEditor(JTable table, JCheckBox checkBox) {
            super(checkBox);
            this.table = table; // Guardar la referencia de la tabla
            button = new JButton();

            button.setOpaque(false);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Aquí puedes manejar la acción del botón
                String nombre = table.getModel().getValueAt(table.getEditingRow(), 0).toString();
//                JOptionPane.showMessageDialog(button, "Detalles de: " + nombre);

                txtNameMedico.setText(nombre.strip());
                ((CardLayout)getParent().getLayout()).show(getParent(),"CALENDAR");
                superContainer.getParent().revalidate();
                superContainer.getParent().repaint();
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

    /**
     * The NonEditableCell class represents a non-editable cell.
     */
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