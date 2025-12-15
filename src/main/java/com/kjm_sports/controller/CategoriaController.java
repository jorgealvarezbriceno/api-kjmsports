package com.kjm_sports.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // <-- ESTA ES LA IMPORTACIÓN QUE FALTABA
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
import com.kjm_sports.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // 1. GET: Ver todas las categorías
    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    // 2. GET: Ver una sola categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoria(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. POST: Crear una nueva categoría (MÉTODO CORREGIDO)
    @PostMapping
    public ResponseEntity<Categoria> guardarCategoria(@RequestBody Categoria categoria) {
        // Forzamos el ID a ser null para que Hibernate sepa que es un INSERT.
        categoria.setId(null); 
        
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        
        // Devolvemos un código de estado 201 (CREATED), que es la práctica correcta.
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaGuardada);
    }

    // 4. PUT: Actualizar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria detallesCategoria) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            Categoria categoriaExistente = categoriaOptional.get();
            categoriaExistente.setNombre(detallesCategoria.getNombre());
            categoriaExistente.setDescripcion(detallesCategoria.getDescripcion());
            
            return ResponseEntity.ok(categoriaRepository.save(categoriaExistente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Borrar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.ok().build(); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
}