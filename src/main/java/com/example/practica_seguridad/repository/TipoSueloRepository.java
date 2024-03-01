package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.TipoSuelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoSueloRepository extends JpaRepository<TipoSuelo,Integer> {
    TipoSuelo findByTipoSuelo(String tipoSuelo);
    List<TipoSuelo> findByTipoSueloContainingIgnoreCase(String tipoSuelo);
}
