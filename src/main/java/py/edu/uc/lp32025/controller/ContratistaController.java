// src/main/java/py/edu/uc/lp32025/controller/ContratistaController.java
package py.edu.uc.lp32025.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Contratista;
import py.edu.uc.lp32025.service.ContratistaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contratistas")
public class ContratistaController extends BaseController {

    @Autowired
    private ContratistaService contratistaService;

    @GetMapping
    public ResponseEntity<List<Contratista>> listarTodos() {
        logger.info("Listando contratistas");
        return ok(contratistaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contratista> obtenerPorId(@PathVariable Long id) {
        Contratista contratista = contratistaService.findById(id);
        if (contratista != null) {
            return ok(contratista);
        }
        return notFound();
    }

    @PostMapping
    public ResponseEntity<Contratista> crearContratista(@RequestBody Contratista contratista) {
        try {
            Contratista contratistaGuardado = contratistaService.guardarContratista(contratista);
            URI location = URI.create("/api/contratistas/" + contratistaGuardado.getId());
            return created(location, contratistaGuardado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear contratista: {}", e.getMessage());
            return badRequest();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarContratista(@PathVariable Long id) {
        logger.info("Eliminando contratista ID: {}", id);
        contratistaService.deleteById(id);
        return noContent();
    }
}