// src/main/java/py/edu/uc/lp32025/controller/EmpleadoTiempoCompletoController.java
package py.edu.uc.lp32025.controller;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.dto.BatchResponse;
import py.edu.uc.lp32025.dto.EmpleadoDTO;
import py.edu.uc.lp32025.dto.ImpuestosResponseDTO;
import py.edu.uc.lp32025.mappers.EmpleadoTiempoCompletoMapper;
import py.edu.uc.lp32025.service.EmpleadoTiempoCompletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoTiempoCompletoController extends BaseController {

    @Autowired
    private EmpleadoTiempoCompletoService empleadoService;

    @Autowired
    private EmpleadoTiempoCompletoMapper empleadoMapper; // Inyección del nuevo Mapper

    @GetMapping
    public ResponseEntity<List<EmpleadoTiempoCompleto>> listarTodos() {
        logger.info("Listando todos los empleados de tiempo completo");
        return ok(empleadoService.findAll());
    }

    // Endpoint para probar el Mapper (Retorna DTO en vez de Entidad)
    @GetMapping("/dto/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerDtoPorId(@PathVariable Long id) {
        EmpleadoTiempoCompleto empleado = empleadoService.findById(id);
        if (empleado != null) {
            // Usamos el mapper inyectado
            return ok(empleadoMapper.mapToDto(empleado));
        }
        return notFound();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoTiempoCompleto> obtenerPorId(@PathVariable Long id) {
        EmpleadoTiempoCompleto empleado = empleadoService.findById(id);
        if (empleado != null) {
            return ok(empleado);
        }
        return notFound();
    }

    @PostMapping
    public ResponseEntity<EmpleadoTiempoCompleto> crearEmpleado(@RequestBody EmpleadoTiempoCompleto empleado) {
        try {
            EmpleadoTiempoCompleto empleadoGuardado = empleadoService.guardarEmpleado(empleado);
            URI location = URI.create("/api/empleados/" + empleadoGuardado.getId());
            return created(location, empleadoGuardado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear empleado: {}", e.getMessage());
            return badRequest();
        }
    }

    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<EmpleadoTiempoCompleto>> findByDepartamento(@PathVariable String departamento) {
        logger.info("Buscando empleados por departamento: {}", departamento);
        return ok(empleadoService.findByDepartamento(departamento));
    }

    @GetMapping("/salario-mayor/{salario}")
    public ResponseEntity<List<EmpleadoTiempoCompleto>> findBySalarioMayor(@PathVariable BigDecimal salario) {
        logger.info("Buscando empleados con salario mayor a: {}", salario);
        return ok(empleadoService.findBySalarioMayor(salario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        logger.info("Eliminando empleado ID: {}", id);
        empleadoService.deleteById(id);
        return noContent();
    }

    @GetMapping("/{id}/impuestos")
    public ResponseEntity<ImpuestosResponseDTO> consultarImpuestos(@PathVariable Long id) {
        EmpleadoTiempoCompleto empleado = empleadoService.obtenerDatosImpuestos(id);
        if (empleado == null) {
            return notFound();
        }

        try {
            BigDecimal salarioBruto = empleado.getSalarioMensual();
            BigDecimal salarioConDescuento = empleado.calcularSalario();
            BigDecimal deducciones = empleado.calcularDeducciones();
            BigDecimal impuestoBase = empleado.calcularImpuestoBase();
            BigDecimal impuestosTotales = empleado.calcularImpuestos();

            ImpuestosResponseDTO response = ImpuestosResponseDTO.builder()
                    .id(id)
                    .nombreCompleto(empleado.getNombre() + " " + empleado.getApellido())
                    .departamento(empleado.getDepartamento())
                    .salarioBruto(salarioBruto)
                    .salarioConDescuento(salarioConDescuento)
                    .deducciones(deducciones)
                    .impuestoBase(impuestoBase)
                    .impuestosTotales(impuestosTotales)
                    .build();

            return ok(response);
        } catch (Exception e) {
            logger.error("Error calculando impuestos para empleado ID {}: {}", id, e.getMessage());
            return badRequest();
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<BatchResponse> guardarEmpleadosEnBatch(@RequestBody List<EmpleadoTiempoCompleto> empleados) {
        try {
            BatchResponse response = empleadoService.guardarEmpleadosEnBatch(empleados);
            return ok(response);
        } catch (Exception e) {
            logger.error("Error en carga batch: {}", e.getMessage());
            return badRequest();
        }
    }
}