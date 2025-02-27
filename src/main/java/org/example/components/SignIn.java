package org.example.components;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.example.Utils;
import org.example.models.Paciente;
import org.example.service.ServicePaciente;
import org.hibernate.Session;

/**
 * The SignIn class represents a panel for user sign-in and registration.
 */
public class SignIn extends JPanel {
    private JPanel container;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido1;
    private JTextField txtApellido2;
    private JTextField txtDni;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtBirdthDayDate;
    private CalendarPanel calendarPanel;

    private JButton btnGuardar;
    private JButton btnCancelar;
    private ServicePaciente servicePaciente;

    private CardLayout initLayout;
    private JPanel mainPanel;

    private Paciente userSession;

    /**
     * Constructs a SignIn panel with the specified main panel, layout, and session.
     *
     * @param mainPanel the main panel
     * @param initLayout the initial layout
     * @param session the Hibernate session
     */
    public SignIn(JPanel mainPanel, CardLayout initLayout, Session session) {
        servicePaciente = new ServicePaciente(session);
        this.mainPanel = mainPanel;
        this.initLayout = initLayout;
        initComponents();
        setupLayout();
        setupDocumentFilters();
    }

    /**
     * Constructs a SignIn panel with the specified session.
     *
     * @param session the Hibernate session
     */
    public SignIn(Session session) {
        servicePaciente = new ServicePaciente(session);
        userSession = DisplayLayout.pacienteSession;
        initComponents();
        setupLayout();
        setupDocumentFilters();
    }

    /**
     * Initializes the components of the SignIn panel.
     */
    private void initComponents() {
        container = new JPanel();
        txtId = new JTextField(10);
        txtId.setEditable(false);
        txtNombre = new JTextField(20);
        txtApellido1 = new JTextField(20);
        txtApellido2 = new JTextField(20);
        txtDni = new JTextField(9);
        txtEmail = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtConfirmPassword = new JPasswordField(20);
        txtDireccion = new JTextField(20);
        txtTelefono = new JTextField(9);
        txtBirdthDayDate = new JTextField(10);
        if(DisplayLayout.pacienteSession!=null){
            userSession =DisplayLayout.pacienteSession;
            txtId.setText(String.valueOf(userSession.getIdPaciente()));
            txtNombre.setText(userSession.getNombre());
            txtApellido1.setText(userSession.getApellido1());
            txtApellido2.setText(userSession.getApellido2());
            txtDni.setText(userSession.getDni());
            txtDni.setEditable(false);
            txtEmail.setText(userSession.getEmail());
            txtDireccion.setText(userSession.getDireccion());
            txtTelefono.setText(String.valueOf(userSession.getTelefono()));
            txtBirdthDayDate.setText(Utils.DateFormat.dateAsStringDateF(Utils.DateFormat.asDate(userSession.getbDate())));

            txtBirdthDayDate.setEditable(false);
        }

        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(this::guardarAction);
        if (mainPanel != null){
            btnCancelar = new JButton("Cancelar");
            btnCancelar.addActionListener(this::cancelarAction);
        }

    }

    /**
     * Sets up the layout of the SignIn panel.
     */
    private void setupLayout() {
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        addField(gbc, row++, "ID Paciente:", txtId);
        addField(gbc, row++, "Nombre*:", txtNombre);
        addField(gbc, row++, "Primer Apellido*:", txtApellido1);
        addField(gbc, row++, "Segundo Apellido:", txtApellido2);
        addField(gbc, row++, "DNI* (9 caracteres):", txtDni);
        addField(gbc, row++, "Email*:", txtEmail);
        addField(gbc, row++, "Contraseña*:", txtPassword);
        addField(gbc, row++, "Confirmar contraseña*:", txtConfirmPassword);
        addField(gbc, row++, "Dirección:", txtDireccion);
        addField(gbc, row++, "Teléfono:", txtTelefono);
        addField(gbc, row++, "Fecha de nacimiento:", txtBirdthDayDate);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.add(btnGuardar);
        if (mainPanel != null){
            buttonPanel.add(btnCancelar);
        }
        container.add(buttonPanel, gbc);
        add(container);
    }

    /**
     * Adds a field to the specified GridBagConstraints.
     *
     * @param gbc the GridBagConstraints
     * @param row the row index
     * @param label the label text
     * @param field the field component
     */
    private void addField(GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        container.add(new JLabel(label), gbc);


        if (!label.equals("Fecha de nacimiento:")){
            gbc.gridx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            container.add(field, gbc);
        }else{
            JPanel calendarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton openCalendar = new JButton("Abrir calendario");
            if(userSession != null){
                openCalendar.setEnabled(false);
            }
            openCalendar.addActionListener(e -> new CalendarPanel((JTextField) field));
            calendarContainer.add(field);
            calendarContainer.add(openCalendar);
            gbc.gridx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            container.add(calendarContainer, gbc);

        }

    }

    /**
     * Sets up document filters for input validation.
     */
    private void setupDocumentFilters() {
        // Filtro para DNI (max 9 caracteres)
        ((AbstractDocument) txtDni.getDocument()).setDocumentFilter(new LengthDocumentFilter(9));

        // Filtro para teléfono (solo números)
        ((AbstractDocument) txtTelefono.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        // Filtro para teléfono (solo fechas válidas)
        ((AbstractDocument) txtBirdthDayDate.getDocument()).setDocumentFilter(new DateDocumentFilter());
    }

    /**
     * Handles the action for the "Guardar" button.
     *
     * @param e the ActionEvent
     */
    private void guardarAction(ActionEvent e) {
        if (validarCampos()) {
            String[] bDateSplit = txtBirdthDayDate.getText().split("/");
            boolean created;
            if (userSession==null){
                created = servicePaciente.createPaciente(
                        txtNombre.getText(),
                        txtApellido1.getText(),
                        txtApellido2.getText(),
                        txtDni.getText(),
                        txtEmail.getText(),
                        Utils.Password.encriptarPassword(new String(txtPassword.getPassword())),
                        txtDireccion.getText(),
                        txtTelefono.getText(),
                        LocalDate.of(
                                Integer.parseInt(bDateSplit[2]),
                                Integer.parseInt(bDateSplit[1]),
                                Integer.parseInt(bDateSplit[0]))
                        );
            }else{
                created = servicePaciente.updatePaciente(
                        Integer.parseInt(txtId.getText()),
                        txtNombre.getText(),
                        txtApellido1.getText(),
                        txtApellido2.getText(),
                        txtDni.getText(),
                        txtEmail.getText(),
                        new String(txtPassword.getPassword()),
                        txtDireccion.getText(),
                        txtTelefono.getText(),
                        LocalDate.of(
                                Integer.parseInt(bDateSplit[2]),
                                Integer.parseInt(bDateSplit[1]),
                                Integer.parseInt(bDateSplit[0]))
                );
            }
            if (created){
                JOptionPane.showMessageDialog(this, "Paciente guardado:\n" + txtNombre.getText());
                if (mainPanel!=null){
                    initLayout.show(mainPanel,"LOGIN");
                }else{
                    repaint();
                    revalidate();
                }
            }else{
                JOptionPane.showMessageDialog(this, "Algún campo es inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Aquí iría la lógica para guardar en la base de datos
    }

    /**
     * Handles the action for the "Cancelar" button.
     *
     * @param e the ActionEvent
     */
    private void cancelarAction(ActionEvent e) {
        limpiarCampos();
        initLayout.show(mainPanel,"LOGIN");
    }

    /**
     * Validates the input fields.
     *
     * @return true if the fields are valid, false otherwise
     */
    private boolean validarCampos() {
        boolean result = true;
        String mensaje = "";
        if (userSession == null){
            if (txtNombre.getText().isBlank() ||
                    txtApellido1.getText().isBlank() ||
                    txtDni.getText().isBlank() ||
                    txtEmail.getText().isBlank() ||
                    txtPassword.getPassword().length == 0 || txtConfirmPassword.getPassword().length == 0) {

                mensaje += "Los campos marcados con * son obligatorios.\n";
                result =  false;
            }
        }else {
            if (txtNombre.getText().isBlank() ||
                    txtApellido1.getText().isBlank() ||
                    txtDni.getText().isBlank() ||
                    txtEmail.getText().isBlank() ){
                mensaje += "Los campos marcados con * son obligatorios.\n";
                result =  false;
            }
        }

        if (txtDni.getText().length() != 9) {
            mensaje += "El DNI debe tener 9 caracteres.\n";
            result =  false;
        }
        if (!Utils.validarEmail(txtEmail.getText())){
            mensaje += "El email no cumple las características.\n";
            result =  false;
        }
        // Validar que las contraseñas coincidan
        if (!new String(txtConfirmPassword.getPassword()).equals(new String(txtPassword.getPassword()))) {
            mensaje = "Las contraseñas no coinciden.\n";
            result = false;
        }

        if (!Utils.DateFormat.isFullDateValid(txtBirdthDayDate.getText())) {
            mensaje = "La fecha de nacimiento no es válida o está fuera del rango permitido.\n";
            result =  false;
        }
        if (!result){
            JOptionPane.showMessageDialog(this,
                    mensaje,
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);

        }

        return result;
    }

    /**
     * Clears the input fields.
     */
    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        txtDni.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtBirdthDayDate.setText("");
    }

    /**
     * Sets the Paciente data in the input fields.
     *
     * @param paciente the Paciente object
     */
    public void setPaciente(Paciente paciente) {
        txtId.setText(String.valueOf(paciente.getIdPaciente()));
        txtNombre.setText(paciente.getNombre());
        txtApellido1.setText(paciente.getApellido1());
        txtApellido2.setText(paciente.getApellido2());
        txtDni.setText(paciente.getDni());
        txtEmail.setText(paciente.getEmail());
        txtPassword.setText(paciente.getPassword());
        txtDireccion.setText(paciente.getDireccion());
        txtTelefono.setText(String.valueOf(paciente.getTelefono()));
    }

    /**
     * Gets the Paciente data from the input fields.
     *
     * @return the Paciente object
     */
    public Paciente getPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre(txtNombre.getText());
        paciente.setApellido1(txtApellido1.getText());
        paciente.setApellido2(txtApellido2.getText());
        paciente.setDni(txtDni.getText());
        paciente.setEmail(txtEmail.getText());
        paciente.setPassword(new String(txtPassword.getPassword()));
        paciente.setDireccion(txtDireccion.getText());

        try {
            paciente.setTelefono(Integer.parseInt(txtTelefono.getText()));
        } catch (NumberFormatException e) {
            paciente.setTelefono(0);
        }

        return paciente;
    }

    /**
     * DocumentFilter to limit the length of a document.
     */
    private static class LengthDocumentFilter extends DocumentFilter {
        private final int maxLength;

        public LengthDocumentFilter(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            int currentLength = fb.getDocument().getLength();
            int newLength = currentLength - length + text.length();

            if (newLength <= maxLength) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
    /**
     * DocumentFilter to allow only numeric characters.
     */
    private static class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string.matches("\\d*")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text.matches("\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
    /**
     * DocumentFilter to allow only valid date characters.
     */
    private static class DateDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
            if (Utils.DateFormat.isPartialDateValid(newText)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (Utils.DateFormat.isFullDateValid(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}