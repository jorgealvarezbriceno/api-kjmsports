package com.kjm_sports.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kjm_sports.model.DetalleBoleta;

@Repository
public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, Long> {

    // --- ESTA ES LA NUEVA FUNCIÓN ---
    // Cuenta las veces que aparece cada producto, las agrupa, las ordena de mayor a menor,
    // y nos devuelve solo los IDs de los productos más vendidos.
    @Query("SELECT d.producto.id FROM DetalleBoleta d GROUP BY d.producto.id ORDER BY COUNT(d.producto.id) DESC")
    List<Long> findTopSellingProductIds(Pageable pageable);
}