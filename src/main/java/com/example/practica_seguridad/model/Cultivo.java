package com.example.practica_seguridad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Table(name = "cultivo")
@Data
@AllArgsConstructor
public class Cultivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCultivo")
    private Integer idCultivo;
    @ManyToOne
    @JoinColumn(name = "idTipoSuelo")
    private TipoSuelo tipoSuelo;
    @ManyToOne
    @JoinColumn(name = "idTipoCultivo")
    private TipoCultivo tipoCultivo;
    @Column(name = "nombreCultivo", nullable = false, length = 150)
    private String nombreCultivo;
    @Column(name = "caracteristicas", nullable = false, length = 150)
    private String caracteristicas;
    @Column(name = "distanciaSiembra", nullable = false, length = 150)
    private String distanciaSiembra;
    @Column(name = "profundidadSiembra", nullable = false, length = 150)
    private String profundidadSiembra;
    @Column(name = "germinacion", nullable = false)
    private Integer germinacion;
    @Column(name = "tiempoMadurez", nullable = false)
    private Integer tiempoMadurez;
    @Column(name = "fertilizacion", nullable = false, length = 200)
    private String fertilizacion;
    @Column(name = "frecuenciaRiego", nullable = false, length = 150)
    private String frecuenciaRiego;
    @Column(name = "poda", length = 150)
    private String poda;
    @Column(name = "imagen", length = 250)
    private String imagen;
    @Column(name = "nivelPh", nullable = false)
    private double nivelPh;
    @Column(name = "nivelFosforo", nullable = false)
    private double nivelFosforo;
    @Column(name = "nivelPotasio", nullable = false)
    private double nivelPotasio;
    @Column(name = "nivelNitrogeno", nullable = false)
    private double nivelNitrogeno;
    @Column(name = "nivelHumedadSuelo", nullable = false)
    private double nivelHumedadSuelo;
    @Column(name = "nivelTemperatura", nullable = false)
    private double nivelTemperatura;
    @Column(name = "humedadAmbiente", nullable = false)
    private double humedadAmbiente;

    public Cultivo() {

    }

    public Cultivo(Integer idCultivo, String nombreCultivo, String caracteristicas) {

    }
}
