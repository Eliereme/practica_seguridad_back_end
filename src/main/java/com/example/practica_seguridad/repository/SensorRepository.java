package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Sensor findByNombreIgnoreCase(String tipoCultivo);

    List<Sensor> findByNombreContainingIgnoreCase(String tipoCultivo);
    @Query("Select e from Sensor e where e.idSensor In :detalleSensor")
    List<Sensor> findByDetalleSensorIn(@Param("detalleSensor") List<Long> detalleSensor);
}
