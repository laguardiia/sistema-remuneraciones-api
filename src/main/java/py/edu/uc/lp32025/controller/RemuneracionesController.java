// src/main/java/py/edu/uc/lp32025/controller/RemuneracionesController.java
package py.edu.uc.lp32025.controller;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.dto.EmpleadoDTO;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.EmpleadoNoEncontradoException;
import py.edu.uc.lp32025.service.EmpleadoTiempoCompletoService;
import py.edu.uc.lp32025.service.RemuneracionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/remuneraciones")
public class RemuneracionesController {

    @Autowired
    private RemuneracionesService remuneracionesService;

    @Autowired
    private EmpleadoTiempoCompletoService empleadoService;

    @GetMapping("/empleados")
    public List<EmpleadoDTO> listarTodosLosEmpleados() {
        return remuneracionesService.listarTodosLosEmpleados();
    }

    @GetMapping("/total-remuneraciones")
    public BigDecimal calcularTotalRemuneraciones() {
        return remuneracionesService.calcularTotalRemuneraciones();
    }

    @GetMapping("/empleados/tipo/{tipoEmpleado}")
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleadosPorTipo(@PathVariable String tipoEmpleado) {
        List<EmpleadoDTO> empleados = remuneracionesService.listarEmpleadosPorTipo(tipoEmpleado);
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/nomina-con-dias")
    public ResponseEntity<Map<String, Object>> calcularNominaConDiasSolicitados() {
        Map<String, Object> nominaConDias = remuneracionesService.calcularNominaConDiasSolicitados();
        return ResponseEntity.ok(nominaConDias);
    }

    // ✅ Endpoint actualizado para solicitar días - NO declares throws
    @PostMapping("/empleados/{empleadoId}/solicitar-dias")
    public ResponseEntity<String> solicitarDias(
            @PathVariable Long empleadoId,
            @RequestParam String tipoSolicitud,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam(required = false) String justificacion
    ) {
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            LocalDate[] rangoFechas = {inicio, fin};

            // Llamar al servicio para procesar la solicitud
            // Este método puede lanzar DiasInsuficientesException o EmpleadoNoEncontradoException
            remuneracionesService.solicitarDias(empleadoId, tipoSolicitud, rangoFechas, justificacion);

            String mensajeExito = "Solicitud de " + tipoSolicitud.toLowerCase() + " para el empleado ID " + empleadoId + " procesada exitosamente.";
            return ResponseEntity.ok(mensajeExito);

        } catch (EmpleadoNoEncontradoException e) {
            // Esta también debería ser manejada por GlobalExceptionHandler
            // Si GlobalExceptionHandler está configurado, no necesitas capturarla explícitamente aquí
            // a menos que quieras devolver un ResponseEntity específico desde el controlador.
            // Para cumplir con la especificación de usar GlobalExceptionHandler, déjala propagar.
            throw e; // O no hacer nada si GlobalExceptionHandler lo maneja globalmente
        } catch (Exception e) {
            // Cualquier otra excepción inesperada que no sea de las personalizadas
            // Si GlobalExceptionHandler maneja DiasInsuficientesException y EmpleadoNoEncontradoException,
            // esta captura solo atrapará errores inesperados.
            return ResponseEntity.badRequest().body("Error inesperado al procesar la solicitud: " + e.getMessage());
        }
    }

    @GetMapping("/empleados/buscar")
    public ResponseEntity<List<EmpleadoDTO>> filtrarEmpleadosPorNombre(@RequestParam String nombre) {
        List<EmpleadoDTO> empleados = remuneracionesService.filtrarEmpleadosPorNombre(nombre);
        return ResponseEntity.ok(empleados);
    }
}