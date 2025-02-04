package org.example;

import org.example.components.*;
import org.example.components.LogIn;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Routing {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Acceso: Hospital tramuntana");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Image icon = Utils.getLogo();
        frame.setIconImage(icon);
        System.out.println(frame.getIconImage());


//        frame.setSize(600,600);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        frame.add(mainPanel);


        JPanel loginPanel =  setLoginPanel(mainPanel,cardLayout);
        JButton[] botones = {new JButton("Informes"), new JButton("Medicos"), new JButton("Perfil"), new JButton("Calendario")};
        DisplayLayout displayLayout =  new DisplayLayout(cardLayout,botones);

        Profile profile = new Profile();
        Calendar calendario = new Calendar();
        displayLayout.appendBody(profile,"PERFIL");
        displayLayout.appendBody(calendario, "CALENDARIO");

        mainPanel.add(loginPanel,"LOGIN");
        mainPanel.add(displayLayout,"DISPLAY");

        cardLayout.show(mainPanel,"LOGIN");


        frame.setVisible(true);
    }


    private static JPanel createPanel(String message, String buttonText){
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        JButton button = new JButton(buttonText);

        panel.add(label, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel setLoginPanel(JPanel mainPanel, CardLayout mainLayout){
//        mainPanel.get
        JPanel fulledContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,20,100));
        JPanel innerContainer = new JPanel();
        fulledContainer.add(innerContainer);
        innerContainer.setLayout(new GridLayout(3,1));
        innerContainer.setBackground(new Color(15,15,15));

        JPanel loginFormPanel = new JPanel();

        JPanel emailPanel = new JPanel();
        JLabel emailLabel = new JLabel("Email");
        JTextField emailField = new JTextField();

        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Password");
        JTextField passwordField = new JTextField();

        JPanel buttonsFormPanel = new JPanel();
        JButton registrarButton = new JButton("Registrar");
        JButton iniciarSesionButton = new JButton("Iniciar sesión");


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


        Image logo = Utils.getLogo();
        int newWidth = 200;
        int newHeight = 150;
        Image scaledImage = logo.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(imageIcon);
        innerContainer.add(imageLabel);

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
        setLoginActionButton(iniciarSesionButton,mainPanel,mainLayout);
        return fulledContainer;

    }

    private static JPanel setProfilePanel(JPanel mainPanel, CardLayout mainLayout){

        JPanel fulledContainer = new JPanel(new BorderLayout());
        JPanel body = new JPanel(new CardLayout());


        return fulledContainer;
    }

    private static JPanel setDisplayLayout(JPanel mainPanel, CardLayout mainLayout){
        JButton[] botones = {new JButton("Citas"),new JButton("Informes"), new JButton("Medicos"), new JButton("Medicos")};

        JPanel fulledContainer = new JPanel(new BorderLayout());
        JPanel aside = new JPanel(new GridLayout(botones.length*2,1));
        JPanel body = new JPanel(new CardLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Image logo = Utils.getLogo();

        int newWidth = 100;
        int newHeight = 100;
        Image scaledImage = logo.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(imageIcon);
        logoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        top.add(logoLabel);
        top.add(new JLabel("Hospital Tramuntana"));
//        Border dividerBorder = BorderFactory.createLineBorder(Color.black);
//        Border dividerBorder = BorderFactory.create;
        top.setBorder(new MatteBorder(-1,-1,2,-1,Color.BLACK));

        aside.setBackground(new Color(878762));

        aside.add(botones[0]);
        aside.add(botones[1]);
        aside.add(botones[2]);
        aside.add(botones[3]);

        fulledContainer.add(aside,BorderLayout.WEST);
        fulledContainer.add(top,BorderLayout.NORTH);
        fulledContainer.add(body,BorderLayout.CENTER);

        return fulledContainer;
    }


    private static void setLoginActionButton(JButton loginButton, JPanel mainPanel, CardLayout mainLayout){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainLayout.show(mainPanel,"DISPLAY");
            }
        });

    }

}
