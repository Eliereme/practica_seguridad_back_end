package com.example.practica_seguridad.service;

import com.example.practica_seguridad.interfaces.IEnfermedad;
import com.example.practica_seguridad.model.Enfermedad;
import com.example.practica_seguridad.repository.EnfermedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnfermedadService implements IEnfermedad {
    @Autowired
    private EnfermedadRepository enfermedadRepository;

    @Override
    @Transactional
    public Enfermedad create(Enfermedad enfermedad) {
        try {
            if (enfermedadRepository.findByNombreEnfermedad(enfermedad.getNombreEnfermedad()) != null) {
                return new Enfermedad(-1, "", "", "", "", "", "");
            }
            return enfermedadRepository.save(enfermedad);
        } catch (Exception e) {
            return new Enfermedad(-1, "", e.getMessage(), "", "", "", "");
        }
    }

    @Override
    @Transactional
    public Enfermedad update(Enfermedad enfermedad) {
        try {
            Enfermedad existingEnfermedad = enfermedadRepository.findByNombreEnfermedad(enfermedad.getNombreEnfermedad());
            if (existingEnfermedad != null && !existingEnfermedad.getIdEnfermedad().equals(enfermedad.getIdEnfermedad())) {
                return new Enfermedad(-1, "", "", "", "", "", "");
            }
            return enfermedadRepository.save(enfermedad);
        } catch (Exception e) {
            return new Enfermedad(-1, "", e.getMessage(), "", "", "", "");
        }
    }

    @Override
    @Transactional
    public Enfermedad findById(Integer idEnfermedad) {
        try {
            Optional<Enfermedad> enfermedad = enfermedadRepository.findById(idEnfermedad);
            return enfermedad.orElse(null);
        } catch (Exception e) {
            return new Enfermedad(-1, "", e.getMessage(), "", "", "", "");
        }
    }

    @Override
    @Transactional
    public List<Enfermedad> findAll() {
        try {
            return enfermedadRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idEnfermedad) {
        try {
            enfermedadRepository.deleteById(idEnfermedad);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @Transactional
    public List<Enfermedad> findByNombre(String nombre) {
        try {
            return enfermedadRepository.findByNombreEnfermedadContainingIgnoreCase(nombre);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
