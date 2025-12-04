package com.kjm_sports.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjm_sports.model.Boleta;
import java.util.List;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
	List<Boleta> findByUsuario_Id(Long usuarioId);
}