package org.example.service;

import org.example.dao.DaoPaciente;
import org.example.models.Paciente;
import org.hibernate.Session;

import javax.swing.*;

public class ServicePaciente {
    private DaoPaciente daoPaciente;
    public ServicePaciente(Session session) {
        daoPaciente = new DaoPaciente(session);

    }

    public boolean createPaciente(String nombre, String apellido1, String apellido2, String dni, String email, String password, String direccion, String telefono){
        Paciente paciente = new Paciente(nombre, apellido1, apellido2, dni, email, password, direccion);
        try {
            paciente.setTelefono(Integer.parseInt(telefono));
            daoPaciente.savePaciente(paciente);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public void getPacienteByDni(){
    }
    public void getPacienteByEmail(){
    }
    public void getAllPaciente(){
    }
}
