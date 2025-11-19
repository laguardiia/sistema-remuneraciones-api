// src/main/java/py/edu/uc/lp32025/service/EmpleadoTiempoCompletoService.java
package py.edu.uc.lp32025.service;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.dto.BatchResponse;
import py.edu.uc.lp32025.repository.EmpleadoTiempoCompletoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoTiempoCompletoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoTiempoCompletoService.class);

    @Autowired
    private EmpleadoTiempoCompletoRepository empleadoRepository;

    public EmpleadoTiempoCompleto guardarEmpleado(EmpleadoTiempoCompleto empleado) {
        logger.info("Guardando empleado: {} {}", empleado.getNombre(), empleado.getApellido());

        if (empleado.getSalarioMensual() == null || empleado.getSalarioMensual().compareTo(BigDecimal.ZERO) < 0) {
            logger.warn("Intento de guardar empleado con salario inválido: {}", empleado.getSalarioMensual());
            throw new IllegalArgumentException("El salario no puede ser nulo o negativo");
        }

        if (empleado.getDepartamento() == null || empleado.getDepartamento().trim().isEmpty()) {
            logger.warn("Intento de guardar empleado sin departamento: {} {}", empleado.getNombre(), empleado.getApellido());
            throw new IllegalArgumentException("El departamento es obligatorio");
        }

        EmpleadoTiempoCompleto empleadoGuardado = empleadoRepository.save(empleado);
        logger.info("Empleado guardado exitosamente con ID: {}", empleadoGuardado.getId());
        return empleadoGuardado;
    }

    public List<EmpleadoTiempoCompleto> findAll() {
        logger.info("Obteniendo todos los empleados de tiempo completo");
        List<EmpleadoTiempoCompleto> empleados = empleadoRepository.findAll();
        logger.info("Se encontraron {} empleados", empleados.size());
        return empleados;
    }

    public EmpleadoTiempoCompleto findById(Long id) {
        logger.info("Buscando empleado con ID: {}", id);
        EmpleadoTiempoCompleto empleado = empleadoRepository.findById(id).orElse(null);
        if (empleado != null) {
            logger.info("Empleado encontrado: {} {}", empleado.getNombre(), empleado.getApellido());
        } else {
            logger.warn("Empleado con ID {} no encontrado", id);
        }
        return empleado;
    }

    public List<EmpleadoTiempoCompleto> findByDepartamento(String departamento) {
        logger.info("Buscando empleados por departamento: {}", departamento);
        List<EmpleadoTiempoCompleto> empleados = empleadoRepository.findByDepartamento(departamento);
        logger.info("Se encontraron {} empleados en el departamento {}", empleados.size(), departamento);
        return empleados;
    }

    public List<EmpleadoTiempoCompleto> findBySalarioMayor(BigDecimal salario) {
        logger.info("Buscando empleados con salario mayor a: {}", salario);
        List<EmpleadoTiempoCompleto> empleados = empleadoRepository.findBySalarioMensualGreaterThan(salario);
        logger.info("Se encontraron {} empleados con salario mayor a {}", empleados.size(), salario);
        return empleados;
    }

    public void deleteById(Long id) {
        logger.info("Eliminando empleado con ID: {}", id);
        if (empleadoRepository.existsById(id)) {
            empleadoRepository.deleteById(id);
            logger.info("Empleado con ID {} eliminado exitosamente", id);
        } else {
            logger.warn("Intento de eliminar empleado con ID {} que no existe", id);
        }
    }

    public EmpleadoTiempoCompleto obtenerDatosImpuestos(Long id) {
        logger.info("Obteniendo datos de impuestos para empleado ID: {}", id);
        EmpleadoTiempoCompleto empleado = empleadoRepository.findById(id).orElse(null);
        if (empleado != null) {
            if (!empleado.validarDatosEspecificos()) {
                logger.warn("Datos inválidos para cálculo de impuestos del empleado ID: {}", id);
                throw new IllegalArgumentException("Datos del empleado no válidos para cálculo de impuestos");
            }
            logger.info("Datos de impuestos obtenidos exitosamente para empleado ID: {}", id);
        } else {
            logger.warn("Empleado con ID {} no encontrado para cálculo de impuestos", id);
        }
        return empleado;
    }

    // ✅ Método para guardar empleados en batch con validaciones detalladas
    @Transactional
    public BatchResponse guardarEmpleadosEnBatch(List<EmpleadoTiempoCompleto> empleados) {
        logger.info("Iniciando proceso de guardado en batch de {} empleados", empleados.size());

        if (empleados == null || empleados.isEmpty()) {
            logger.info("Lista de empleados vacía, no se procesará nada");
            return new BatchResponse(0, 0, new ArrayList<>());
        }

        List<EmpleadoTiempoCompleto> empleadosValidos = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        int totalProcesados = 0;
        int totalGuardados = 0;

        // Validar todos los empleados antes de guardar
        for (int i = 0; i < empleados.size(); i++) {
            EmpleadoTiempoCompleto empleado = empleados.get(i);

            try {
                // --- VALIDACIÓN DETALLADA ---
                List<String> erroresIndividuales = new ArrayList<>();

                // 1. Validar número de documento (usando la anotación @Pattern en Persona)
                // Esta validación se disparará durante la persistencia o al llamar a setter si se usa Bean Validation en setters
                // Pero aquí la validamos explícitamente para un mejor control
                if (empleado.getNumeroDocumento() != null) {
                    if (!empleado.getNumeroDocumento().matches("^\\d{1,20}$")) {
                        erroresIndividuales.add("Número de documento debe tener entre 1 y 20 dígitos");
                    }
                } else {
                    erroresIndividuales.add("Número de documento es obligatorio");
                }

                // 2. Validar salario mensual (debe ser >= 2899048)
                if (empleado.getSalarioMensual() == null) {
                    erroresIndividuales.add("Salario mensual es obligatorio");
                } else if (empleado.getSalarioMensual().compareTo(BigDecimal.valueOf(2899048)) < 0) {
                    erroresIndividuales.add("Salario mensual debe ser mayor o igual a 2899048 (salario mínimo en Paraguay)");
                }

                // 3. Validar departamento (no debe estar vacío)
                if (empleado.getDepartamento() == null || empleado.getDepartamento().trim().isEmpty()) {
                    erroresIndividuales.add("Departamento es obligatorio");
                }

                // 4. Validar que el nombre no esté vacío (aunque @NotBlank en Persona lo debería cubrir al persistir)
                if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
                    erroresIndividuales.add("Nombre es obligatorio");
                }

                // 5. Validar que el apellido no esté vacío (aunque @NotBlank en Persona lo debería cubrir al persistir)
                if (empleado.getApellido() == null || empleado.getApellido().trim().isEmpty()) {
                    erroresIndividuales.add("Apellido es obligatorio");
                }

                // 6. Validar fecha de nacimiento (no debe ser futura, aunque PersonaController ya lo validaba al crear)
                if (empleado.getFechaNacimiento() != null && empleado.getFechaNacimiento().isAfter(LocalDate.now())) {
                    erroresIndividuales.add("Fecha de nacimiento no puede ser futura");
                }


                // Si hay errores individuales, no pasar a la validación general
                if (!erroresIndividuales.isEmpty()) {
                    String mensajeError = "Empleado en posición " + i + ": " + String.join(", ", erroresIndividuales);
                    errores.add(mensajeError);
                    logger.warn(mensajeError);
                    totalProcesados++;
                    continue; // Saltar a la siguiente iteración
                }

                // Si pasó las validaciones manuales, verificar la validación general
                // (Esta validación general ahora debería ser redundante si todas las validaciones están aquí arriba)
                // Pero la mantenemos por si acaso.
                boolean datosEspecificosValidos = empleado.validarDatosEspecificos();
                logger.debug("Validación general para empleado {}: {}", i, datosEspecificosValidos ? "APROBADA" : "RECHAZADA");

                if (!datosEspecificosValidos) {
                    // Este caso solo debería ocurrir si `validarDatosEspecificos` cambia o si hay otra validación allí
                    String mensajeError = "Empleado en posición " + i + ": Datos inválidos según la validación general";
                    errores.add(mensajeError);
                    logger.warn(mensajeError);
                } else {
                    // Si pasa todas las validaciones, agregar a la lista de válidos
                    empleadosValidos.add(empleado);
                    logger.debug("Empleado en posición {} aprobado en todas las validaciones: {} {}", i, empleado.getNombre(), empleado.getApellido());
                }

                totalProcesados++;

            } catch (Exception e) {
                // Captura de excepciones inesperadas durante la validación
                String mensajeError = "Empleado en posición " + i + ": Error inesperado durante la validación - " + e.getMessage();
                errores.add(mensajeError);
                logger.error(mensajeError, e);
                totalProcesados++;
            }
        }


        // Procesar en chunks de 100 solo los empleados válidos
        List<EmpleadoTiempoCompleto> empleadosGuardados = new ArrayList<>();
        int chunkSize = 100;
        int chunkNumber = 1;

        for (int i = 0; i < empleadosValidos.size(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, empleadosValidos.size());
            List<EmpleadoTiempoCompleto> chunk = empleadosValidos.subList(i, endIndex);

            logger.info("Procesando chunk {} de empleados (índices {}-{})", chunkNumber, i, endIndex - 1);

            List<EmpleadoTiempoCompleto> chunkGuardados;
            try {
                // Intentar guardar el chunk
                chunkGuardados = empleadoRepository.saveAll(chunk);
            } catch (Exception e) {
                // Si falla el chunk entero, marcar todos como errores
                for (int j = i; j < endIndex; j++) {
                    EmpleadoTiempoCompleto emp = empleadosValidos.get(j);
                    String mensajeError = "Empleado en posición (en lista válida) " + j + ": Error al guardar - " + e.getMessage();
                    errores.add(mensajeError);
                    logger.error(mensajeError, e);
                }
                chunkGuardados = new ArrayList<>(); // Chunk vacío para no incrementar guardados
            }

            empleadosGuardados.addAll(chunkGuardados);
            totalGuardados += chunkGuardados.size();

            logger.info("Chunk {} procesado exitosamente: {} empleados guardados", chunkNumber, chunkGuardados.size());
            chunkNumber++;
        }

        // El número de errores aquí es el total procesado MENOS los que se guardaron exitosamente
        // (esto incluye los que fallaron la validación inicial y los que fallaron al guardar)
        int erroresFinales = totalProcesados - totalGuardados;

        BatchResponse response = new BatchResponse(totalGuardados, erroresFinales, errores);
        logger.info("Proceso de guardado en batch finalizado. Guardados: {}, Errores (procesados - guardados): {}", response.getGuardados(), response.getErrores());

        return response;
    }
}