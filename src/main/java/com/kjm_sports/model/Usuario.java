package com.kjm_sports.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore; // <-- Asegúrate de que esta importación esté presente

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
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
    
    private String rol = "cliente"; 

    // --- LA CORRECCIÓN CLAVE ESTÁ AQUÍ ---
    // Se reemplaza @JsonIgnoreProperties por @JsonIgnore. 
    // Esto rompe el bucle infinito al convertir el objeto a JSON para enviarlo a la app.
    @JsonIgnore 
    @OneToMany(mappedBy = "usuario")
    private List<Boleta> boletas;
}