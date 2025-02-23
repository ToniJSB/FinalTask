package org.example.service;

import org.example.dao.DaoHistorialMedico;
import org.example.models.HistorialMedico;
import org.example.models.Paciente;
import org.hibernate.Session;

import java.util.List;

public class ServiceHistorialMedico {
    private DaoHistorialMedico daoHistorialMedico;
    public ServiceHistorialMedico(Session session) {
        daoHistorialMedico = new DaoHistorialMedico(session);
    }

    public List<HistorialMedico> askHistorialMedicoFromPaciente(Paciente paciente){
        return daoHistorialMedico.getHistorialMedico(paciente);
    }
}
