package com.example.practica_seguridad.service;

import com.example.practica_seguridad.model.SistemaRiego;
import com.example.practica_seguridad.model.Usuario;
import com.example.practica_seguridad.repository.ArduinoRepository;
import com.example.practica_seguridad.interfaces.IArduinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArduinoService implements IArduinoService {
    @Autowired
    private ArduinoRepository arduinoRepository;

    @Override
    public SistemaRiego findByName(String name) {
        return arduinoRepository.findOneByNombre(name)
                .orElseThrow(() -> new UsernameNotFoundException("El sistema " + name + " no existe."));
    }

    @Override
    @Transactional
    public SistemaRiego create(SistemaRiego sistemaRiego) {
        try {
            if (arduinoRepository.findByNombre(sistemaRiego.getNombre()) != null) {
                return new SistemaRiego(-1L, "", "");
            }
            return arduinoRepository.save(sistemaRiego);

        } catch (Exception e) {
            return new SistemaRiego(-1L, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public SistemaRiego update(SistemaRiego sistemaRiego) {
        try {
            SistemaRiego existingSistema = arduinoRepository.findByNombre(sistemaRiego.getNombre());
            if (existingSistema != null && !existingSistema.getIdSistema().equals(sistemaRiego.getIdSistema())) {
                return new SistemaRiego(-1L, "", "");
            }
            return arduinoRepository.save(sistemaRiego);
        } catch (Exception e) {
            return new SistemaRiego(-1L, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public SistemaRiego findById(Integer idSistema) {
        try {
            Optional<SistemaRiego> sistemaRiego = arduinoRepository.findById(idSistema);
            return sistemaRiego.orElse(null);
        } catch (Exception e) {
            return new SistemaRiego(-1L, "", e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<SistemaRiego> findAll() {
        try {
            return arduinoRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public List<SistemaRiego> findByUsuario(Usuario usuario) {
        try {
            return arduinoRepository.findByUsuario(usuario)
                    .orElseThrow(() -> new IllegalArgumentException("Does not have a watering system installed."));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idSistemaRiego) {
        try {
            arduinoRepository.deleteById(idSistemaRiego);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public List<SistemaRiego> findByNombre(String nombre) {
        try {
            return arduinoRepository.findByNombreContainingIgnoreCase(nombre);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
