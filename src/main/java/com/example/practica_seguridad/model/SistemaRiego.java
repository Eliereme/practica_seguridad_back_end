package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "sistemaRiego")
@AllArgsConstructor
public class SistemaRiego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSistema")
    private Long idSistema;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo")
    private String tipo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fechaInstalacion", nullable = false)
    private Date fechaInstalacion;
    @Column(name = "modoRiego")
    private String modoRiego;
    @Column(name = "configuracionRiego")
    private String configuracionRiego;
    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    public SistemaRiego(Long idSistema, String nombre, String tipo) {
        this.idSistema = idSistema;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public SistemaRiego() {

    }
}
