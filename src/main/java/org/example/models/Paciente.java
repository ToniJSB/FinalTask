package org.example.models;

import jakarta.persistence.*;

import javax.crypto.spec.OAEPParameterSpec;

@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Paciente")
    private int idPaciente;


    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido1")
    private String apellido1;

    @Column(name = "Apellido2")
    private String apellido2;

    @Column(name = "DNI",length = 9)
    private String dni;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "Telefono")
    private int telefono;


    public Paciente(){}
    public Paciente(String nombre, String apellido1, String apellido2, String dni, String email, String password, String direccion) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
//        this.telefono = telefono;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
