package org.example.components;

import org.example.models.Medico;
import org.example.service.ServiceMedico;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.List;

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

    public Medicos(Session session, JTextField nameMedicoField) {
        super();
        serviceMedico = new ServiceMedico(session);
        txtNameMedico = nameMedicoField;
        setDisplay();
    }

    private void setDisplay() {
        superContainer = new JPanel();
        medicosList = serviceMedico.getAllMedicos();
        container = new JPanel();

        setupLayout();
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                medicosList = serviceMedico.getMedicosByEspecialidadPlusName(especialidad.getText(),name.getText(),apellidos.getText());
                superContainer.remove(container);
                setupLayout();
            }
        });
    }

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
//        searchContainer.setSize(100,200);
        name = new JTextField(15);
        apellidos = new JTextField(15);
        especialidad = new JTextField(15);
        int row=0;
        addField(gbc, row++, "Nombre del doctor: ", name, searchContainer);
        addField(gbc, row++, "Apellidos", apellidos, searchContainer);
        addField(gbc, row++, "Especialidad", especialidad, searchContainer);

//        searchContainer.add(name);
//        searchContainer.add(apellidos);
//        searchContainer.add(especialidad);
        superContainer.add(searchContainer);
        superContainer.add(container);
        add(superContainer);

    }

    private JTable initTable(){
        DefaultTableModel model = new DefaultTableModel(COLUMNAS, 0);
        for (Medico medico: medicosList) {
            System.out.println(medico.simpleInfo());
            String medicoId = String.valueOf(medico.getIdMedico());
            String especializacion = medico.getEspecialidad();
            String nombre = medico.getNombre();
            String apellidos = medico.getApellidos();
            String[] fila = {medicoId,especializacion, nombre,apellidos,"Seleccionar"};
            model.addRow(fila);
        }
        return new JTable(model);
    }

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

    private void btnSetMedico(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(btn.getName());
                txtNameMedico.setText(btn.getName());
                ((CardLayout)getParent().getLayout()).show(getParent(),"CALENDAR");
                superContainer.getParent().repaint();
                superContainer.getParent().revalidate();
            }
        });
    }

    // Renderizador personalizado para botones
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

    // Editor personalizado para botones
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;

        public ButtonEditor(JTable table, JCheckBox checkBox) {
            super(checkBox);
            this.table = table; // Guardar la referencia de la tabla
            button = new JButton();
            button.setOpaque(true);
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
