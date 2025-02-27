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

    /**
     * Logs in a user with the given email and password.
     * @param email the email of the user
     * @param password the password of the user
     * @return true if login is successful, false otherwise
     */
    public boolean logIn(String email, String password){
        return false;
    }

    /**
     * Retrieves a Paciente by email or DNI.
     * @param email the email or DNI of the Paciente
     * @return the Paciente object if found, null otherwise
     */
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

    /**
     * Retrieves a Paciente by ID.
     * @param id the ID of the Paciente
     * @return the Paciente object if found
     */
    public Paciente getPacienteById(int id){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Paciente> criteriaQuery = criteriaBuilder.createQuery(Paciente.class);
        Root<Paciente> paciente = criteriaQuery.from(Paciente.class);
        criteriaQuery.select(paciente).where(criteriaBuilder.equal(paciente.get("id"), id));
        return dbSession.createQuery(criteriaQuery).getSingleResult();
    }

    /**
     * Saves a new Paciente to the database.
     * @param paciente the Paciente object to be saved
     */
    public void savePaciente(Paciente paciente){
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.persist(paciente);
        transaction.commit();
    }

    /**
     * Updates an existing Paciente in the database.
     * @param paciente the Paciente object to be updated
     */
    public void updatePaciente(Paciente paciente){
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.merge(paciente);
        transaction.commit();
    }

}
