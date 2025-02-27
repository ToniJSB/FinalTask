package org.example.service;

import java.util.List;

import org.example.dao.DaoMedico;
import org.example.models.Medico;
import org.hibernate.Session;

/**
 * Service class for managing Medico entities.
 * 
 * @param daoMedico the {@link org.example.dao.DaoMedico} object that manages the persistence of doctors.
 */
public class ServiceMedico {
    private DaoMedico daoMedico;

    /**
     * Constructor for ServiceMedico.
     * Instances a new {@link org.example.dao.DaoMedico} object.
     * 
     * @param session the Hibernate session to be used by the DAO.
     */
    public ServiceMedico(Session session) {
        daoMedico = new DaoMedico(session);
    }
    /**
     * get all Medico Object from database.
     * @return a list of {@link org.example.models.Medico} objects.
     */
    public List<Medico> getAllMedicos(){
        return daoMedico.askAllMedicos();
    }

    /**
     * Retrieves doctors by specialty.
     * 
     * @param especialidad the specialty of the doctor.
     * @return a list of Medico objects.
     */
    public List<Medico> getMedicosByEspecialidad(String especialidad){
        return daoMedico.askMedicosByEspecialidad(especialidad);
    }
    /**
     * Retrieves doctors by specialty and name.
     * 
     * @param especialidad the specialty of the doctor.
     * @param nombre the name of the doctor.
     * @param apellidos the surnames of the doctor.
     * @return a list of Medico objects.
     */
    public List<Medico> getMedicosByEspecialidadPlusName(String especialidad, String nombre, String apellidos){
        return daoMedico.askMedicosByEspecialidadPlusName(especialidad, nombre,apellidos);
    }

    /**
     * Retrieves doctors by name and surname.
     * 
     * @param nombre the name of the doctor.
     * @param apellidos the surname of the doctor.
     * @return a list of Medico objects.
     */
    public List<Medico> getMedicosByFullName(String nombre, String apellidos){
        return daoMedico.askMedicosByNombreApellidos(nombre,apellidos);
    }
    /**
     * Retrieves a doctor by name and surname.
     * 
     * @param nombre the name of the doctor.
     * @param apellidos the surname of the doctor.
     * @return a Medico object.
     */
    public Medico getMedicoByFullName(String nombre, String apellidos){
        return daoMedico.askMedicoByNombreApellidos(nombre,apellidos);
    }
    /**
     * Retrieves doctors by name and surname.
     * 
     * @param nombre the name of the doctor.
     * @param apellidos the surname of the doctor.
     * @return a list of Medico objects.
     */
    public List<Medico> filterMedicoByName(String nombre, String apellidos){
        return daoMedico.askMedicosByNombreApellidos(nombre,apellidos);
    }

    /**
     * Retrieves a doctor by id.
     * 
     * @param id the id of the doctor.
     * @return a Medico object.
     */    
    public Medico getMedicoById(int id){
        return daoMedico.askMedicoById(id);
    }

}
