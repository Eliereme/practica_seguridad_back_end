package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.Cosecha;
import com.example.practica_seguridad.model.SistemaRiego;
import com.example.practica_seguridad.model.Usuario;

import java.util.List;

public interface ICosechaService {
    Cosecha findByName(String name);
    Cosecha create(Cosecha datosCosecha);
    Cosecha update(Cosecha cosecha);
    Cosecha findById(Integer idCosecha);
    List<Cosecha> findAll();
    void delete(Integer idCosecha);
}
