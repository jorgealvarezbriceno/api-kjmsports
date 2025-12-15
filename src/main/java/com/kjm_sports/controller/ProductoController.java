package com.kjm_sports.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.kjm_sports.repository.DetalleBoletaRepository; // <-- IMPORTANTE
import com.kjm_sports.repository.ProductoRepository;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // --- INYECTAMOS EL NUEVO REPOSITORIO DE DETALLES ---
    @Autowired
    private DetalleBoletaRepository detalleBoletaRepository;

    // --- MÉTODO PARA EL NUEVO REPORTE ---
    @GetMapping("/top-selling")
    public ResponseEntity<List<Producto>> getTopSellingProducts() {
        // Le pedimos que nos devuelva solo los 5 productos más vendidos.
        Pageable topFive = PageRequest.of(0, 5);
        List<Long> topProductIds = detalleBoletaRepository.findTopSellingProductIds(topFive);

        if (topProductIds.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>()); // Devuelve lista vacía si no hay ventas
        }

        // Buscamos los detalles completos de los productos con los IDs que encontramos
        List<Producto> topProducts = productoRepository.findAllById(topProductIds);

        // Reordenamos la lista para que coincida con el orden de más a menos vendido
        Map<Long, Producto> productMap = topProducts.stream().collect(Collectors.toMap(Producto::getId, p -> p));
        List<Producto> sortedTopProducts = topProductIds.stream()
                                                       .map(productMap::get)
                                                       .collect(Collectors.toList());

        return ResponseEntity.ok(sortedTopProducts);
    }

    // El resto de tus métodos GET, POST, PUT, DELETE se mantienen igual...
    
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

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

    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        if (producto.getCategoria() == null || producto.getCategoria().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Categoria> categoriaReal = categoriaRepository.findById(producto.getCategoria().getId());
        if (!categoriaReal.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        producto.setId(null);
        producto.setCategoria(categoriaReal.get());
        Producto productoGuardado = productoRepository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
    }

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
            if (detalles.getCategoria() != null && detalles.getCategoria().getId() != null) {
                Optional<Categoria> categoriaReal = categoriaRepository.findById(detalles.getCategoria().getId());
                categoriaReal.ifPresent(prodExistente::setCategoria);
            } else {
                prodExistente.setCategoria(null);
            }
            return ResponseEntity.ok(productoRepository.save(prodExistente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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