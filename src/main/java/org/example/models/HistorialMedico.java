package org.example.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "historial_medico")
public class HistorialMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Historial")
    private Long idHistorial;

    @ManyToOne
    @JoinColumn(name = "ID_Paciente")
    private Paciente paciente;

    @Column(name = "Fecha_Visita")
    @Temporal(TemporalType.DATE)
    private Date fechaVisita;

    @Column(name = "Diagnóstico", columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "Tratamiento", columnDefinition = "TEXT")
    private String tratamiento;

    public HistorialMedico() {
    }

    public HistorialMedico(Paciente paciente, Date fechaVisita, String diagnostico, String tratamiento) {
        this.paciente = paciente;
        this.fechaVisita = fechaVisita;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }

    public Long getIdHistorial() {
        return idHistorial;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
// Getters, Setters y Constructor vacío
}