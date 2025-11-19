// src/main/java/py/edu/uc/lp32025/demo/DemoPermisionable.java
package py.edu.uc.lp32025.demo;

import py.edu.uc.lp32025.domain.*;
import py.edu.uc.lp32025.exception.PermisoDenegadoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class DemoPermisionable {

    public static void main(String[] args) {
        System.out.println("=== Demostración de la Interfaz Permisionable y PermisionableGerente ===\n");

        // Crear empleados subordinados
        EmpleadoTiempoCompleto empSub1 = new EmpleadoTiempoCompleto();
        empSub1.setId(10L); // Asegúrate de usar IDs únicos
        empSub1.setNombre("Ana");
        empSub1.setApellido("Gómez");
        empSub1.setFechaNacimiento(LocalDate.of(1992, 5, 10));
        empSub1.setNumeroDocumento("987654321");
        empSub1.setNumeroEmpleado("SUB001");
        empSub1.setSalarioMensual(new BigDecimal("2800000"));
        empSub1.setDepartamento("Ventas");

        EmpleadoTiempoCompleto empSub2 = new EmpleadoTiempoCompleto();
        empSub2.setId(11L);
        empSub2.setNombre("Pedro");
        empSub2.setApellido("López");
        empSub2.setFechaNacimiento(LocalDate.of(1989, 8, 22));
        empSub2.setNumeroDocumento("123456789");
        empSub2.setNumeroEmpleado("SUB002");
        empSub2.setSalarioMensual(new BigDecimal("3000000"));
        empSub2.setDepartamento("Marketing");

        // Crear un gerente
        Gerente gerente = new Gerente();
        gerente.setId(1L);
        gerente.setNombre("Luis");
        gerente.setApellido("Fernández");
        gerente.setFechaNacimiento(LocalDate.of(1980, 3, 15));
        gerente.setNumeroDocumento("112233445");
        gerente.setNumeroEmpleado("GER001");
        gerente.setSalarioMensual(new BigDecimal("5000000")); // Salario base, el cálculo lo sobrescribe
        gerente.setDepartamento("Gerencia");
        gerente.setNivelAutoridad(3); // Puede aprobar hasta 15 días (3 * 5)

        // Asignar subordinados al gerente
        gerente.setSubordinados(Arrays.asList(empSub1, empSub2));
        // Asegurarse que los subordinados también "saben" quién es su gerente (si se usa mappedBy)
        empSub1.setGerente(gerente); // Asumiendo que EmpleadoTiempoCompleto tiene un campo 'gerente'
        empSub2.setGerente(gerente); // Asumiendo que EmpleadoTiempoCompleto tiene un campo 'gerente'

        // --- Caso 1: Subordinado solicita vacaciones ---
        System.out.println("--- Caso 1: Subordinado solicita vacaciones ---");
        try {
            LocalDate[] rangoVacacionesSub = {LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 15)}; // 15 días
            empSub1.solicitarVacaciones(rangoVacacionesSub);
        } catch (PermisoDenegadoException e) {
            System.out.println("Error: " + e);
        }

        // --- Caso 2: Subordinado solicita permiso ---
        System.out.println("\n--- Caso 2: Subordinado solicita permiso (matrimonio) ---");
        try {
            LocalDate[] rangoPermisoSub = {LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 12)}; // 3 días
            empSub2.solicitarPermiso("MATRIMONIO", rangoPermisoSub, "Boda");
        } catch (PermisoDenegadoException e) {
            System.out.println("Error: " + e);
        }

        // --- Caso 3: Gerente aprueba solicitud de subordinado ---
        System.out.println("\n--- Caso 3: Gerente aprueba solicitud de subordinado ---");
        try {
            LocalDate[] rangoSolicitudSub = {LocalDate.of(2025, 8, 1), LocalDate.of(2025, 8, 5)}; // 5 días
            gerente.aprobarSolicitud(empSub1, "VACACIONES", rangoSolicitudSub, "Vacaciones planificadas");
        } catch (PermisoDenegadoException e) {
            System.out.println("Error al aprobar: " + e);
        }

        // --- Caso 4: Gerente intenta aprobar solicitud de alguien que no es su subordinado ---
        System.out.println("\n--- Caso 4: Gerente intenta aprobar solicitud de alguien que no es su subordinado ---");
        try {
            // Creamos un empleado que NO está en la lista de subordinados del gerente
            EmpleadoTiempoCompleto empNoSub = new EmpleadoTiempoCompleto();
            empNoSub.setId(99L); // ID diferente
            empNoSub.setNombre("Carlos");
            empNoSub.setApellido("Mendoza");
            empNoSub.setNumeroEmpleado("NO_SUB001");
            // ... otros setters ...

            LocalDate[] rangoSolicitudNoSub = {LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 3)};
            gerente.aprobarSolicitud(empNoSub, "PERMISO", rangoSolicitudNoSub, "Motivo cualquiera");
        } catch (PermisoDenegadoException e) {
            System.out.println("Error (esperado): " + e);
        }

        // --- Caso 5: Gerente intenta aprobar solicitud que excede su autoridad ---
        System.out.println("\n--- Caso 5: Gerente intenta aprobar solicitud que excede su autoridad ---");
        try {
            // Solicita 20 días, pero el gerente de nivel 3 solo puede aprobar hasta 15
            LocalDate[] rangoSolicitudLargo = {LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 20)}; // 20 días
            gerente.aprobarSolicitud(empSub2, "VACACIONES", rangoSolicitudLargo, "Vacaciones largas");
        } catch (PermisoDenegadoException e) {
            System.out.println("Error (esperado): " + e);
        }

        // --- Caso 6: Gerente solicita sus propias vacaciones ---
        System.out.println("\n--- Caso 6: Gerente solicita sus propias vacaciones ---");
        try {
            LocalDate[] rangoVacacionesGerente = {LocalDate.of(2025, 12, 20), LocalDate.of(2025, 12, 30)}; // 11 días
            gerente.solicitarVacaciones(rangoVacacionesGerente);
        } catch (PermisoDenegadoException e) {
            System.out.println("Error: " + e);
        }

        System.out.println("\n=== Fin de la Demostración ===");
    }
}