package org.example.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calendar extends JPanel {
    private JTextArea[] areasDeTexto;
    private String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};


    public Calendar() {
        createCalendar();
    }

    private void guardarAgenda() {
        StringBuilder agenda = new StringBuilder("Agenda Semanal:\n");

        // Recorrer los JTextArea y obtener su contenido
        for (int i = 0; i < 7; i++) {
            String dia = dias[i]; // Obtener el nombre del día
            String eventos = areasDeTexto[i].getText(); // Obtener los eventos del día

            agenda.append(dia).append(":\n").append(eventos).append("\n\n");
        }

        // Mostrar la agenda en un JOptionPane
        JOptionPane.showMessageDialog(this, agenda.toString(), "Agenda Guardada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createCalendar(){
        JPanel mainPanel = new JPanel(new GridLayout(1, 7, 10, 10)); // Espacio entre celdas
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes

        // Nombres de los días de la semana

        // Inicializar el array de JTextArea
        areasDeTexto = new JTextArea[7];

        // Crear un JTextArea para cada día
        for (int i = 0; i < 7; i++) {
            JPanel dayPanel = new JPanel(new BorderLayout());
            dayPanel.setBorder(BorderFactory.createTitledBorder(dias[i])); // Título del día

            areasDeTexto[i] = new JTextArea();
            areasDeTexto[i].setLineWrap(true); // Ajustar texto automáticamente
            areasDeTexto[i].setWrapStyleWord(true); // Ajustar por palabras

            JScrollPane scrollPane = new JScrollPane(areasDeTexto[i]); // Agregar barra de desplazamiento
            dayPanel.add(scrollPane, BorderLayout.CENTER);

            mainPanel.add(dayPanel); // Añadir el panel del día al panel principal
        }

        // Crear un botón para guardar la agenda
        JButton guardarButton = new JButton("Guardar Agenda");
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarAgenda();
            }
        });

        // Añadir el panel principal y el botón al JFrame
        add(mainPanel, BorderLayout.CENTER);
        add(guardarButton, BorderLayout.SOUTH);
    }

}
