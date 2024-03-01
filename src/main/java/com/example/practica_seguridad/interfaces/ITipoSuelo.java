package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.TipoSuelo;

import java.util.List;

public interface ITipoSuelo {
    TipoSuelo create(TipoSuelo tipoSuelo);
    TipoSuelo update(TipoSuelo tipoSuelo);
    TipoSuelo findById(Integer idTipoSuelo);
    List<TipoSuelo> findAll();
    void delete(Integer idTipoSuelo);
}
