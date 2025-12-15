package com.kjm_sports.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter // Crea solo los getters
@Setter // Crea solo los setters
@RequiredArgsConstructor // Crea un constructor
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.MERGE)
    private List<Producto> productos;
}