package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.Cultivo;
import com.example.practica_seguridad.model.EnfermedadCultivo;

import java.util.List;

public interface ICultivo {
    Cultivo create(EnfermedadCultivo cultivo);

    Cultivo update(EnfermedadCultivo cultivo);

    Cultivo findById(Integer idCultivo);

    List<Cultivo> findAll();

    void delete(Integer idCultivo);
}
