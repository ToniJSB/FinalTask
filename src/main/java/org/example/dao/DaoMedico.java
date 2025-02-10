package org.example.dao;

import org.example.models.Medico;
import org.hibernate.Session;

import java.util.List;

public class DaoMedico {
    private Session session;
    public DaoMedico(Session session) {
        this.session = session;
    }

    public List<Medico> getAllMedicos(){
        String hql = "FROM Medico";
        return session.createQuery(hql, Medico.class)
                .getResultList();
    }
    public List<Medico> getMedicosByEspecialidad(String especialidad){
        String hql = "FROM Medico";
        return session.createQuery(hql, Medico.class)
                .setParameter("especialidad", especialidad)
                .getResultList();
    }
}
