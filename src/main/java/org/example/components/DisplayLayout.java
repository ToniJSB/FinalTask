package org.example.components;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import org.example.Constants;
import org.example.Utils;
import org.example.models.Paciente;

/**
 * The DisplayLayout class represents the main layout for the application.
 */
public class DisplayLayout extends JPanel{
    private JPanel aside;
    private JPanel body;
    private JPanel bottom;
    private JPanel top;
    private final JButton[] asideButtons;
    public static Paciente pacienteSession;

    /**
     * Constructs a DisplayLayout with the specified layout and buttons.
     *
     * @param layout the layout manager
     * @param buttons the array of buttons
     */
    public DisplayLayout(LayoutManager layout, JButton[] buttons) {
        super(layout);
        asideButtons = buttons;
        setDisplayLayout();
    }

    /**
     * Appends a panel to the body with the specified constraint.
     *
     * @param panel the panel to append
     * @param constraint the constraint for the layout
     */
    public void appendBody(JPanel panel, String constraint){
        body.add(panel,constraint);
        for (JButton button : asideButtons){
            if (button.getText().toUpperCase().equals(constraint)){
                button.addActionListener((ActionEvent e) -> {
                    ((CardLayout)body.getLayout()).show(body,constraint);
                    body.revalidate();
                    body.repaint();
                });
            }
        }
    }

    /**
     * Gets the body panel.
     *
     * @return the body panel
     */
    public JPanel getBody() {
        return body;
    }

    /**
     * Sets the display layout.
     */
    private void setDisplayLayout(){
        top = new JPanel(new FlowLayout(FlowLayout.LEFT));

//        innerAside = new JPanel(new FlowLayout(FlowLayout.CENTER,15,10));
        aside = new JPanel();

        body = new JPanel(new CardLayout());
//        body.setBorder(new BorderTopLeft(5, Constants.Colors.BORDER_DIVIDER)); // Grosor: 5px, Color: Rojo
        body.setBorder(new MatteBorder(-1,5,-1,-1,Constants.Colors.BORDER_DIVIDER));

        bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));

        setHeader();
        setFooter();
        setAside();

        JPanel fulledContainer = new JPanel(new BorderLayout());

        fulledContainer.add(top,BorderLayout.NORTH);
        fulledContainer.add(body,BorderLayout.CENTER);
        fulledContainer.add(bottom,BorderLayout.SOUTH);
        fulledContainer.add(aside,BorderLayout.WEST);

        add(fulledContainer);

    }

    /**
     * Sets up the aside panel.
     */
    private void setAside(){
        aside.setLayout(new BoxLayout(aside, BoxLayout.Y_AXIS));
        aside.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        aside.setBackground(Constants.Colors.ASIDE_BACKGROUND);
        for (JButton boton : asideButtons){
            boton.setAlignmentX(Component.LEFT_ALIGNMENT);
            boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            boton.setBackground(Constants.Colors.BUTTON_ASIDE_BACKGROUND);
            boton.setMargin(new Insets(5,5,5,5));
            aside.add(boton);
        }
    }

    /**
     * Sets up the header panel.
     */
    private void setHeader(){
        Image logo = Utils.getLogo();
        int newWidth = 100;
        int newHeight = 100;
        Image scaledImage = logo.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel("Hospital Tramuntana",imageIcon,SwingConstants.LEFT);
        Font fHeader = logoLabel.getFont();
        fHeader.deriveFont(Font.BOLD,1000000f);
        logoLabel.setFont(fHeader);
        top.setBorder(BorderFactory.createMatteBorder(-1,-1,10,-1, Constants.Colors.BORDER_DIVIDER));
        top.add(logoLabel);

    }

    /**
     * Sets up the footer panel.
     */
    private void setFooter(){
        bottom.setLayout(new BorderLayout());
        bottom.setBorder(BorderFactory.createMatteBorder(10,-1,-1,-1, Constants.Colors.BORDER_DIVIDER));
        JPanel locationContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel linkLabel = new JLabel("<html><u>Carrer raixa, Son vida, en frente de Sports field</u></html>");
        Image logo = Utils.getFotoMap();
        int newWidth = 200;
        int newHeight = 150;
        Image scaledImage = logo.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(imageIcon);

        linkLabel.setForeground(Color.BLUE);
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Agregar un MouseListener para abrir la URL
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // Abrir la URL en el navegador predeterminado
                    Desktop.getDesktop().browse(new URI("https://maps.app.goo.gl/ki49PKcxkhajrkTx8"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el color del texto cuando el mouse pasa sobre el enlace
                linkLabel.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar el color del texto cuando el mouse sale del enlace
                linkLabel.setForeground(Color.BLUE);
            }
        });
        locationContainer.add(imageLabel);
        locationContainer.add(linkLabel);

        JPanel contactContainer = new JPanel();
        contactContainer.setBorder(BorderFactory.createEmptyBorder(15,0,0,15));
        contactContainer.setLayout(new BoxLayout(contactContainer, BoxLayout.Y_AXIS));
        JLabel contactLabel = new JLabel("Contacte con nosotros:");
        JLabel phoneAdminLabel = new JLabel("   Servicio de administración: 682 93 62 01");
        JLabel phoneEmergenciesLabel = new JLabel("   Servicio de emergencia: 670 40 31 80");
        JLabel emailLabel = new JLabel("   Email: administrador_falso@hospitaltramuntana.com");
        contactContainer.add(contactLabel);
        contactContainer.add(phoneAdminLabel);
        contactContainer.add(phoneEmergenciesLabel);
        contactContainer.add(emailLabel);
        bottom.add(locationContainer,BorderLayout.WEST);
        bottom.add(contactContainer,BorderLayout.EAST);

    }

}
