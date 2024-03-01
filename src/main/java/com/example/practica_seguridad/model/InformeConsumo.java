package com.example.practica_seguridad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "detalleConsume")
@AllArgsConstructor
public class InformeConsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConsumo")
    private Long idConsumo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fechaCosumo", nullable = false)
    private Date fechaCosumo;
    @Column(name = "cantidadConsumo")
    private Double cantidadConsumo;
    @Column(name = "tiempoRiego")
    private Double tiempoRiego;
    @Column(name = "cantidadRestante")
    private Double cantidadRestante;
    @Column(name = "estadoTanque")
    private Boolean estadoTanque;

    @ManyToOne
    @JoinColumn(name = "idDeposito")
    private DepositoAgua depositoAgua;

    public InformeConsumo() {

    }

    public InformeConsumo(Long idConsumo, Double cantidadConsumo) {
        this.idConsumo = idConsumo;
        this.cantidadConsumo = cantidadConsumo;
    }
}
