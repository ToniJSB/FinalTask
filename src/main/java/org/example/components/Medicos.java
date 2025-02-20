package org.example.components;

import org.example.models.Medico;
import org.example.service.ServiceMedico;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Medicos extends JPanel {
    private JPanel superContainer;
    private JPanel container;
    private JTextField txtNameMedico;
    private ServiceMedico serviceMedico;
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
        JScrollPane scrollPane = new JScrollPane(container);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        for (Medico medico:serviceMedico.getAllMedicos()){
            JButton btnMedico = new JButton();
            btnMedico.setText(medico.simpleInfo());
            btnMedico.setName(String.valueOf(medico.getIdMedico()));
            btnSetMedico(btnMedico);
            addField(gbc, row++,medico.getNombre(),btnMedico);
        }

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
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


}
