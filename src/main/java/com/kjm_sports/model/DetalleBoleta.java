package com.kjm_sports.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue; // Importante para evitar bucles infinitos
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class DetalleBoleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private Double precioUnitario; // Guardamos el precio del momento de la compra
    private Double subtotal;       // cantidad * precio

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "boleta_id")
    @JsonIgnore // Evita que al pedir los datos se cree un bucle infinito Boleta->Detalle->Boleta
    private Boleta boleta;
}