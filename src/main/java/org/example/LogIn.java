package org.example;

import javax.swing.*;

public class LogIn {
    private JTextField emailField;
    private JPanel panel1;
    private JLabel emailLabel;
    private JLabel password;
    private JTextField passwordField;
    private JButton registrarButton;
    private JButton iniciarSesionButton;

    private void iniciarSesion(){

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Init");
        frame.setContentPane(new LogIn().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//        this.registrarButton.setAction();
    }

}



