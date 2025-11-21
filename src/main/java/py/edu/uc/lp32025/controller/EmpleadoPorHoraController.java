// src/main/java/py/edu/uc/lp32025/controller/EmpleadoPorHoraController.java
package py.edu.uc.lp32025.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.service.EmpleadoPorHoraService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/empleados-por-hora")
public class EmpleadoPorHoraController extends BaseController {

    @Autowired
    private EmpleadoPorHoraService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoPorHora>> listarTodos() {
        return ok(empleadoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoPorHora> obtenerPorId(@PathVariable Long id) {
        // ✅ Ya no hay chequeo de null, el servicio lanza la excepción
        return ok(empleadoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmpleadoPorHora> crearEmpleado(@RequestBody EmpleadoPorHora empleado) {
        try {
            EmpleadoPorHora empleadoGuardado = empleadoService.guardarEmpleado(empleado);
            URI location = URI.create("/api/empleados-por-hora/" + empleadoGuardado.getId());
            return created(location, empleadoGuardado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear empleado por hora: {}", e.getMessage());
            return badRequest();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.deleteById(id);
        return noContent();
    }
}