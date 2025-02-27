package org.example.service;

import java.util.List;

import org.example.dao.DaoHistorialMedico;
import org.example.models.HistorialMedico;
import org.example.models.Paciente;
import org.hibernate.Session;

/**
 * Service class for managing HistorialMedico entities.
 */
public class ServiceHistorialMedico {
    private DaoHistorialMedico daoHistorialMedico;

    /**
     * Constructor for ServiceHistorialMedico.
     * Instances a new DaoHistorialMedico object.
     * 
     * @param session the Hibernate session to be used by the DAO.
     */
    public ServiceHistorialMedico(Session session) {
        daoHistorialMedico = new DaoHistorialMedico(session);
    }

    /**
     * Retrieves the medical history for a given patient.
     * 
     * @param paciente the patient whose medical history is to be retrieved.
     * @return a list of HistorialMedico objects.
     */
    public List<HistorialMedico> askHistorialMedicoFromPaciente(Paciente paciente){
        return daoHistorialMedico.getHistorialMedico(paciente);
    }

    /**
     * Retrieves a medical history record by its ID.
     * 
     * @param id the ID of the medical history record.
     * @return the HistorialMedico object with the given ID.
     */
    public HistorialMedico askHistorialMedicoById(int id){
        return daoHistorialMedico.getHistorialMedicoById(id);
    }
}
