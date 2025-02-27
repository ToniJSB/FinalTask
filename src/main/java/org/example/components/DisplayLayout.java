package org.example.components;

import org.example.Constants;
import org.example.Utils;
import org.example.models.Paciente;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DisplayLayout extends JPanel{
    private JPanel aside;
    private JPanel innerAside;
    private JPanel body;
    private JPanel bottom;
    private JPanel top;
    private final JButton[] asideButtons;
    public static Paciente pacienteSession;


    public DisplayLayout(LayoutManager layout, JButton[] buttons) {
        super(layout);
        asideButtons = buttons;
        setDisplayLayout();
    }

    public void appendBody(JPanel panel, String constraint){
        body.add(panel,constraint);
        for (JButton button : asideButtons){
//            button.setBackground(Constants.Colors.BUTTON_ASIDE_BACKGROUND);
            if (button.getText().toUpperCase().equals(constraint)){
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ((CardLayout)body.getLayout()).show(body,constraint);
                        body.revalidate();
                        body.repaint();
                    }
                });
            }
        }
    }

    public JPanel getBody() {
        return body;
    }

    private void setDisplayLayout(){
        top = new JPanel(new FlowLayout(FlowLayout.LEFT));

//        innerAside = new JPanel(new FlowLayout(FlowLayout.CENTER,15,10));
        aside = new JPanel();

        body = new JPanel(new CardLayout());
//        body.setBorder(new BorderTopLeft(5, Constants.Colors.BORDER_DIVIDER)); // Grosor: 5px, Color: Rojo
        body.setBorder(new MatteBorder(5,5,-1,-1,Constants.Colors.BORDER_DIVIDER));

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
//        top.setBorder(new MatteBorder(-1,-1,2,-1,Color.BLACK));
        top.add(logoLabel);

    }

    private void setFooter(){
        JPanel footerContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel linkLabel = new JLabel("Carrer raixa, Son vida, en frente de Sports field");
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
        footerContainer.add(linkLabel);
        footerContainer.add(imageLabel);
        bottom.add(footerContainer);

    }

}
