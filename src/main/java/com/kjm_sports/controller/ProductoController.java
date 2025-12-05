package com.kjm_sports.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjm_sports.model.Categoria;
import com.kjm_sports.model.Producto;
import com.kjm_sports.repository.CategoriaRepository;
import com.kjm_sports.repository.ProductoRepository;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // --- INYECTAMOS EL NUEVO REPOSITORIO ---
    @Autowired
    private CategoriaRepository categoriaRepository;

    // 1. GET: Ver todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // 2. GET: Buscar un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{id}")
    public List<Producto> obtenerProductosPorCategoria(@PathVariable Long id) {
        return productoRepository.findByCategoriaId(id);
    }

   // --- MÉTODO "GUARDAR" CON LA CORRECCIÓN FINAL ---
        @PostMapping
        public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
            // Validamos que el producto que nos llega tenga una categoría con un ID
            if (producto.getCategoria() == null || producto.getCategoria().getId() == null) {
                return ResponseEntity.badRequest().build(); // Error 400: Petición incorrecta
            }

            // Buscamos en la base de datos si esa categoría realmente existe
            Optional<Categoria> categoriaReal = categoriaRepository.findById(producto.getCategoria().getId());

            if (!categoriaReal.isPresent()) {
                // Si el ID de la categoría no existe, devolvemos un error
                return ResponseEntity.badRequest().build();
            }
            
            // --- ESTA ES LA LÍNEA CLAVE DE LA SOLUCIÓN ---
            // Forzamos el ID a ser null para que Hibernate sepa que es un INSERT y no un UPDATE.
            producto.setId(null); 
            
            // Si todo está bien, asignamos la categoría real al producto y lo guardamos
            producto.setCategoria(categoriaReal.get());
            Producto productoGuardado = productoRepository.save(producto);
            
            // Devolvemos un 201 Created (éxito) con el producto recién creado
            return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
        }

    // 4. PUT: Editar un producto (Método mejorado)
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detalles) {
        Optional<Producto> productoOptional = productoRepository.findById(id);

        if (productoOptional.isPresent()) {
            Producto prodExistente = productoOptional.get();
            
            prodExistente.setNombre(detalles.getNombre());
            prodExistente.setDescripcion(detalles.getDescripcion());
            prodExistente.setPrecio(detalles.getPrecio());
            prodExistente.setStock(detalles.getStock());
            prodExistente.setImagenUrl(detalles.getImagenUrl());
            
            // Hacemos la misma validación para la categoría al editar
            if (detalles.getCategoria() != null && detalles.getCategoria().getId() != null) {
                Optional<Categoria> categoriaReal = categoriaRepository.findById(detalles.getCategoria().getId());
                categoriaReal.ifPresent(prodExistente::setCategoria); // Si la categoría existe, la actualiza
            } else {
                prodExistente.setCategoria(null); // Si no se envía categoría, la deja nula
            }

            return ResponseEntity.ok(productoRepository.save(prodExistente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Borrar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
