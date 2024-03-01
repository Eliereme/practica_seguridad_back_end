package com.example.practica_seguridad.repository;


import com.example.practica_seguridad.model.DetalleSensor;
import com.example.practica_seguridad.model.ZonaRiego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleSensorRepository extends JpaRepository<DetalleSensor, Integer> {
    List<DetalleSensor> findByZona(ZonaRiego zonaRiego);
}
