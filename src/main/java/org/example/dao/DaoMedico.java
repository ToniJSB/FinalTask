package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.models.Cita;
import org.example.models.Medico;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DaoMedico {
    private Session dbSession;
    public DaoMedico(Session session) {
        this.dbSession = session;
    }

    public void saveMedico(Medico medico){
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.persist(medico);
        transaction.commit();
    }

    public List<Medico> askAllMedicos(){
        String hql = "FROM Medico";
        return dbSession.createQuery(hql, Medico.class)
                .getResultList();
    }
    public Medico askMedicoByNombreApellidos(String nombre,String apellidos){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
        Root<Medico> citasPaciente = criteriaQuery.from(Medico.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.equal(citasPaciente.get("nombre"), nombre),criteriaBuilder.equal(citasPaciente.get("apellidos"), apellidos));
        return dbSession.createQuery(criteriaQuery).getSingleResultOrNull();
    }

    public List<Medico> askMedicosByNombreApellidos(String nombre,String apellidos){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
        Root<Medico> citasPaciente = criteriaQuery.from(Medico.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.or(criteriaBuilder.like(citasPaciente.get("nombre"), nombre),criteriaBuilder.like(citasPaciente.get("apellidos"), apellidos)));
        return dbSession.createQuery(criteriaQuery).getResultList();
    }
    public List<Medico> askMedicosByEspecialidad(String especialidad){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
        Root<Medico> citasPaciente = criteriaQuery.from(Medico.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.like(citasPaciente.get("especialidad"), especialidad));
        return dbSession.createQuery(criteriaQuery).getResultList();
    }
    public List<Medico> askMedicosByEspecialidadPlusName(String especialidad, String nombre, String apellidos){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
        Root<Medico> medicoRoot = criteriaQuery.from(Medico.class);

        // Construir las condiciones de búsqueda
        Predicate condicionNombre = criteriaBuilder.like(medicoRoot.get("nombre"), "%" + nombre + "%");
        Predicate condicionApellidos = criteriaBuilder.like(medicoRoot.get("apellidos"), "%" + apellidos + "%");
        Predicate condicionEspecialidad = criteriaBuilder.like(medicoRoot.get("especialidad"), "%" + especialidad + "%");

        // Combinar las condiciones con AND
        Predicate condicionesCombinadas = criteriaBuilder.and(condicionNombre, condicionApellidos, condicionEspecialidad);
        criteriaQuery.select(medicoRoot).where(condicionesCombinadas);
        return dbSession.createQuery(criteriaQuery).getResultList();
    }

    public Medico askMedicoById(int id){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
        Root<Medico> medicoRoot = criteriaQuery.from(Medico.class);
        criteriaQuery.select(medicoRoot).where(criteriaBuilder.equal(medicoRoot.get("idMedico"), id));
        return dbSession.createQuery(criteriaQuery).getSingleResultOrNull();
    }
}
