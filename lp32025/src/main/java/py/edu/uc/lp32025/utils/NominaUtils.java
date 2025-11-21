// src/main/java/py/edu/uc/lp32025/utils/NominaUtils.java
package py.edu.uc.lp32025.utils;

import py.edu.uc.lp32025.domain.Permisionable;
import py.edu.uc.lp32025.dto.EmpleadoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NominaUtils {

    private static final Logger logger = LoggerFactory.getLogger(NominaUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Reutilizable

    /**
     * Calcula el total de días solicitados por todos los empleados en la lista.
     * Se asume que cada objeto Permisionable representa una solicitud con un rango de fechas.
     * Este es un cálculo de ejemplo basado en el rango de fechas de la solicitud.
     * En un sistema real, esto podría leerse de una entidad de "Solicitud" específica.
     *
     * @param solicitudes Lista de objetos que implementan Permisionable (representan solicitudes).
     * @return El total de días calculados a partir de las solicitudes.
     */
    public static long calcularTotalDiasSolicitados(List<Permisionable> solicitudes) {
        logger.info("Calculando total de días solicitados para {} solicitudes.", solicitudes.size());

        if (solicitudes == null || solicitudes.isEmpty()) {
            logger.info("Lista de solicitudes vacía, total de días es 0.");
            return 0;
        }

        long totalDias = solicitudes.stream()
                .mapToLong(solicitud -> {
                    // Este es un ejemplo de cómo podrías calcular días si tuvieras acceso directo al rango de fechas
                    // Supongamos que cada Permisionable tiene un rango de fechas asociado (esto no está en la interfaz actual)
                    // LocalDate[] rangoFechas = solicitud.getRangoFechasSolicitud(); // Este método no existe
                    // if (rangoFechas != null && rangoFechas.length == 2) {
                    //     return ChronoUnit.DAYS.between(rangoFechas[0], rangoFechas[1]) + 1;
                    // }
                    // return 0;

                    // DADO QUE LA INTERFAZ Permisionable NO TIENE MÉTODO PARA OBTENER RANGO DE FECHAS DE LA SOLICITUD ACTUAL,
                    // ESTE CÁLCULO ES SIMPLIFICADO O INAPLICABLE TAL CUAL ESTÁ DEFINIDA LA INTERFAZ.
                    // SE PODRÍA HACER CON UNA LISTA DE OBJETOS QUE SI TENGAN ESA INFORMACIÓN.
                    // Por ejemplo, si recibimos una lista de EmpleadoDTO que contengan información de sus solicitudes pendientes o realizadas.
                    // Para este ejemplo, retornamos 0, ya que no tenemos forma de saber cuántos días solicitó cada Permisionable aquí.
                    logger.warn("No se puede calcular días solicitados directamente desde la interfaz Permisionable sin información de fechas de solicitud.");
                    return 0L;
                })
                .sum();

        logger.info("Total de días solicitados calculado: {}", totalDias);
        return totalDias;
    }

    /**
     * Genera un reporte en formato JSON con los empleados que han solicitado más de un número de días especificado.
     * Este método asume que recibe una lista de DTOs que contienen la información de días solicitados.
     * En un sistema real, esto leería de una entidad de "Solicitud" o de una vista/materialización de datos.
     *
     * @param empleados Lista de DTOs de empleados que contiene información de días solicitados.
     * @param diasMinimos Número de días mínimo para incluir al empleado en el reporte.
     * @return Una cadena JSON con el reporte de empleados.
     */
    public static String generarReporteEmpleadosConDias(List<EmpleadoDTO> empleados, int diasMinimos) {
        logger.info("Generando reporte de empleados con más de {} días solicitados.", diasMinimos);

        if (empleados == null || empleados.isEmpty()) {
            logger.info("Lista de empleados vacía, reporte será un array vacío.");
            try {
                return objectMapper.writeValueAsString(new ArrayList<>());
            } catch (JsonProcessingException e) {
                logger.error("Error al serializar lista vacía a JSON", e);
                return "{}"; // Devolver un JSON vacío como fallback
            }
        }

        // Filtrar empleados que hayan solicitado más de 'diasMinimos'
        // NOTA: El DTO EmpleadoDTO actual no tiene un campo como 'diasSolicitados'.
        // Esta lógica es un ejemplo de cómo sería si el DTO tuviera esa información.
        // Para este ejemplo, asumiremos que 'informacionEspecifica' contiene una cadena con días.
        // EN UNA IMPLEMENTACIÓN REAL, este dato vendría de una entidad de solicitud o un servicio específico.
        List<EmpleadoDTO> empleadosFiltrados = empleados.stream()
                .filter(empleado -> {
                    // Ejemplo de lógica si 'informacionEspecifica' tuviera días (esto es un hack para el ejemplo actual)
                    // String info = empleado.getInformacionEspecifica();
                    // if (info != null) {
                    //     // Buscar un patrón como "Días Solicitados: XX" en la cadena
                    //     Pattern pattern = Pattern.compile("Días Solicitados:\\s*(\\d+)");
                    //     Matcher matcher = pattern.matcher(info);
                    //     if (matcher.find()) {
                    //         try {
                    //             int dias = Integer.parseInt(matcher.group(1));
                    //             return dias > diasMinimos;
                    //         } catch (NumberFormatException e) {
                    //             logger.warn("No se pudo parsear el número de días de '{}'", info);
                    //         }
                    //     }
                    // }
                    // Para este ejemplo, devolvemos false porque no tenemos la info necesaria en el DTO actual
                    return false;
                })
                .collect(Collectors.toList());

        logger.info("Se encontraron {} empleados con más de {} días solicitados.", empleadosFiltrados.size(), diasMinimos);

        try {
            // Convertir la lista filtrada a JSON
            String jsonReporte = objectMapper.writeValueAsString(empleadosFiltrados);
            logger.debug("Reporte generado: {}", jsonReporte);
            return jsonReporte;
        } catch (JsonProcessingException e) {
            logger.error("Error al serializar el reporte a JSON", e);
            // Devolver un JSON vacío como fallback en caso de error
            try {
                return objectMapper.writeValueAsString(new ArrayList<>());
            } catch (JsonProcessingException fallbackException) {
                logger.error("Error fatal al serializar fallback", fallbackException);
                return "{}";
            }
        }
    }

    // --- MÉTODOS DE EJEMPLO CON DATOS FICTICIOS PARA DEMOSTRAR EL USO ---

    /**
     * Este método es un ejemplo de cómo calcular días si se tuviera una lista de rangos de fechas de solicitudes.
     * No opera sobre la interfaz Permisionable directamente, sino sobre datos de ejemplo.
     *
     * @param rangosFechasSolicitudes Lista de arrays de 2 fechas [inicio, fin] representando solicitudes.
     * @return Total de días calculados.
     */
    public static long calcularTotalDiasSolicitadosDesdeRangos(List<LocalDate[]> rangosFechasSolicitudes) {
        logger.info("Calculando total de días solicitados desde rangos de fechas para {} solicitudes.", rangosFechasSolicitudes.size());

        if (rangosFechasSolicitudes == null || rangosFechasSolicitudes.isEmpty()) {
            logger.info("Lista de rangos de fechas vacía, total de días es 0.");
            return 0;
        }

        long totalDias = rangosFechasSolicitudes.stream()
                .filter(rango -> rango != null && rango.length == 2 && rango[0] != null && rango[1] != null)
                .mapToLong(rango -> ChronoUnit.DAYS.between(rango[0], rango[1]) + 1) // +1 para incluir día de inicio y fin
                .sum();

        logger.info("Total de días calculado desde rangos: {}", totalDias);
        return totalDias;
    }

    /**
     * Este método es un ejemplo de cómo generar un reporte si se tuviera una lista de objetos
     * que contengan la información necesaria (empleado y días solicitados).
     * No usa directamente EmpleadoDTO, sino un objeto de ejemplo.
     *
     * @param solicitudesEmpleado Lista de objetos que contengan empleado y días solicitados.
     * @param diasMinimos Número de días mínimo.
     * @return JSON del reporte.
     */
    public static String generarReporteEmpleadosConDiasDesdeSolicitudes(List<SolicitudEmpleadoEjemplo> solicitudesEmpleado, int diasMinimos) {
        logger.info("Generando reporte desde lista de solicitudes ejemplo, con umbral de {} días.", diasMinimos);

        if (solicitudesEmpleado == null || solicitudesEmpleado.isEmpty()) {
            logger.info("Lista de solicitudes ejemplo vacía, reporte será un array vacío.");
            try {
                return objectMapper.writeValueAsString(new ArrayList<>());
            } catch (JsonProcessingException e) {
                logger.error("Error al serializar lista vacía de solicitudes ejemplo a JSON", e);
                return "{}";
            }
        }

        List<SolicitudEmpleadoEjemplo> filtradas = solicitudesEmpleado.stream()
                .filter(solicitud -> solicitud.getDiasSolicitados() > diasMinimos)
                .collect(Collectors.toList());

        logger.info("Se encontraron {} solicitudes ejemplo que superan el umbral de {} días.", filtradas.size(), diasMinimos);

        try {
            String jsonReporte = objectMapper.writeValueAsString(filtradas);
            logger.debug("Reporte desde solicitudes ejemplo generado: {}", jsonReporte);
            return jsonReporte;
        } catch (JsonProcessingException e) {
            logger.error("Error al serializar el reporte de solicitudes ejemplo a JSON", e);
            try {
                return objectMapper.writeValueAsString(new ArrayList<>());
            } catch (JsonProcessingException fallbackException) {
                logger.error("Error fatal al serializar fallback de solicitudes ejemplo", fallbackException);
                return "{}";
            }
        }
    }
}

// Clase de ejemplo para demostrar el método generarReporteEmpleadosConDiasDesdeSolicitudes
// En una implementación real, esto sería una entidad o DTO de solicitud específica.
class SolicitudEmpleadoEjemplo {
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String tipoSolicitud; // "VACACIONES", "PERMISO"
    private long diasSolicitados;

    public SolicitudEmpleadoEjemplo(String nombreEmpleado, String apellidoEmpleado, String tipoSolicitud, long diasSolicitados) {
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.tipoSolicitud = tipoSolicitud;
        this.diasSolicitados = diasSolicitados;
    }

    // Getters
    public String getNombreEmpleado() { return nombreEmpleado; }
    public String getApellidoEmpleado() { return apellidoEmpleado; }
    public String getTipoSolicitud() { return tipoSolicitud; }
    public long getDiasSolicitados() { return diasSolicitados; }

    // No es obligatorio, pero útil para logging/debugging
    @Override
    public String toString() {
        return "SolicitudEmpleadoEjemplo{" +
                "nombreEmpleado='" + nombreEmpleado + '\'' +
                ", apellidoEmpleado='" + apellidoEmpleado + '\'' +
                ", tipoSolicitud='" + tipoSolicitud + '\'' +
                ", diasSolicitados=" + diasSolicitados +
                '}';
    }
}