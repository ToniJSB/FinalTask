package org.example;

import org.example.components.*;
import org.example.dao.AccessDB;
import org.example.models.Paciente;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;

public class App {
    private AccessDB accessDB;
    private Session dbSession;

    public App() {

        JFrame frame = new JFrame("Acceso: Hospital tramuntana");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Image icon = Utils.getLogo();
        frame.setIconImage(icon);
        System.out.println(frame.getIconImage());
        accessDB = new AccessDB();
        openSession();
        frame.add(initRouting(dbSession));
        frame.setVisible(true);

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
            closeSession();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

    }
    private void closeSession(){
        dbSession.close();
    }
    private void openSession(){
        dbSession = accessDB.getSessionFactory();
    }





    private JPanel initRouting(Session session){
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);


        LogIn loginPanel =  new LogIn(mainPanel,cardLayout,session);
        SignIn signInPanel = new SignIn(mainPanel,cardLayout,session);

        // Cambiar el flujo. Ha de ir desde el login a Display


        mainPanel.add(signInPanel,"SIGNIN");
        mainPanel.add(loginPanel,"LOGIN");

        cardLayout.show(mainPanel,"LOGIN");

        return mainPanel;
    }




}
