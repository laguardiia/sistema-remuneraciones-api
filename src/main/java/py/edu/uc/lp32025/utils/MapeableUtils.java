// src/main/java/py/edu/uc/lp32025/utils/MapeableUtils.java
package py.edu.uc.lp32025.utils;

import py.edu.uc.lp32025.domain.*;
import lombok.extern.slf4j.Slf4j; // Importar la anotación de Lombok

import java.util.List;

@Slf4j // ✅ Agregar la anotación de Lombok
public class MapeableUtils {

    // private static final Logger logger = LoggerFactory.getLogger(MapeableUtils.class); // ❌ Eliminar esta línea

    /**
     * Recorre una lista de elementos Mapeables e imprime su ubicación y avatar MOCK.
     * @param elementosMapeables Lista de objetos que implementan la interfaz Mapeable.
     */
    public static void imprimirReporteMapeable(List<Mapeable> elementosMapeables) {
        // ✅ Usar log (el campo generado por @Slf4j) en lugar de logger
        log.info("--- Inicio del Reporte Mapeable ---");

        for (Mapeable elemento : elementosMapeables) {
            // ✅ Usar log para información del elemento
            log.info("\n--- Procesando elemento (Tipo: {}) ---", elemento.getClass().getSimpleName());

            // Imprimir información específica según el tipo
            if (elemento instanceof Persona) {
                Persona persona = (Persona) elemento;
                log.info("  - Nombre: {} {}", persona.getNombre(), persona.getApellido());
            } else if (elemento instanceof Vehiculo) {
                Vehiculo veh = (Vehiculo) elemento;
                log.info("  - Detalles: {} {} ({})", veh.getMarca(), veh.getModelo(), veh.getPlaca());
            } else if (elemento instanceof Edificio) {
                Edificio edif = (Edificio) elemento;
                log.info("  - Detalles: {} en {} ({} pisos)", edif.getNombre(), edif.getDireccion(), edif.getPisos());
            }

            // Demostrar polimorfismo: Llamar a métodos de Mapeable
            PosicionGps ubicacion = elemento.ubicarElemento();
            Avatar avatar = elemento.obtenerImagen();

            // ✅ Usar log para la ubicación y avatar MOCK
            log.info("  - Ubicación MOCK: {}", ubicacion);
            log.info("  - Avatar MOCK: {}", avatar);
        }

        // ✅ Usar log para el final del reporte
        log.info("\n--- Fin del Reporte Mapeable ---");
    }
}