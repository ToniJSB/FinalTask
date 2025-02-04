package org.example.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LogIn  extends JFrame {
    private Container panel;

    private JTextField emailField;
    private JPanel loginFormPanel;
    private JPanel buttonsFormPanel;
    private JPanel emailPanel;
    private JPanel passwordPanel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField passwordField;
    private JButton registrarButton;
    private JButton iniciarSesionButton;
    private JPanel mainPanel;
    private CardLayout mainLayout;

    public LogIn(JPanel mainPanel, CardLayout mainLayout) throws HeadlessException {
        super("Log in");
//        setLoginFormPanel();
        setDisplay();
        this.mainPanel = mainPanel;
        this.mainLayout = mainLayout;
        setActionsButtons();
    }

    private void setActionsButtons(){
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainLayout.show(mainPanel,"HOME");
            }
        });

    }

    private JPanel setDisplay(){
        JPanel innerContainer = new JPanel();
        innerContainer.setLayout(new GridLayout(3,1));
        innerContainer.setBackground(new Color(15,15,15));

        loginFormPanel = new JPanel();

        emailPanel = new JPanel();
        emailLabel = new JLabel("Email");
        emailField = new JTextField();

        passwordPanel = new JPanel();
        passwordLabel = new JLabel("Password");
        passwordField = new JTextField();

        buttonsFormPanel = new JPanel();
        registrarButton = new JButton("Registrar");
        iniciarSesionButton = new JButton("Iniciar sesión");


        loginFormPanel.add(emailPanel);
        emailPanel.setLayout(new GridLayout(1,2));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);


        loginFormPanel.add(passwordPanel);
        passwordPanel.setLayout(new GridLayout(1,2));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        emailPanel.setSize(80,80);
        passwordPanel.setSize(80,80);
        loginFormPanel.setSize( 0, 0);

        loginFormPanel.setLayout(new GridLayout(2,1));



        try{

            File file = new File("./src/main/resources/img.png");

            Image originalImage = ImageIO.read(file);

            // Escalar la imagen
            int newWidth = 200;
            int newHeight = 150;
            Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);

            JLabel imageLabel = new JLabel(imageIcon);
            innerContainer.add(imageLabel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        innerContainer.setOpaque(false);
        innerContainer.add(loginFormPanel);
        innerContainer.add(buttonsFormPanel);

        loginFormPanel.setBackground(new Color(808080));


        registrarButton.setSize(10,10);
        iniciarSesionButton.setSize(10,10);

        buttonsFormPanel.add(registrarButton);
        buttonsFormPanel.add(iniciarSesionButton);
        buttonsFormPanel.setBackground(new Color(150,20,52));
        buttonsFormPanel.setSize(10,10);
        buttonsFormPanel.setLayout(new GridLayout());
        panel.setBackground(new Color(5,215,125));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,20,100));
        panel.add(innerContainer);
        return innerContainer;

//        setSize(600,600);
//        setVisible(true);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//

    }
//
//    public static void main(String[] args) {
//        LogIn login = new LogIn();
//
//    }
}
