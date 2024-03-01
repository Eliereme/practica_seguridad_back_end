package com.example.practica_seguridad.repository;

import com.example.practica_seguridad.model.SistemaRiego;
import com.example.practica_seguridad.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArduinoRepository extends JpaRepository<SistemaRiego,Integer> {
    Optional<SistemaRiego> findOneByNombre(String nombre);
    Optional<List<SistemaRiego>> findByUsuario(Usuario usuario);
    SistemaRiego findByNombre(String nombreSistema);
    List<SistemaRiego> findByNombreContainingIgnoreCase(String nombreSistema);
}
