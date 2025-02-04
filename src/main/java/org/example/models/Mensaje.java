package org.example.models;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Mensaje")
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Mensaje")
    private Long idMensaje;

    @ManyToOne
    @JoinColumn(name = "ID_Paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "ID_Medico")
    private Medico medico;

    @Column(name = "Fecha_Mensaje")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMensaje;

    @Column(name = "Contenido", columnDefinition = "TEXT")
    private String contenido;

    public Mensaje(Paciente paciente, Medico medico, Date fechaMensaje, String contenido) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaMensaje = fechaMensaje;
        this.contenido = contenido;
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

    public Date getFechaMensaje() {
        return fechaMensaje;
    }

    public void setFechaMensaje(Date fechaMensaje) {
        this.fechaMensaje = fechaMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
