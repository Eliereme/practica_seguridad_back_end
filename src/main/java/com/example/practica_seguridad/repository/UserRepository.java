package com.example.practica_seguridad.repository;
import com.example.practica_seguridad.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByEmail(String email);
}
