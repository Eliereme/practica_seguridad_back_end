package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.Cosecha;
import com.example.practica_seguridad.model.Cultivo;
import com.example.practica_seguridad.model.ZonaRiego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CosechaRepository extends JpaRepository<Cosecha,Integer> {
    Cosecha findOneByDescripcion(String nombre);
    List<Cosecha> findByZonaRiego(ZonaRiego zonaRiego);
    List<Cosecha> findByCultivo(Cultivo cultivo);
    List<Cosecha> findByDescripcionContainingIgnoreCase(String tipoCultivo);
}
