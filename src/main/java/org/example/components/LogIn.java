package org.example.components;

import org.example.App;
import org.example.models.Paciente;
import org.example.service.ServicePaciente;
import org.hibernate.Session;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LogIn  extends JPanel {

    private JTextField emailField;
    private JPanel loginFormPanel;
    private JPanel buttonsFormPanel;
    private JPanel emailPanel;
    private JPanel passwordPanel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registrarButton;
    private JButton iniciarSesionButton;
    private ServicePaciente servicePaciente;
    private JPanel initPanel;
    private CardLayout initLayout;
    private Session dbSession;

    public LogIn(JPanel mainPanel, CardLayout mainLayout, Session session) throws HeadlessException {
        super();
//        setLoginFormPanel();
        dbSession = session;
        servicePaciente = new ServicePaciente(session);
        setDisplay();
        initPanel = mainPanel;
        initLayout = mainLayout;
        setActionsButtons(mainPanel, mainLayout);
    }

    private JPanel setDisplay(){
        JPanel innerContainer = new JPanel();
        innerContainer.setLayout(new GridLayout(3,1));
        innerContainer.setBackground(new Color(15,15,15));

        instanceComponents();
        setLoginFormPanel();
        setButtonsFormPanel();

        appendLogo(innerContainer);

        innerContainer.setOpaque(false);
        innerContainer.add(loginFormPanel);
        innerContainer.add(buttonsFormPanel);

        setBackground(new Color(5,215,125));
        setLayout(new FlowLayout(FlowLayout.CENTER,20,100));
        add(innerContainer);
        return innerContainer;


    }
    private void instanceComponents(){
        loginFormPanel = new JPanel();

        emailPanel = new JPanel();
        emailLabel = new JLabel("Email");
        emailField = new JTextField();

        passwordPanel = new JPanel();
        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(20);

        buttonsFormPanel = new JPanel();
        registrarButton = new JButton("Registrar");
        iniciarSesionButton = new JButton("Iniciar sesión");
    }

    private void setButtonsFormPanel(){
        registrarButton.setSize(10,10);
        iniciarSesionButton.setSize(10,10);

        buttonsFormPanel.add(registrarButton);
        buttonsFormPanel.add(iniciarSesionButton);
        buttonsFormPanel.setBackground(new Color(150,20,52));
        buttonsFormPanel.setSize(10,10);
        buttonsFormPanel.setLayout(new GridLayout());
    }
    private void setLoginFormPanel(){
        emailPanel.setLayout(new GridLayout(1,2));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        loginFormPanel.add(emailPanel);


        passwordPanel.setLayout(new GridLayout(1,2));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        loginFormPanel.add(passwordPanel);

        emailPanel.setSize(80,80);
        passwordPanel.setSize(80,80);
        loginFormPanel.setSize( 0, 0);

        loginFormPanel.setLayout(new GridLayout(2,1));


    }

    private void appendLogo(JPanel innerContainer){
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
    }

    private boolean login(){
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        Paciente pacienteLogged = servicePaciente.getPacienteByEmail(email);
        boolean result = false;
        if (servicePaciente.isValidPassowrd(password,pacienteLogged.getPassword())){
            JButton[] botones = {new JButton("Informes"), new JButton("Medicos"), new JButton("Perfil"), new JButton("Calendario")};

            DisplayLayout.pacienteSession = pacienteLogged;
            DisplayLayout displayLayout =  new DisplayLayout(initLayout,botones);
            Profile profile = new Profile(dbSession);
            Calendar calendario = new Calendar(dbSession);

            displayLayout.appendBody(profile,"PERFIL");
            displayLayout.appendBody(calendario, "CALENDARIO");
            initPanel.add(displayLayout,"DISPLAY");
            result = true;
        }
        return result;
    }

    private void setActionsButtons(JPanel mainPanel, CardLayout mainLayout){
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(login()){
                    mainLayout.show(mainPanel,"DISPLAY");
                }
            }
        });
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainLayout.show(mainPanel,"SIGNIN");
            }
        });

    }
}
