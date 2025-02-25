package org.example.service;

import org.example.dao.DaoMedico;
import org.example.models.Medico;
import org.hibernate.Session;

import java.util.List;

public class ServiceMedico {
    private DaoMedico daoMedico;
    public ServiceMedico(Session session) {
        daoMedico = new DaoMedico(session);
    }
    public List<Medico> getAllMedicos(){
        return daoMedico.askAllMedicos();
    }

    public List<Medico> getMedicosByEspecialidad(String especialidad){
        return daoMedico.askMedicosByEspecialidad(especialidad);
    }
    public List<Medico> getMedicosByEspecialidadPlusName(String especialidad, String nombre, String apellidos){
        return daoMedico.askMedicosByEspecialidadPlusName(especialidad, nombre,apellidos);
    }
    public List<Medico> getMedicosByFullName(String nombre, String apellidos){
        return daoMedico.askMedicosByNombreApellidos(nombre,apellidos);
    }
    public Medico getMedicoByFullName(String nombre, String apellidos){
        return daoMedico.askMedicoByNombreApellidos(nombre,apellidos);
    }
    public List<Medico> filterMedicoByName(String nombre, String apellidos){
        return daoMedico.askMedicosByNombreApellidos(nombre,apellidos);
    }
    public Medico getMedicoById(int id){
        return daoMedico.askMedicoById(id);
    }

}
