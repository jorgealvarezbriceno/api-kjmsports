package com.kjm_sports.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjm_sports.model.Producto;
import com.kjm_sports.repository.ProductoRepository;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

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

    // ESTE ES EL NUEVO MÉTODO COMPLETO:
    // Crea el endpoint GET /api/productos/categoria/{id}
    @GetMapping("/categoria/{id}")
    public List<Producto> obtenerProductosPorCategoria(@PathVariable Long id) {
        return productoRepository.findByCategoriaId(id);
    }
    // FIN DEL NUEVO MÉTODO

    // 3. POST: Crear producto
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // 4. PUT: Editar un producto (Necesario para el Panel Admin)
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
            prodExistente.setCategoria(detalles.getCategoria());

            return ResponseEntity.ok(productoRepository.save(prodExistente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Borrar un producto (Necesario para el Panel Admin)
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
