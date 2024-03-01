package com.example.practica_seguridad.service;

import com.example.practica_seguridad.interfaces.ITipoSuelo;
import com.example.practica_seguridad.model.TipoCultivo;
import com.example.practica_seguridad.model.TipoSuelo;
import com.example.practica_seguridad.repository.TipoSueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoSueloService implements ITipoSuelo {
    @Autowired
    private TipoSueloRepository tipoSueloRepository;

    @Override
    @Transactional
    public TipoSuelo create(TipoSuelo tipoSuelo) {
        try {
            if (tipoSueloRepository.findByTipoSuelo(tipoSuelo.getTipoSuelo()) != null) {
                return new TipoSuelo(-1, "");
            }
            return tipoSueloRepository.save(tipoSuelo);
        } catch (Exception e) {
            return new TipoSuelo(-1, e.getMessage());
        }
    }

    @Override
    @Transactional
    public TipoSuelo update(TipoSuelo tipoSuelo) {
        try {
            TipoSuelo existingSuelo = tipoSueloRepository.findByTipoSuelo(tipoSuelo.getTipoSuelo());
            if (existingSuelo != null && !existingSuelo.getIdTipoSuelo().equals(tipoSuelo.getIdTipoSuelo())) {
                return new TipoSuelo(-1, "");
            }
            return tipoSueloRepository.save(tipoSuelo);
        } catch (Exception e) {
            return new TipoSuelo(-1, e.getMessage());
        }
    }

    @Override
    @Transactional
    public TipoSuelo findById(Integer idTipoSuelo) {
        try {
            Optional<TipoSuelo> tipoSuelo = tipoSueloRepository.findById(idTipoSuelo);
            return tipoSuelo.orElse(null);
        } catch (Exception e) {
            return new TipoSuelo(-1, e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<TipoSuelo> findAll() {
        try {
            return tipoSueloRepository.findAll();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void delete(Integer idTipoSuelo) {
        try {
            tipoSueloRepository.deleteById(idTipoSuelo);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public List<TipoSuelo> findByNombre(String nombre) {
        try {
            return tipoSueloRepository.findByTipoSueloContainingIgnoreCase(nombre);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
