package com.example.practica_seguridad.service;

import com.example.practica_seguridad.model.Notificacion;
import com.example.practica_seguridad.interfaces.INotificacionesService;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.repository.NotificacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionesService implements INotificacionesService {
    @Autowired
    private NotificacionesRepository notificacionesRepository;

    @Override
    @Transactional
    public Notificacion create(Notificacion notificacionsensor) {
        try {
            notificacionsensor.setFecha(new Date());
            return notificacionesRepository.save(notificacionsensor);
        } catch (Exception e) {
            return new Notificacion(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public Notificacion update(Notificacion notificacion) {
        try {
            return notificacionesRepository.save(notificacion);
        } catch (Exception e) {
            return new Notificacion(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public Notificacion findById(Integer idNotificacion) {
        try {
            Optional<Notificacion> notificacion = notificacionesRepository.findById(idNotificacion);
            return notificacion.orElse(null);
        } catch (Exception e) {
            return new Notificacion(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Notificacion> findAll() {
        try {
            return notificacionesRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idNotificacion) {
        try {
            notificacionesRepository.deleteById(idNotificacion);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public List<Notificacion> findByZonaRiego(ZonaRiego zonaRiego) {
        try {
            return notificacionesRepository.findByZonaRiego(zonaRiego);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<Notificacion> findByFecha(ZonaRiego zonaRiego, Date fecha) {
        try {
            return notificacionesRepository.findByZonaRiegoAndFecha(zonaRiego, fecha);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
