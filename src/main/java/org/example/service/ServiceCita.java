package org.example.service;

import org.example.components.DisplayLayout;
import org.example.dao.DaoCita;
import org.example.dao.DaoPaciente;
import org.example.models.Cita;
import org.example.models.Medico;
import org.example.models.Paciente;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class ServiceCita {
    private DaoCita daoCita;
    public ServiceCita(Session session) {
        daoCita = new DaoCita(session);
    }

    public List<Cita> askCitasByDay(Date date) {
        return daoCita.getCitasByDay(date);
    }
    public List<Cita> askCitasByDayWithMedico(Date date, Medico medico) {
        return daoCita.getCitasByDayWithDoctor(date, medico);
    }
    public List<Cita> askCitasByPaciente(Paciente paciente) {
        return daoCita.getCitasFromPaciente(paciente);
    }

    public void createCita(Date localDate, Medico medico, LocalTime localTime){
        daoCita.saveCita(new Cita(DisplayLayout.pacienteSession,medico, localDate,localTime));
    }

    public Session getSession(){
        return daoCita.getDbSession();
    }


    // Actualizar una cita existente
    public void actualizarCita(Cita cita) {
        daoCita.update(cita);
    }



}
