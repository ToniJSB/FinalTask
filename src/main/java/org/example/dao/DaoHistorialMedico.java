package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.models.HistorialMedico;
import org.example.models.Paciente;
import org.hibernate.Session;

import java.util.List;

public class DaoHistorialMedico {
    private Session dbSession;
    public DaoHistorialMedico(Session session) {
        dbSession = session;
    }
    public List<HistorialMedico> getHistorialMedico(Paciente paciente){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<HistorialMedico> criteriaQuery = criteriaBuilder.createQuery(HistorialMedico.class);
        Root<HistorialMedico> citasPaciente = criteriaQuery.from(HistorialMedico.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.equal(citasPaciente.get("paciente"), paciente));
        return dbSession.createQuery(criteriaQuery).getResultList();

    }
}
