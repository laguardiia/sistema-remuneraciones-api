// src/main/java/py/edu/uc/lp32025/utils/MapeableFactory.java
package py.edu.uc.lp32025.utils;

import py.edu.uc.lp32025.domain.*;
import lombok.extern.slf4j.Slf4j; // Importar la anotación de Lombok

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j // ✅ Agregar la anotación de Lombok
public class MapeableFactory {

    // private static final Logger logger = LoggerFactory.getLogger(MapeableFactory.class); // ❌ Eliminar esta línea

    /**
     * Crea una lista de ejemplo de objetos Mapeables para demostración.
     * @return Lista de objetos que implementan la interfaz Mapeable.
     */
    public static List<Mapeable> crearEjemploMapeables() {
        // ✅ Usar log en lugar de logger
        log.info("Creando lista de ejemplo de Mapeables...");

        // Crear instancias de diferentes tipos de empleados (todos implementan Mapeable indirectamente)
        EmpleadoTiempoCompleto empTC = new EmpleadoTiempoCompleto();
        empTC.setId(1L);
        empTC.setNombre("Carlos");
        empTC.setApellido("Mendoza");
        empTC.setFechaNacimiento(LocalDate.of(1985, 6, 15));
        empTC.setNumeroDocumento("11223344");
        empTC.setSalarioMensual(new BigDecimal("3500000"));
        empTC.setDepartamento("Desarrollo");

        EmpleadoPorHora empPH = new EmpleadoPorHora();
        empPH.setId(2L);
        empPH.setNombre("Ana");
        empPH.setApellido("Rodríguez");
        empPH.setFechaNacimiento(LocalDate.of(1990, 3, 15));
        empPH.setNumeroDocumento("87654321");
        empPH.setTarifaPorHora(new BigDecimal("35000"));
        empPH.setHorasTrabajadas(45);

        Contratista cont = new Contratista();
        cont.setId(3L);
        cont.setNombre("Laura");
        cont.setApellido("Pérez");
        cont.setFechaNacimiento(LocalDate.of(1987, 11, 20));
        cont.setNumeroDocumento("55667788");
        cont.setMontoPorProyecto(new BigDecimal("2000000"));
        cont.setProyectosCompletados(2);
        cont.setFechaFinContrato(LocalDate.of(2025, 12, 31));

        // Crear instancias de Vehiculo y Edificio (también implementan Mapeable)
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(4L);
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Corolla");
        vehiculo.setPlaca("ABC123");
        vehiculo.setTipo("auto");

        Edificio edificio = new Edificio();
        edificio.setId(5L);
        edificio.setNombre("Torre A");
        edificio.setDireccion("Calle Principal 123");
        edificio.setPisos(10);

        // Devolver la lista de Mapeables (polimorfismo en acción)
        List<Mapeable> lista = Arrays.asList(empTC, empPH, cont, vehiculo, edificio);
        // ✅ Usar log en lugar de logger
        log.info("Lista de ejemplo de Mapeables creada exitosamente. Tamaño: {}", lista.size());
        return lista;
    }
}