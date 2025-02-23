package org.example.components;

import org.example.Utils;
import org.example.models.HistorialMedico;
import org.example.service.ServiceHistorialMedico;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;
import java.util.List;

public class CHistorialMedico extends JPanel {
    private ServiceHistorialMedico serviceHistorialMedico;
    public CHistorialMedico(Session session) {
        super();
        serviceHistorialMedico = new ServiceHistorialMedico(session);

        setDisplay();
    }

    private void setDisplay() {
        setLayout(new CardLayout());
        System.out.println();
        List<HistorialMedico> listHMedico = serviceHistorialMedico.askHistorialMedicoFromPaciente(DisplayLayout.pacienteSession);
        String[] columnas = {"Fecha","Diagnóstico", "Tratamiento", "Acciones"};
        DefaultTableModel model = new DefaultTableModel(columnas,0);
        for (HistorialMedico hMedico : listHMedico){
            String[] tuple = {Utils.DateFormat.dateAsStringDateF(hMedico.getFechaVisita()),hMedico.getDiagnostico(),hMedico.getTratamiento(), "Editar"};
            model.addRow(tuple);
        }
        JTable listHistorial = new JTable(model);

        listHistorial.getColumn("Fecha").setCellEditor(new NonEditableCell());
        listHistorial.getColumn("Fecha").setWidth(90);
        listHistorial.getColumn("Fecha").setMaxWidth(90);
        listHistorial.getColumn("Fecha").setMinWidth(90);

        listHistorial.getColumn("Diagnóstico").setCellEditor(new NonEditableCell());
        listHistorial.getColumn("Tratamiento").setCellEditor(new NonEditableCell());

        JScrollPane scrollPane = new JScrollPane(listHistorial);
        add(scrollPane);
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
