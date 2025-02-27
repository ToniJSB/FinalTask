package org.example.dao;

import org.example.models.Paciente;
import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class DaoPaciente {
    private Session dbSession;
    public DaoPaciente(Session session) {
        dbSession = session;
    }

    public boolean logIn(String email, String password){
        return false;
    }

    public Paciente askPacienteByEmailOrDNI(String email){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Paciente> criteriaQuery = criteriaBuilder.createQuery(Paciente.class);
        Root<Paciente> paciente = criteriaQuery.from(Paciente.class);
        criteriaQuery.select(paciente).where(criteriaBuilder.or(
                criteriaBuilder.equal(paciente.get("email"), email),
                criteriaBuilder.equal(paciente.get("dni"), email)
        ));
        return dbSession.createQuery(criteriaQuery).getSingleResultOrNull();
    }
    public Paciente getPacienteById(int id){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Paciente> criteriaQuery = criteriaBuilder.createQuery(Paciente.class);
        Root<Paciente> paciente = criteriaQuery.from(Paciente.class);
        criteriaQuery.select(paciente).where(criteriaBuilder.equal(paciente.get("id"), id));
        return dbSession.createQuery(criteriaQuery).getSingleResult();
    }

    public void savePaciente(Paciente paciente){
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.persist(paciente);
        transaction.commit();
    }
    public void updatePaciente(Paciente paciente){
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.merge(paciente);
        transaction.commit();
    }

}
