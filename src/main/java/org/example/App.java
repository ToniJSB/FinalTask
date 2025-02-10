package org.example;

import org.example.components.*;
import org.example.dao.AccessDB;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class App {
    private AccessDB accessDB;
    private Session session;

    public App() {
        JFrame frame = new JFrame("Acceso: Hospital tramuntana");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Image icon = Utils.getLogo();
        frame.setIconImage(icon);
        System.out.println(frame.getIconImage());
        accessDB = new AccessDB();
        openSession();
        frame.add(initRouting(session));
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
        session.close();
    }
    private void openSession(){
        session = accessDB.getSessionFactory();
    }





    private JPanel initRouting(Session session){
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JButton[] botones = {new JButton("Informes"), new JButton("Medicos"), new JButton("Perfil"), new JButton("Calendario")};
        DisplayLayout displayLayout =  new DisplayLayout(cardLayout,botones);

        LogIn loginPanel =  new LogIn(mainPanel,cardLayout);
        SignIn signInPanel = new SignIn(mainPanel,cardLayout,session);
        Profile profile = new Profile(session);
        Calendar calendario = new Calendar();

        displayLayout.appendBody(profile,"PERFIL");
        displayLayout.appendBody(calendario, "CALENDARIO");

        mainPanel.add(signInPanel,"SIGNIN");
        mainPanel.add(loginPanel,"LOGIN");
        mainPanel.add(displayLayout,"DISPLAY");

        cardLayout.show(mainPanel,"LOGIN");

        return mainPanel;
    }




}
