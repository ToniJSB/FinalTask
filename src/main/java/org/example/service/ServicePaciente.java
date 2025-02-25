package org.example.service;

import org.example.Utils;
import org.example.dao.DaoPaciente;
import org.example.models.Paciente;
import org.hibernate.Session;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Locale;

public class ServicePaciente {
    private DaoPaciente daoPaciente;
    public ServicePaciente(Session session) {
        daoPaciente = new DaoPaciente(session);
    }

    public boolean createPaciente(String nombre, String apellido1, String apellido2, String dni, String email, String password, String direccion, String telefono, LocalDate bdate){
        Paciente paciente = new Paciente(0,nombre, apellido1, apellido2, dni, email.toLowerCase(Locale.ROOT), password, direccion, bdate);
        try {
            paciente.setTelefono(Integer.parseInt(telefono));
            daoPaciente.savePaciente(paciente);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public boolean updatePaciente(int id,String nombre, String apellido1, String apellido2, String dni, String email, String password, String direccion, String telefono, LocalDate bdate){
        // TODO comprobar por que se actualiza la contraseña aunque se envíe vacía.
        // TODO posible solucion temporal "recoger el hash de db para aplicarlo y no poner vaciuo"
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
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public void getPacienteByDni(){
    }

    public Paciente getPacienteByEmail(String email){
        return daoPaciente.getPacienteByEmail(email);

    }

    public boolean isValidPassowrd(String pwd, String hash){
        return Utils.Password.verificarPassword(pwd,hash);
    }

}
