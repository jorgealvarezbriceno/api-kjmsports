package com.kjm_sports.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode; 
import lombok.ToString;

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
    
    // 🔑 CORRECCIÓN CRÍTICA: 
    // Excluimos la contraseña de los métodos de Lombok 
    // para evitar conflictos al cargar la entidad desde la DB.
    @ToString.Exclude 
    @EqualsAndHashCode.Exclude 
    private String password;
    
    private String direccion;
    
    @Column(unique = true)
    private String telefono;
    
    private String rol = "cliente"; 

    @JsonIgnore 
    @OneToMany(mappedBy = "usuario")
    private List<Boleta> boletas;
}