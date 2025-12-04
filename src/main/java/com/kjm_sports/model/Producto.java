package com.kjm_sports.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagenUrl; // CamelCase en Java se convierte a imagen_url en MySQL

    // Relación: Muchos productos pueden tener una misma categoría
    @ManyToOne(optional = true)
    @JoinColumn(name = "categoria_id", nullable = true) 
    private Categoria categoria;
}