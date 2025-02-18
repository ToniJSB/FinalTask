package org.example.service;

import org.example.components.DisplayLayout;
import org.example.dao.DaoCita;
import org.example.dao.DaoPaciente;
import org.example.models.Cita;
import org.example.models.Medico;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ServiceCita {
    private DaoCita daoCita;
    private DaoPaciente daoPaciente;
    public ServiceCita(Session session) {
        daoPaciente = new DaoPaciente(session);
        daoCita = new DaoCita(session);
    }

    public List<Cita> askCitasByDay(Date date) {
        return daoCita.getCitasByDay(date);
    }

    public void createCita(Date localDate, Medico medico){
        daoCita.saveCita(new Cita(DisplayLayout.pacienteSession,medico, localDate,localDate));
    }

    public Session getSession(){
        return daoCita.getDbSession();
    }


    // Actualizar una cita existente
    public void actualizarCita(Cita cita) {
        daoCita.update(cita);
    }



}
