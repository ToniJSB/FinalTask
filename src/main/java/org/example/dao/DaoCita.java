package org.example.dao;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.example.models.Cita;
import org.example.models.EstadoCita;
import org.example.models.Medico;
import org.example.models.Paciente;
import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class DaoCita {
    private Session dbSession;
    public DaoCita(Session session) {
        dbSession = session;
    }

    /**
     * Retrieves the database session.
     * @return the current database session
     */
    public Session getDbSession(){
        return dbSession;
    }

    /**
     * Retrieves a list of Citas for a given day.
     * @param date the date of the Citas
     * @return a list of Cita objects
     */
    public List<Cita> getCitasByDay(Date date){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Cita> criteriaQuery = criteriaBuilder.createQuery(Cita.class);
        Root<Cita> citasExistentes = criteriaQuery.from(Cita.class);
        criteriaQuery.select(citasExistentes).where(criteriaBuilder.equal(citasExistentes.get("fechaCita"), date));
        return dbSession.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Retrieves a list of Citas for a given day with a specific doctor available.
     * @param date the date of the Citas
     * @param medico the Medico whose availability is to be checked
     * @return a list of Cita objects
     */
    public List<Cita> getCitasByDayWithDoctorAvailable(Date date, Medico medico){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Cita> criteriaQuery = criteriaBuilder.createQuery(Cita.class);
        Root<Cita> citasExistentes = criteriaQuery.from(Cita.class);
        criteriaQuery.select(citasExistentes)
                .where(
                    criteriaBuilder.and(
                        criteriaBuilder.equal(citasExistentes.get("fechaCita"), date),
                        criteriaBuilder.equal(citasExistentes.get("medico"), medico),
                            criteriaBuilder.or(
                                    criteriaBuilder.equal(citasExistentes.get("estado"),EstadoCita.REPROGRAMADA),
                                    criteriaBuilder.equal(citasExistentes.get("estado"),EstadoCita.CANCELADA)
                            )));
        return dbSession.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Retrieves a Cita by date, time, and doctor.
     * @param date the date of the Cita
     * @param time the time of the Cita
     * @param medico the Medico for the Cita
     * @return the Cita object if found, null otherwise
     */
    public Cita getCitasByDateTimeWithDoctor(Date date, LocalTime time, Medico medico){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Cita> criteriaQuery = criteriaBuilder.createQuery(Cita.class);
        Root<Cita> citasExistentes = criteriaQuery.from(Cita.class);
        criteriaQuery.select(citasExistentes)
                .where(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(citasExistentes.get("fechaCita"), date),
                                criteriaBuilder.equal(citasExistentes.get("horaCita"), time),
                                criteriaBuilder.equal(citasExistentes.get("medico"), medico)));
        return dbSession.createQuery(criteriaQuery).getSingleResultOrNull();
    }

    /**
     * Retrieves a list of Citas for a given Paciente.
     * @param paciente the Paciente whose Citas are to be retrieved
     * @return a list of Cita objects
     */
    public List<Cita> getCitasFromPaciente(Paciente paciente){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Cita> criteriaQuery = criteriaBuilder.createQuery(Cita.class);
        Root<Cita> citasPaciente = criteriaQuery.from(Cita.class);
        criteriaQuery.select(citasPaciente).orderBy(criteriaBuilder.asc(citasPaciente.get("fechaCita"))).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(
                                citasPaciente.get("paciente"), paciente))
                        ,criteriaBuilder.or(
                                criteriaBuilder.equal(
                                        citasPaciente.get("estado"), EstadoCita.PROGRAMADA),
                        criteriaBuilder.equal(
                                citasPaciente.get("estado"), EstadoCita.REPROGRAMADA)
                ));
        return dbSession.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Saves a new Cita to the database.
     * @param cita the Cita object to be saved
     */
    public void saveCita(Cita cita){
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.persist(cita);
        transaction.commit();
    }

    /**
     * Updates an existing Cita in the database.
     * @param cita the Cita object to be updated
     */
    public void update(Cita cita) {
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.merge(cita);
        transaction.commit();
    }

    /**
     * Retrieves a Cita by its ID.
     * @param id the ID of the Cita
     * @return the Cita object if found, null otherwise
     */
    public Cita getCitasById(int id) {
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Cita> criteriaQuery = criteriaBuilder.createQuery(Cita.class);
        Root<Cita> citasExistentes = criteriaQuery.from(Cita.class);
        criteriaQuery.select(citasExistentes).where(criteriaBuilder.equal(citasExistentes.get("idCita"), id));
        return dbSession.createQuery(criteriaQuery).getSingleResultOrNull();
    }
}
