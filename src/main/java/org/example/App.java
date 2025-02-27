package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.components.*;
import org.example.dao.AccessDB;
import org.example.models.Medico;


import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;
import java.util.Calendar;
import java.util.List;

/**
 * Main application class for the Hospital Tramuntana system.
 */
public class App {
    private final AccessDB accessDB;
    private Session dbSession;

    /**
     * Constructor for the App class. 
     * Init a main frame and set the look and feel. 
     * Open a session to the database.
     * Finally, add the main panel to the frame.
     * 
     * In case the frame is closed, the database session is closed.
     * 
     * @throws UnsupportedLookAndFeelException if the look and feel is not supported.
     * @throws ClassNotFoundException if the class is not found.
     * @throws InstantiationException if the class cannot be instantiated.
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible.
     */
    public App() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        JFrame frame = new JFrame("Acceso: Hospital tramuntana");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        OwnTheme.setup();
        Image icon = Utils.getLogo();
        frame.setIconImage(icon);
        accessDB = new AccessDB();
        openSession();
        createMedicos();
        cambiarFuenteComponentes(frame, Constants.APP_FONT_CUSTOM);
        frame.add(initRouting(dbSession));
        frame.setVisible(true);

        frame.addWindowListener(customWindowListener());

    }

    private void closeSession(){
        dbSession.close();
    }
    
    private void openSession(){
        dbSession = accessDB.getSessionFactory();
    }

    /**
     * Initializes the routing of the application. with the components for the login and sign in.
     * 
     * @param session
     * @return
     * 
     */
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

    /**
     * Method to create doctors and save them to the database.
     */
    private void createMedicos(){
        List<Map<String,String>> futureNewDocs = new ArrayList<Map<String,String>>();

        String[] fuelte = {
            "Médico general: Bernardo : Garau Quetgles",
            "Traumatólogo: Ana : Romero Santos",
            "Psicóloga: María : Muñoz Ramirez",
            "Psiquiatra: Daniel : Pérez Miguel",
            "Urólogo: Vicente : Canario Ibáñez",
            "Oftalmólogo: Miguel Ángel : Sánchez Ramírez",
            "Pediatra: Ana : Jiménez García",
            "Cirujano: Sebastián : Grau López",
            "Oncólogo: Fernando : Fernández Jaume",
            "Otorrinolaringólogo: Manuel : Socias Gil",
            "Dermatólogo: Antonia : Arenas Llabres",
            "Podólogo: Lidia : Alonso Galera",
            "Radiólogo: Vanesa : Suárez Salas",
            "Fisioterapia: Bernat : Montserrat Gornals"
        };
        int count = 0;
        for (String doc : fuelte){
            String[] data = doc.split(":");
            Map<String,String> newMap = new HashMap<>();
            newMap.put("especialidad",data[0]);
            newMap.put("nombre",data[1]);
            newMap.put("apellidos",data[2]);
            newMap.put("hora_inicio", String.valueOf(Date.UTC(2025,02, Calendar.FEBRUARY,9,0,0)));
            newMap.put("hora_fin", String.valueOf(Date.UTC(2025,02, Calendar.FEBRUARY,18,0,0)));

            if(esPrimo(count) && count != 0){
                newMap.put("businessDays","Jueves|Sabado|Domingo|Lunes");
            }
            else if (count%2 == 0){
                newMap.put("businessDays","Lunes|Martes|Jueves|Viernes");
            }
            else if(count%2 != 0){
                newMap.put("businessDays","Lunes|Martes|Miercoles|Domingo");

            }
            futureNewDocs.add(newMap);
            count++;
        }
        for (Map<String,String> itm: futureNewDocs){
            if (getMedicoByNombreApellidos(itm.get("nombre"), itm.get("apellidos")) == null){
                Medico medico = new Medico(
                        itm.get("nombre"),
                        itm.get("apellidos"),
                        itm.get("especialidad"),
                        Utils.DateFormat.asDate(itm.get("hora_inicio")),
                        Utils.DateFormat.asDate(itm.get("hora_fin")),
                        itm.get("businessDays").split("\\|")
                );

                saveMedico(medico);
            }
        }

    }

    /**
     * Retrieves a doctor by their first and last name.
     * 
     * @param nombre the first name of the doctor.
     * @param apellidos the last name of the doctor.
     * @return the Medico object, or null if not found.
     */
    private Medico getMedicoByNombreApellidos(String nombre,String apellidos){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
        Root<Medico> citasPaciente = criteriaQuery.from(Medico.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.equal(citasPaciente.get("nombre"), nombre),criteriaBuilder.equal(citasPaciente.get("apellidos"), apellidos));
        return dbSession.createQuery(criteriaQuery).getSingleResultOrNull();

    }

    /**
     * Saves a doctor to the database. This method should b at Dao, but it is here for simplicity.
     * 
     * @param medico the Medico object to be saved.
     */
    private void saveMedico(Medico medico){
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.persist(medico);
        transaction.commit();
    }

    private boolean esPrimo(int n) {
        for(int i=2;i<n;i++) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    /**
     * Changes the font of all components within a container.
     * 
     * @param contenedor the container whose components' fonts are to be changed.
     * @param fuente the new font to be applied.
     */
    private static void cambiarFuenteComponentes(Container contenedor, Font fuente) {
        for (Component componente : contenedor.getComponents()) {
            componente.setFont(fuente);
            if (componente instanceof Container container) {
                cambiarFuenteComponentes(container, fuente); // Recursividad para contenedores anidados
            }
        }
    }

    /**
     * Method to set a custom listener when the window is closed.
     * 
     */
    private WindowListener customWindowListener(){
        return new WindowListener() {
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
        };
    }


}
