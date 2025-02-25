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
        innerContainer.setLayout(new BoxLayout(innerContainer,BoxLayout.Y_AXIS));

        instanceComponents();
        setLoginFormPanel();
        setButtonsFormPanel();

        appendLogo(innerContainer);

        innerContainer.setOpaque(false);
        loginFormPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        innerContainer.add(loginFormPanel);
        innerContainer.add(buttonsFormPanel);

        setLayout(new FlowLayout(FlowLayout.CENTER));
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
//        registrarButton.setSize(10,10);
//        iniciarSesionButton.setSize(10,10);
        registrarButton.setPreferredSize(new Dimension(150,50));
        iniciarSesionButton.setPreferredSize(new Dimension(150,50));

        buttonsFormPanel.add(registrarButton);
        buttonsFormPanel.add(iniciarSesionButton);
        buttonsFormPanel.setLayout(new GridLayout());
    }
    private void setLoginFormPanel(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension();
        dim.setSize(screenSize.getWidth()/3,screenSize.getHeight()/10);

        emailPanel.setLayout(new GridLayout(1,2));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        loginFormPanel.add(emailPanel);


        passwordPanel.setLayout(new GridLayout(1,2));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
//        Dimension btnDim = new Dimension();
//        btnDim.setSize(screenSize.getWidth()/3,screenSize.getHeight()/16);
//        passwordPanel.setPreferredSize(btnDim);
//        passwordPanel.setSize(btnDim);

        loginFormPanel.add(passwordPanel);
        loginFormPanel.setPreferredSize(dim);
        loginFormPanel.setSize(dim);

        loginFormPanel.setLayout(new GridLayout(2,1));


    }

    private void appendLogo(JPanel innerContainer){
        try{
            File file = new File("./src/main/resources/img-Photoroom.png");

            Image originalImage = ImageIO.read(file);

            // Escalar la imagen
            int newWidth = 200;
            int newHeight = 200;
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
            JButton[] botones = {new JButton("Historial"), new JButton("Perfil"), new JButton("Calendario")};

            DisplayLayout.pacienteSession = pacienteLogged;
            DisplayLayout displayLayout =  new DisplayLayout(initLayout,botones);
            Profile profile = new Profile(dbSession);
            CHistorialMedico historialMedico = new CHistorialMedico(dbSession);
            Calendar calendario = new Calendar(dbSession,displayLayout);

            displayLayout.appendBody(profile,"PERFIL");
            displayLayout.appendBody(historialMedico,"HISTORIAL");
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
