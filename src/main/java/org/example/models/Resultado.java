package org.example.models;
import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "resultados")
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Resultado")
    private Long idResultado;

    @ManyToOne
    @JoinColumn(name = "ID_Paciente")
    private Paciente paciente;

    @Column(name = "Tipo_Resultado")
    private String tipoResultado;

    @Column(name = "Descripción", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Lob
    @Column(name = "Archivo_PDF")
    private byte[] archivoPDF;

    // Getters, Setters y Constructor vacío
}