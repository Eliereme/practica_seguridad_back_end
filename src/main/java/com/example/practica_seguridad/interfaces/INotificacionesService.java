package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.Notificacion;
import com.example.practica_seguridad.model.Usuario;
import com.example.practica_seguridad.model.ZonaRiego;

import java.util.List;

public interface INotificacionesService {
    Notificacion create(Notificacion notificacionsensor);
    Notificacion update(Notificacion notificacion);
    Notificacion findById(Integer idNotificacion);
    List<Notificacion> findAll();
    void delete(Integer idNotificacion);
    List<Notificacion> findByZonaRiego(ZonaRiego zonaRiego);
}
