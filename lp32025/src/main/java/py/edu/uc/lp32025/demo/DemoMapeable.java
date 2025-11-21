// src/main/java/py/edu/uc/lp32025/demo/DemoMapeable.java
package py.edu.uc.lp32025.demo;

import py.edu.uc.lp32025.domain.Mapeable;
import py.edu.uc.lp32025.utils.MapeableFactory;
import py.edu.uc.lp32025.utils.MapeableUtils;
import lombok.extern.slf4j.Slf4j; // Importar la anotación de Lombok

import java.util.List;

@Slf4j // ✅ Agregar la anotación de Lombok
public class DemoMapeable {

    public static void main(String[] args) {
        // ✅ Usar log en lugar de logger
        log.info("=== Demostración de la Interfaz Mapeable ===");

        // Obtener la lista de Mapeables desde la fábrica
        List<Mapeable> elementosMapeables = MapeableFactory.crearEjemploMapeables();

        // Llamar al método de utilidad para recorrer e imprimir el reporte
        MapeableUtils.imprimirReporteMapeable(elementosMapeables);

        // ✅ Usar log en lugar de logger
        log.info("\n=== Fin de la Demostración ===");
    }
}