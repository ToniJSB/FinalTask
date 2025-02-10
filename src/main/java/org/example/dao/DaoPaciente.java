package org.example.dao;

import org.example.models.Paciente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.*;

public class DaoPaciente {
    private Session dbSession;
    public DaoPaciente(Session session) {
        dbSession = session;
    }

    public boolean logIn(String email, String password){
        return false;
    }

    public void savePaciente(Paciente paciente){
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.persist(paciente);
        transaction.commit();
    }
}
