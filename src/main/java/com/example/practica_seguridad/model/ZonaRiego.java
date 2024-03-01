package com.example.practica_seguridad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Table(name = "zonaRiego")
@Data
@AllArgsConstructor
public class ZonaRiego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idZona")
    private Long idZona;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "nombre",nullable = false, length = 50)
    private String nombreZona;
    @Column(name = "largoZona")
    private double largoZona;
    @Column(name = "anchoZona")
    private double anchoZona;
    @Column(name = "longitudMapa")
    private double longitudMapa;
    @Column(name = "latitudMapa")
    private double latitudMapa;
    @Column(name = "imagen",length = 250)
    private String imagen;
    @Column(name = "direccionMAC",length = 50, nullable = false)
    private String direccionMAC;
    @Column(name = "metodoCosecha",length = 150, nullable = false)
    private String metodoCosecha;
    @Column(name = "cantidadSemillas")
    private Integer cantidadSemillas;
    @Column(name = "ultimaTemaperatura")
    private Float ultimaTemaperatura;
    @Column(name = "ultimaHumedadAmbiente")
    private Float ultimaHumedadAmbiente;
    @Column(name = "ultimaHumedadSuelo")
    private Float ultimaHumedadSuelo;
    @Column(name = "ultimanitrogeno")
    private Float ultimaNitrogeno;
    @Column(name = "ultimafosforo")
    private Float ultimaFosforo;
    @Column(name = "ultimapotasio")
    private Float ultimaPotasio;
    //datos para los nuevos registro para el riego
    @Column(name = "recomendacionTemaperatura")
    private Float recomendacionTemaperatura;
    @Column(name = "recomendacionHumedadAmbiente")
    private Float recomendacionHumedadAmbiente;
    @Column(name = "recomendacionHumedadSuelo")
    private Float recomendacionHumedadSuelo;
    @Column(name = "recomendacionNitrogeno")
    private Float recomendacionNitrogeno;
    @Column(name = "recomendacionFosforo")
    private Float recomendacionFosforo;
    @Column(name = "recomendacionPotasio")
    private Float recomendacionPotasio;
    @Column(name = "aplicarNutrientes")
    private boolean aplicarNutrientes;
    @Column(name = "aplicarAgua")
    private boolean aplicarAgua;
    @ManyToOne
    @JoinColumn(name = "idSistema")
    private SistemaRiego sistemaRiego;
    @ManyToOne
    @JoinColumn(name = "idTipoSuelo")
    private TipoSuelo tipoSuelo;

    public ZonaRiego() {

    }

    public ZonaRiego(Long idZona, String nombreZona) {
        this.idZona = idZona;
        this.nombreZona = nombreZona;
    }

    public ZonaRiego(Long idZona) {
        this.idZona = idZona;
    }
}
