// src/main/java/py/edu/uc/lp32025/controller/GerenteController.java
package py.edu.uc.lp32025.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.Gerente;
import py.edu.uc.lp32025.service.EmpleadoTiempoCompletoService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gerentes")
public class GerenteController extends BaseController {

    @Autowired
    private EmpleadoTiempoCompletoService empleadoService;

    @GetMapping
    public ResponseEntity<List<Gerente>> listarTodos() {
        logger.info("Listando todos los gerentes");
        List<Gerente> gerentes = empleadoService.findAll().stream()
                .filter(e -> e instanceof Gerente)
                .map(e -> (Gerente) e)
                .collect(Collectors.toList());
        return ok(gerentes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gerente> obtenerPorId(@PathVariable Long id) {
        logger.info("Buscando gerente con ID: {}", id);
        var empleado = empleadoService.findById(id);
        if (empleado instanceof Gerente) {
            return ok((Gerente) empleado);
        }
        return notFound();
    }

    @PostMapping
    public ResponseEntity<Gerente> crearGerente(@RequestBody Gerente gerente) {
        logger.info("Creando nuevo gerente: {} {}", gerente.getNombre(), gerente.getApellido());
        try {
            Gerente gerenteGuardado = (Gerente) empleadoService.guardarEmpleado(gerente);
            URI location = URI.create("/api/gerentes/" + gerenteGuardado.getId());
            return created(location, gerenteGuardado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al crear gerente: {}", e.getMessage());
            return badRequest();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGerente(@PathVariable Long id) {
        logger.info("Eliminando gerente con ID: {}", id);
        empleadoService.deleteById(id);
        return noContent();
    }
}