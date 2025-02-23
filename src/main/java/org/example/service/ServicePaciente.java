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
        Paciente paciente;
        System.out.println(password);
        System.out.println(password.isEmpty());
        System.out.println("la contraseña que recibe");
        if (password.isEmpty()){
            System.out.println("password Empty");
            paciente = new Paciente(id,nombre, apellido1, apellido2, dni, email.toLowerCase(Locale.ROOT), direccion, bdate);
        }else{
            paciente = new Paciente(id,nombre, apellido1, apellido2, dni, email.toLowerCase(Locale.ROOT), Utils.Password.encriptarPassword(password), direccion, bdate);
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
