package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.Enfermedad;

import java.util.List;

public interface IEnfermedad {
    Enfermedad create(Enfermedad enfermedad);

    Enfermedad update(Enfermedad enfermedad);

    Enfermedad findById(Integer idEnfermedad);

    List<Enfermedad> findAll();

    void delete(Integer idEnfermedad);
}
