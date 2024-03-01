package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.Cultivo;
import com.example.practica_seguridad.model.DetalleEnfermedad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleEnfermedades extends JpaRepository<DetalleEnfermedad,Integer> {
    List<DetalleEnfermedad> findByCultivo(Cultivo cultivo);
}
