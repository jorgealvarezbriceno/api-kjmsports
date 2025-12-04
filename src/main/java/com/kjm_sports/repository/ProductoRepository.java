package com.kjm_sports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjm_sports.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // ESTA ES LA LÍNEA NUEVA:
    // Le enseña a Spring a buscar todos los productos que tengan un ID de categoría específico.
    List<Producto> findByCategoriaId(Long categoriaId);
}
