package org.example.dao;

import java.util.List;

import org.example.models.Medico;
import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Data Access Object class for managing Medico entities.
 * 
 * @param dbSession the Hibernate session to be used by the DAO.
 */
public class DaoMedico {
    private Session dbSession;
    /**
     * Constructor for DaoMedico.
     * 
     * @param session the Hibernate session to be used by the DAO.
     */
    public DaoMedico(Session session) {
        this.dbSession = session;
    }

    /**
     * Saves a doctor to the database.
     * 
     * @param medico the doctor to be saved.
     */
    public void saveMedico(Medico medico){
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.persist(medico);
        transaction.commit();
    }
    /**
     * Get all Medico Object from database.
     * 
     */
    public List<Medico> askAllMedicos(){
        String hql = "FROM Medico";
        return dbSession.createQuery(hql, Medico.class)
                .getResultList();
    }

    /**
     * Retrieves doctor by name and surname.
     * 
     * @param nombre the name of the doctor.
     * @param apellidos the surname of the doctor.
     * @return a Medico objects.
     */
    public Medico askMedicoByNombreApellidos(String nombre,String apellidos){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
        Root<Medico> citasPaciente = criteriaQuery.from(Medico.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.equal(citasPaciente.get("nombre"), nombre),criteriaBuilder.equal(citasPaciente.get("apellidos"), apellidos));
        return dbSession.createQuery(criteriaQuery).getSingleResultOrNull();
    }

    /**
     * Retrieves doctors by name and surname. 
     * 
     * @param nombre the name of the doctor.
     * @return a list of Medico objects.
     */
    public List<Medico> askMedicosByNombreApellidos(String nombre,String apellidos){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
        Root<Medico> citasPaciente = criteriaQuery.from(Medico.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.or(criteriaBuilder.like(citasPaciente.get("nombre"), nombre),criteriaBuilder.like(citasPaciente.get("apellidos"), apellidos)));
        return dbSession.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Retrieves doctors by specialty.
     * 
     * @param especialidad the specialty of the doctor.
     * @return a list of Medico objects.
     */
    public List<Medico> askMedicosByEspecialidad(String especialidad){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
        Root<Medico> citasPaciente = criteriaQuery.from(Medico.class);
        criteriaQuery.select(citasPaciente).where(criteriaBuilder.like(citasPaciente.get("especialidad"), especialidad));
        return dbSession.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Retrieves doctors by specialty, name, and surname.
     * 
     * @param especialidad the specialty of the doctor.
     * @param nombre the name of the doctor.
     * @param apellidos the surname of the doctor.
     * @return a list of Medico objects.
     */
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

    /**
     * Retrieves doctor by ID.
     * 
     * @param id the ID of the doctor.
     * @return a Medico object.
     */
    public Medico askMedicoById(int id){
        CriteriaBuilder criteriaBuilder = dbSession.getCriteriaBuilder();
        CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
        Root<Medico> medicoRoot = criteriaQuery.from(Medico.class);
        criteriaQuery.select(medicoRoot).where(criteriaBuilder.equal(medicoRoot.get("idMedico"), id));
        return dbSession.createQuery(criteriaQuery).getSingleResultOrNull();
    }
}
