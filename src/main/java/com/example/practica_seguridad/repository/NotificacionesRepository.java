package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.Notificacion;
import com.example.practica_seguridad.model.ZonaRiego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NotificacionesRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByZonaRiego(ZonaRiego zonaRiego);

    List<Notificacion> findByZonaRiegoAndFecha(ZonaRiego zonaRiego, Date fecha);
}
