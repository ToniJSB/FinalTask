package org.example.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "medico")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Medico")
    private Long idMedico;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellidos")
    private String apellidos;

    @Column(name = "Especialidad")
    private String especialidad;

    @Column(name = "hora_inicio_jornada")
    @Temporal(TemporalType.TIME)
    private Date hora_inicio;

    @Column(name = "hora_fin_jornada")
    @Temporal(TemporalType.TIME)
    private Date hora_fin;

    @Column(
            name = "dias_laborales",
            columnDefinition = "text[]"
    )
    private String[] businessDays;

    public Medico() {
    }

    public Medico(String nombre, String apellidos, String especialidad, Date hora_inicio, Date hora_fin, String[] businessDays) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.businessDays = businessDays;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Date getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Date hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public Date getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(Date hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String[] getBusinessDays() {
        return businessDays;
    }


    public void setBusinessDays(String[] businessDays) {
        this.businessDays = businessDays;
    }

    public String simpleInfo(){
        return "%s - %s %s".formatted(especialidad,nombre,apellidos);
    }
}





