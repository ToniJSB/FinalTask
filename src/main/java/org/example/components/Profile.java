package org.example.components;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

public class Profile extends JPanel {
    private String dni;
    private String name;
    private String surname1;
    private String surname2;
    private LocalDate birthdate;
    public Profile() {
        super();

        name = "Paco";
        surname1 = "Diaz";
        surname2 = "Puentes";
        dni = "97438263K";
        birthdate = LocalDate.now();
        setDisplay();

    }
    private void setDisplay(){
        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(container);
        JPanel panelFullName = createPanelFullName();
        JPanel panelBirthdate = new JPanel();
        JLabel labelBirthdate = new JLabel(birthdate.toString());

        panelBirthdate.add(labelBirthdate);

        container.add(panelFullName);
        container.add(panelBirthdate);
    }

    private JPanel createPanelFullName(){
        JPanel panelFullName = new JPanel(new GridLayout(1,2));
        JPanel panelName = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelName = new JLabel(name);

        JPanel panelSurname = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelSurname1 = new JLabel(surname1);
        JLabel labelSurname2 = new JLabel(surname2);

        panelName.add(labelName);
        panelSurname.add(labelSurname1);
        panelSurname.add(labelSurname2);
        panelFullName.add(panelName);
        panelFullName.add(panelSurname);

        return panelFullName;
    }

}
