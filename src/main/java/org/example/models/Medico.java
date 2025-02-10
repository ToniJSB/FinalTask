package org.example.models;

import jakarta.persistence.*;

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

}





