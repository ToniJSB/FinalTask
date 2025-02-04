package org.example.models;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "Cita")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cita")
    private Long idCita;

    @ManyToOne
    @JoinColumn(name = "ID_Paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "ID_Medico")
    private Medico medico;

    @Column(name = "Fecha_Cita")
    @Temporal(TemporalType.DATE)
    private Date fechaCita;

    @Column(name = "Hora_Cita")
    @Temporal(TemporalType.TIME)
    private Date horaCita;

    @Column(name = "Estado")
    private String estado;  // Podría usarse un Enum

    public Cita(Paciente paciente, Medico medico, Date fechaCita, Date horaCita, String estado) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.estado = estado;
    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Date getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(Date horaCita) {
        this.horaCita = horaCita;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}