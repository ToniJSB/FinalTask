package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.Utils;
import org.example.models.Cita;
import org.example.models.Medico;
import org.example.models.Paciente;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DaoCita {
    private Session dbSession;
    public DaoCita(Session session) {
        dbSession = session;
    }
    public Session getDbSession(){
        return dbSession;
    }

    public List<Cita> getCitasByDay(Date date){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Cita> criteriaQuery = criteriaBuilder.createQuery(Cita.class);
        Root<Cita> citasExistentes = criteriaQuery.from(Cita.class);
        criteriaQuery.select(citasExistentes).where(criteriaBuilder.equal(citasExistentes.get("fechaCita"), date));
        return dbSession.createQuery(criteriaQuery).getResultList();
    }
    public List<Cita> getCitasByDayWithDoctor(Date date, Medico medico){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Cita> criteriaQuery = criteriaBuilder.createQuery(Cita.class);
        Root<Cita> citasExistentes = criteriaQuery.from(Cita.class);
        criteriaQuery.select(citasExistentes)
                .where(
                    criteriaBuilder.and(
                        criteriaBuilder.equal(citasExistentes.get("fechaCita"), date),
                        criteriaBuilder.equal(citasExistentes.get("medico"), medico)));
        return dbSession.createQuery(criteriaQuery).getResultList();
    }

    public List<Cita> getCitasFromPaciente(Paciente paciente){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Cita> criteriaQuery = criteriaBuilder.createQuery(Cita.class);
        Root<Cita> citasPaciente = criteriaQuery.from(Cita.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.equal(citasPaciente.get("paciente"), paciente));
        return dbSession.createQuery(criteriaQuery).getResultList();
    }

    public void saveCita(Cita cita){
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.persist(cita);
        transaction.commit();
    }

    public void update(Cita cita) {
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.merge(cita);
        transaction.commit();
    }


}
