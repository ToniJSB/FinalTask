package org.example.components;

import org.example.Constants;
import org.example.Utils;
import org.example.models.Paciente;
import org.example.service.ServicePaciente;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The LogIn class represents a panel for user login.
 */
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

    /**
     * Constructs a LogIn panel with the specified main panel, layout, and session.
     *
     * @param mainPanel the main panel
     * @param mainLayout the main layout
     * @param session the Hibernate session
     */
    public LogIn(JPanel mainPanel, CardLayout mainLayout, Session session) throws HeadlessException {
        super();
        dbSession = session;
        servicePaciente = new ServicePaciente(session);
        initPanel = mainPanel;
        initLayout = mainLayout;
        setDisplay();
        setActionsButtons(mainPanel, mainLayout);
    }

    /**
     * Sets the display of the LogIn panel.
     *
     * @return the inner container panel
     */
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

    /**
     * Initializes the components of the LogIn panel.
     */
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

    /**
     * Sets up the buttons form panel.
     */
    private void setButtonsFormPanel(){
        registrarButton.setPreferredSize(new Dimension(150,50));
        iniciarSesionButton.setPreferredSize(new Dimension(150,50));

        buttonsFormPanel.add(registrarButton);
        buttonsFormPanel.add(iniciarSesionButton);
        buttonsFormPanel.setLayout(new GridLayout());
    }

    /**
     * Sets up the login form panel.
     */
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

    /**
     * Appends the logo to the specified inner container.
     *
     * @param innerContainer the inner container panel
     */
    private void appendLogo(JPanel innerContainer){
        try{

            Image originalImage = Utils.getLogo();

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

    /**
     * Handles the login process.
     *
     * @return true if login is successful, false otherwise
     */
    private boolean login(){
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        Paciente pacienteLogged = servicePaciente.getPacienteByEmailOrDni(email);
        boolean result = false;
        if (pacienteLogged!=null){
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
        }
        return result;
    }

    /**
     * Sets the actions for the buttons.
     *
     * @param mainPanel the main panel
     * @param mainLayout the main layout
     */
    private void setActionsButtons(JPanel mainPanel, CardLayout mainLayout){
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(login()){
                    mainLayout.show(mainPanel,"DISPLAY");
                }else{
                    JDialog notificacion = new JDialog();
                    JLabel text = new JLabel("Campos incorrectos");
                    notificacion.add(text);
                    notificacion.setVisible(true);
                    notificacion.setSize(150,150);
                    int x = (Constants.SCREEN_SIZE.width - notificacion.getWidth()) / 2;
                    int y = (Constants.SCREEN_SIZE.height - notificacion.getHeight()) / 2;

                    // Establecer la posición del diálogo
                    notificacion.setLocation(x, y);

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
