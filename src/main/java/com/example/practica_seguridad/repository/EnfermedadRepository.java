package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.Enfermedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnfermedadRepository extends JpaRepository<Enfermedad, Integer> {
    Enfermedad findByNombreEnfermedad(String nombreEnfermedad);

    List<Enfermedad> findByNombreEnfermedadContainingIgnoreCase(String nombreEnfermedad);

    @Query("Select e from Enfermedad e where e.idEnfermedad In :detalleEnfermedades")
    List<Enfermedad> findByDetalleEnfermedadesIn(@Param("detalleEnfermedades") List<Integer> detalleEnfermedades);

}
