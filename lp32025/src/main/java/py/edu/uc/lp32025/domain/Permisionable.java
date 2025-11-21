// src/main/java/py/edu/uc/lp32025/domain/Permisionable.java
package py.edu.uc.lp32025.domain;

import py.edu.uc.lp32025.exception.PermisoDenegadoException;
import java.time.LocalDate;

public interface Permisionable {

    // ✅ Agregar método para obtener número de empleado
    String getNumeroEmpleado();

    String getNombreEmpleado();
    String getApellidoEmpleado();

    void solicitarVacaciones(LocalDate[] rangoFechas) throws PermisoDenegadoException;
    void solicitarPermiso(String tipoPermiso, LocalDate[] rangoFechas, String justificacion) throws PermisoDenegadoException;
}