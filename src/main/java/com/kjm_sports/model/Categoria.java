package com.kjm_sports.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data // Esto crea los Getters y Setters automáticamente
@Entity // Esto le dice a Spring: "Crea una tabla llamada Categoria en MySQL"
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental
    private Long id;

    private String nombre;       // Ej: "Zapatillas", "Ropa", "Accesorios"
    private String descripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.MERGE)
    private List<Producto> productos;
}