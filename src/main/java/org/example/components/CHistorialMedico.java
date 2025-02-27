package org.example.components;

import org.example.Utils;
import org.example.models.Cita;
import org.example.models.EstadoCita;
import org.example.models.HistorialMedico;
import org.example.service.PdfCreator;
import org.example.service.ServiceHistorialMedico;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.List;

/**
 * The CHistorialMedico class represents a panel for displaying the medical history.
 */
public class CHistorialMedico extends JPanel {
    private ServiceHistorialMedico serviceHistorialMedico;

    /**
     * Constructs a CHistorialMedico panel with the specified session.
     *
     * @param session the Hibernate session
     */
    public CHistorialMedico(Session session) {
        super();
        serviceHistorialMedico = new ServiceHistorialMedico(session);
        setDisplay();
    }

    /**
     * Sets the display of the CHistorialMedico panel.
     */
    private void setDisplay() {
        setLayout(new CardLayout());
        List<HistorialMedico> listHMedico = serviceHistorialMedico.askHistorialMedicoFromPaciente(DisplayLayout.pacienteSession);
        String[] columnas = {"id","Fecha","Diagnóstico", "Tratamiento", "Acciones"};
        DefaultTableModel model = new DefaultTableModel(columnas,0);
        for (HistorialMedico hMedico : listHMedico){
            String[] tuple = {String.valueOf(hMedico.getIdHistorial()),Utils.DateFormat.dateAsStringDateF(hMedico.getFechaVisita()),hMedico.getDiagnostico(),hMedico.getTratamiento(), "Imprimir"};
            model.addRow(tuple);
        }
        JTable listHistorial = new JTable(model);
        listHistorial.setDragEnabled(false);
        listHistorial.getColumn("id").setWidth(-10);
        listHistorial.getColumn("id").setMaxWidth(-10);
        listHistorial.getColumn("id").setMinWidth(-10);
        listHistorial.getColumn("id").setPreferredWidth(-10);
        listHistorial.getColumn("Fecha").setWidth(90);
        listHistorial.getColumn("Fecha").setMaxWidth(90);
        listHistorial.getColumn("Fecha").setMinWidth(90);
        listHistorial.getColumn("Acciones").setWidth(90);
        listHistorial.getColumn("Acciones").setMaxWidth(90);
        listHistorial.getColumn("Acciones").setMinWidth(90);

        listHistorial.getColumn("Fecha").setCellEditor(new NonEditableCell());
        listHistorial.getColumn("Diagnóstico").setCellEditor(new NonEditableCell());
        listHistorial.getColumn("Tratamiento").setCellEditor(new NonEditableCell());
        listHistorial.getColumn("Acciones").setCellEditor(new ButtonEditor(new JCheckBox()));
        listHistorial.getColumn("Acciones").setCellRenderer(new ButtonRenderer());

        JScrollPane scrollPane = new JScrollPane(listHistorial);
        add(scrollPane);
    }

    /**
     * The ButtonRenderer class represents a button renderer. used to create a button in a cell.
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {

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
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);


        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JButton imprimirBtn = new JButton();
            imprimirBtn.setOpaque(true);
            HistorialMedico historialMedico = serviceHistorialMedico.askHistorialMedicoById(Integer.parseInt(table.getModel().getValueAt(row,0).toString()));
            imprimirBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PdfCreator.create(DisplayLayout.pacienteSession, historialMedico);
                }
            });

            label = (value == null) ? "" : value.toString();
            imprimirBtn.setText(label);
            return imprimirBtn;
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
