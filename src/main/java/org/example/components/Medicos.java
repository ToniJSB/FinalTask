package org.example.components;

import org.example.models.Medico;
import org.example.service.ServiceMedico;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Medicos extends JPanel {
    private JPanel superContainer;
    private JPanel container;
    private JTextField txtNameMedico;
    private ServiceMedico serviceMedico;

    private final String[] COLUMNAS = {"id","Especialización", "Nombre", "Apellidos", "Acciones"};

    public Medicos(Session session, JTextField nameMedicoField) {
        super();
        serviceMedico = new ServiceMedico(session);
        txtNameMedico = nameMedicoField;
        superContainer = this;
//        setVisible(false);
        setDisplay();
    }

    private void setDisplay() {
        container = new JPanel();
        setupLayout();
    }

    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        container.setLayout(new GridBagLayout());
        container.setSize(100,200);
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
//        JScrollPane scrollPane = new JScrollPane(container);
//
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        DefaultTableModel model = new DefaultTableModel(COLUMNAS, 0);
        // Rellenar la tabla con los datos
        for (Medico medico:serviceMedico.getAllMedicos()) {
            JButton btnMedico = new JButton(medico.simpleInfo());
            btnMedico.setName(String.valueOf(medico.getIdMedico()));

            btnSetMedico(btnMedico);

            String medicoId = String.valueOf(medico.getIdMedico());
            String especializacion = medico.getEspecialidad();
            String nombre = medico.getNombre();
            String apellidos = medico.getApellidos();
//            Component[] fila = {new JLabel(especializacion), new JLabel(nombre), new JLabel(apellidos),btnMedico};
            String[] fila = {medicoId,especializacion, nombre,apellidos,"Seleccionar"};
            model.addRow(fila);
        }
        JTable tabla = new JTable(model);
        tabla.setAutoCreateRowSorter(true);
        tabla.getColumn("id").setMinWidth(0);
        tabla.getColumn("id").setMaxWidth(0);
        tabla.getColumn("id").setWidth(0);

        tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("Acciones").setCellEditor(new ButtonEditor(tabla, new JCheckBox()));
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

//        gbc.gridx = 0;
//        gbc.gridy = row;
//        gbc.gridwidth = 2;
//        gbc.anchor = GridBagConstraints.CENTER;
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
//        buttonPanel.add(btnGuardar);
//        buttonPanel.add(btnCancelar);
//        container.add(buttonPanel, gbc);
        add(scrollPane);
    }


    private void addField(GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        container.add(new JLabel(label), gbc);


        if (!label.equals("Fecha de nacimiento:")){
            gbc.gridx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            container.add(field, gbc);
        }else{
            JPanel calendarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton openCalendar = new JButton("Abrir calendario");
            openCalendar.addActionListener(e -> new CalendarPanel((JTextField) field));
            calendarContainer.add(field);
            calendarContainer.add(openCalendar);
            gbc.gridx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            container.add(calendarContainer, gbc);

        }

    }

    private void btnSetMedico(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(btn.getName());
                txtNameMedico.setText(btn.getName());
                ((CardLayout)superContainer.getParent().getLayout()).show(superContainer.getParent(),"CALENDAR");
                superContainer.getParent().revalidate();
                superContainer.getParent().repaint();
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
                ((CardLayout)superContainer.getParent().getLayout()).show(superContainer.getParent(),"CALENDAR");
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



}
