package org.example.service;

import java.time.LocalDate;
import java.util.Locale;

import org.example.Utils;
import org.example.dao.DaoPaciente;
import org.example.models.Paciente;
import org.hibernate.Session;

//doc in english

/**
 * Class to manage the persistence of patient entities.
 * 
 * @param daoPaciente the DaoPaciente object that manages the persistence of patients.
 */
public class ServicePaciente {
    private DaoPaciente daoPaciente;
    /**
     * Constructor for ServicePaciente.
     * Instances a new DaoPaciente object.
     * 
     * @param session the Hibernate session to be used by the DAO.
     */
    public ServicePaciente(Session session) {
        daoPaciente = new DaoPaciente(session);
    }

    /**
     * Instance a Paciente object and save it in the database
     * 
     * @param nombre 
     * @param apellido1
     * @param apellido2
     * @param dni
     * @param email
     * @param password
     * @param direccion
     * @param telefono
     * @param bdate
     * @return true if the patient is created successfully, false otherwise.
     */
    public boolean createPaciente(String nombre, String apellido1, String apellido2, String dni, String email, String password, String direccion, String telefono, LocalDate bdate){
        boolean result = false;
        Paciente paciente = new Paciente(0,nombre, apellido1, apellido2, dni, email.toLowerCase(Locale.ROOT), password, direccion, bdate);

        try {
            paciente.setTelefono(Integer.parseInt(telefono));
            daoPaciente.savePaciente(paciente);
            result = true;
        } catch (NumberFormatException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Update a patient in the database
     * 
     * @param id
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param dni
     * @param email
     * @param password
     * @param direccion
     * @param telefono
     * @param bdate
     * @return true if the patient is updated successfully, false otherwise.
     */
    public boolean updatePaciente(int id,String nombre, String apellido1, String apellido2, String dni, String email, String password, String direccion, String telefono, LocalDate bdate){
        boolean result = false;
        Paciente paciente = daoPaciente.getPacienteById(id);
        paciente.setNombre(nombre);
        paciente.setApellido1(apellido1);
        paciente.setApellido2(apellido2);
        paciente.setEmail(email.toLowerCase(Locale.ROOT));
        paciente.setDireccion(direccion);
        if (!password.isEmpty()){
            paciente.setPassword(Utils.Password.encriptarPassword(password));
        }
        try {
            paciente.setTelefono(Integer.parseInt(telefono));
            daoPaciente.updatePaciente(paciente);
            result = true;
        } catch (NumberFormatException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Look for a patient by its email or DNI
     * 
     * @param textToMatch it could be an email or a DNI
     * @return the Paciente object found at the database. Null if no patient is found.
     */
    public Paciente getPacienteByEmailOrDni(String textToMatch){
        return daoPaciente.askPacienteByEmailOrDNI(textToMatch);
    }

    /**
     * Validate the password of a patient against the hash stored in the database
     * @param pwd password to validate
     * @param hash hash stored in the database
     * @return true if the password is valid, false otherwise.
     */
    public boolean isValidPassowrd(String pwd, String hash){
        return Utils.Password.verificarPassword(pwd,hash);
    }

}
