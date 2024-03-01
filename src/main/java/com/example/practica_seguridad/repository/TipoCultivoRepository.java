package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.TipoCultivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoCultivoRepository extends JpaRepository<TipoCultivo,Integer> {
    TipoCultivo findByTipoCultivo(String tipoCultivo);
    List<TipoCultivo> findByTipoCultivoContainingIgnoreCase(String tipoCultivo);

}
