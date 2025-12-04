package com.kjm_sports.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjm_sports.model.Usuario; // 🔑 IMPORTACIÓN NECESARIA

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // 🔑 CORRECCIÓN CLAVE: El método DEBE devolver Optional<Usuario>
    // Esto asegura que el Controller maneje correctamente el caso en que el usuario no exista.
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
}

