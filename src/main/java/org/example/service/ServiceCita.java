package org.example.service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.example.components.DisplayLayout;
import org.example.dao.DaoCita;
import org.example.models.Cita;
import org.example.models.Medico;
import org.example.models.Paciente;
import org.example.models.TipoCita;
import org.hibernate.Session;
 
/**
 * Service class for managing Cita entities.
 * 
 * @param daoCita the DaoCita object that manages the persistence of appointments.
 */
public class ServiceCita {
    
    private DaoCita daoCita;

    /**
     * Constructor for ServiceCita.
     * Instances a new DaoCita object.
     * 
     * @param session the Hibernate session to be used by the DAO.
     */
    public ServiceCita(Session session) {
        daoCita = new DaoCita(session);
    }

    /**
     * Retrieves appointments for a specific day.
     * 
     * @param date the date for which appointments are to be retrieved.
     * @return a list of Cita objects.
     */
    public List<Cita> askCitasByDay(Date date) {
        return daoCita.getCitasByDay(date);
    }

    /**
     * Retrieves appointments for a specific day with a specific doctor available.
     * 
     * @param date the date for which appointments are to be retrieved.
     * @param medico the doctor whose availability is to be checked.
     * @return a list of Cita objects.
     */
    public List<Cita> askCitasByDayWithMedicoAllowed(Date date, Medico medico) {
        return daoCita.getCitasByDayWithDoctorAvailable(date, medico);
    }

    /**
     * Retrieves appointments for a specific patient.
     * 
     * @param paciente the patient whose appointments are to be retrieved.
     * @return a list of Cita objects.
     */
    public List<Cita> askCitasByPaciente(Paciente paciente) {
        return daoCita.getCitasFromPaciente(paciente);
    }

    /**
     * Retrieves an appointment by date, time, and doctor.
     * 
     * @param date the date of the appointment.
     * @param localTime the time of the appointment.
     * @param medico the doctor for the appointment.
     * @return the Cita object.
     */
    public Cita askCitaByDateTimeMedico(Date date, LocalTime localTime, Medico medico){
        return daoCita.getCitasByDateTimeWithDoctor(date,localTime,medico);
    }

    /**
     * Creates a new appointment.
     * 
     * @param localDate the date of the appointment.
     * @param medico the doctor for the appointment.
     * @param localTime the time of the appointment.
     * @param tipoCita the type of the appointment.
     */
    public void createCita(Date localDate, Medico medico, LocalTime localTime, TipoCita tipoCita){
        daoCita.saveCita(new Cita(DisplayLayout.pacienteSession,medico, localDate, localTime, tipoCita));
    }

    /**
     * Retrieves the current Hibernate session.
     * 
     * @return the current Hibernate session.
     */
    public Session getSession(){
        return daoCita.getDbSession();
    }

    /**
     * Retrieves an appointment by its ID.
     * 
     * @param id the ID of the appointment.
     * @return the Cita object.
     */
    public Cita askCitaById(int id){
        return daoCita.getCitasById(id);
    }

    /**
     * Updates an existing appointment.
     * 
     * @param cita the appointment to be updated.
     */
    public void updateCita(Cita cita) {
        daoCita.update(cita);
    }
}
