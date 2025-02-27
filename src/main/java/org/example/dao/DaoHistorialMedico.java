package org.example.dao;

import java.util.List;

import org.example.models.HistorialMedico;
import org.example.models.Paciente;
import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class DaoHistorialMedico {
    private Session dbSession;
    public DaoHistorialMedico(Session session) {
        dbSession = session;
    }

    /**
     * Retrieves the medical history of a given Paciente.
     * @param paciente the Paciente whose medical history is to be retrieved
     * @return a list of HistorialMedico objects
     */
    public List<HistorialMedico> getHistorialMedico(Paciente paciente){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<HistorialMedico> criteriaQuery = criteriaBuilder.createQuery(HistorialMedico.class);
        Root<HistorialMedico> citasPaciente = criteriaQuery.from(HistorialMedico.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.equal(citasPaciente.get("paciente"), paciente));
        return dbSession.createQuery(criteriaQuery).getResultList();

    }

    /**
     * Retrieves a HistorialMedico by its ID.
     * @param id the ID of the HistorialMedico
     * @return the HistorialMedico object if found, null otherwise
     */
    public HistorialMedico getHistorialMedicoById(int id){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<HistorialMedico> criteriaQuery = criteriaBuilder.createQuery(HistorialMedico.class);
        Root<HistorialMedico> citasPaciente = criteriaQuery.from(HistorialMedico.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.equal(citasPaciente.get("idHistorial"), id));
        return dbSession.createQuery(criteriaQuery).getSingleResultOrNull();

    }
}
