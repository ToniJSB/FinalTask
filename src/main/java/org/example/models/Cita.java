package org.example.models;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Date;


@Entity
@Table(name = "cita")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cita")
    private Long idCita;

    @ManyToOne
    @JoinColumn(name = "ID_Paciente")
    private Paciente paciente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_Medico")
    private Medico medico;

    @Column(name = "fecha_cita")
    @Temporal(TemporalType.DATE)
    private Date fechaCita;

    @Column(name = "hora_cita")
    @Temporal(TemporalType.TIME)
    private LocalTime horaCita;

    @Column(name = "Estado")
    private EstadoCita estado;

    @Column(name = "Tipo")
    private TipoCita tipo;


    public Cita(Paciente paciente, Medico medico, Date fechaCita, LocalTime horaCita, TipoCita tipo) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.estado = EstadoCita.PROGRAMADA;
        this.tipo = tipo;
    }

    public Cita() {
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

    public LocalTime getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(LocalTime horaCita) {
        this.horaCita = horaCita;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public TipoCita getTipo() {
        return tipo;
    }

    public void setTipo(TipoCita tipo) {
        this.tipo = tipo;
    }

    private void programarCita() {
        this.estado = EstadoCita.PROGRAMADA;
    }

    private void reprogramarCita() {
        this.estado = EstadoCita.REPROGRAMADA;

    }

    private void cancelarCita() {
        this.estado = EstadoCita.CANCELADA;
    }

    public String getInfo(){
        return "%s - %s - %s".formatted(fechaCita, horaCita, medico.simpleInfo());
    }
}