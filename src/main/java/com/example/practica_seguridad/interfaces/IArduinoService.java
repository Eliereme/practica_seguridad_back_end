package com.example.practica_seguridad.interfaces;

import com.example.practica_seguridad.model.SistemaRiego;
import com.example.practica_seguridad.model.Usuario;

import java.util.List;

public interface IArduinoService {
    SistemaRiego findByName(String name);
    SistemaRiego create(SistemaRiego datosSensor);
    SistemaRiego update(SistemaRiego usuario);
    SistemaRiego findById(Integer idSistema);
    List<SistemaRiego> findAll();
    List<SistemaRiego> findByUsuario(Usuario usuario);
    void delete(Integer idUsuario);
}
