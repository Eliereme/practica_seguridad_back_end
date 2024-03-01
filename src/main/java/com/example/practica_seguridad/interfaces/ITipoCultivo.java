package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.TipoCultivo;

import java.util.List;

public interface ITipoCultivo {
    TipoCultivo create(TipoCultivo tipoCultivo);
    TipoCultivo update(TipoCultivo tipoCultivo);
    TipoCultivo findById(Integer idTipoCultivo);
    List<TipoCultivo> findAll();
    void delete(Integer idTipoCultivo);
}
