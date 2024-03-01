package com.example.practica_seguridad.service;

import com.example.practica_seguridad.model.Cosecha;
import com.example.practica_seguridad.model.Cultivo;
import com.example.practica_seguridad.model.ZonaRiego;
import com.example.practica_seguridad.repository.CosechaRepository;
import com.example.practica_seguridad.interfaces.ICosechaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CosechaService implements ICosechaService {
    @Autowired
    private CosechaRepository cosechaRepository;

    @Override
    @Transactional
    public Cosecha findByName(String name) {
        try {
            return cosechaRepository.findOneByDescripcion(name);
        } catch (Exception e) {
            return new Cosecha(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public Cosecha create(Cosecha datosCosecha) {
        try {
            if (cosechaRepository.findOneByDescripcion(datosCosecha.getDescripcion()) != null)
                return new Cosecha(-1L, "");
            return cosechaRepository.save(datosCosecha);
        } catch (Exception e) {
            return new Cosecha(-1L, "");
        }
    }

    @Override
    @Transactional
    public Cosecha update(Cosecha datosCosecha) {
        try {
            Cosecha existingCosecha = cosechaRepository.findOneByDescripcion(datosCosecha.getDescripcion());
            if (existingCosecha != null && !existingCosecha.getIdCosecha().equals(datosCosecha.getIdCosecha())) {
                return new Cosecha(-1L, "");
            }
            return cosechaRepository.save(datosCosecha);
        } catch (Exception e) {
            return new Cosecha(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public Cosecha findById(Integer idCosecha) {
        try {
            Optional<Cosecha> cosecha = cosechaRepository.findById(idCosecha);
            return cosecha.orElse(null);
        } catch (Exception e) {
            return new Cosecha(-1L, e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Cosecha> findAll() {
        try {
            return cosechaRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idCosecha) {
        try {
            cosechaRepository.deleteById(idCosecha);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public List<Cosecha> findByNombre(String nombre) {
        try {
            return cosechaRepository.findByDescripcionContainingIgnoreCase(nombre);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<Cosecha> findByCosecha(ZonaRiego zonaRiego) {
        try {
            return cosechaRepository.findByZonaRiego(zonaRiego);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<Cosecha> findByCultivo(Cultivo cultivo) {
        try {
            return cosechaRepository.findByCultivo(cultivo);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
