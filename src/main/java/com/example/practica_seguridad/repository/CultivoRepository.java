package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CultivoRepository extends JpaRepository<Cultivo,Integer> {
    Cultivo findByNombreCultivo(String cultivo);
    Cultivo findByIdCultivo(Integer idCultivo);
    List<Cultivo> findByNombreCultivoContainingIgnoreCase(String tipoCultivo);
}
