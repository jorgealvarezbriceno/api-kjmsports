package com.kjm_sports.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
@JsonIgnoreProperties(value = {"boletas"}, allowGetters = true)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    @Column(unique = true)
    private String email;
    private String password;
    private String direccion;
    @Column(unique = true)
    private String telefono;
    
    // NUEVO CAMPO: Por defecto será "USER"
    private String rol = "cliente"; 

    // Relación solo para lectura (sin cascade REMOVE)
    @OneToMany(mappedBy = "usuario")
    private List<Boleta> boletas;
}