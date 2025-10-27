// src/main/java/py/edu/uc/lp32025/controller/ContratistaController.java
package py.edu.uc.lp32025.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Contratista;
import py.edu.uc.lp32025.service.ContratistaService;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contratistas")
public class ContratistaController {

    @Autowired
    private ContratistaService contratistaService;

    @GetMapping
    public List<Contratista> listarTodos() {
        return contratistaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contratista> obtenerPorId(@PathVariable Long id) {
        Contratista contratista = contratistaService.findById(id);
        if (contratista != null) {
            return ResponseEntity.ok(contratista);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Contratista> crearContratista(@RequestBody Contratista contratista) {
        try {
            Contratista contratistaGuardado = contratistaService.guardarContratista(contratista);
            URI location = URI.create("/api/contratistas/" + contratistaGuardado.getId());
            return ResponseEntity.created(location).body(contratistaGuardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarContratista(@PathVariable Long id) {
        contratistaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}