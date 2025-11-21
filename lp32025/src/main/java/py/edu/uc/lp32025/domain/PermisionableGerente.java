// src/main/java/py/edu/uc/lp32025/domain/PermisionableGerente.java
package py.edu.uc.lp32025.domain;

import py.edu.uc.lp32025.exception.PermisoDenegadoException;

import java.time.LocalDate;

public interface PermisionableGerente extends Permisionable { // ✅ Extiende Permisionable

    /**
     * Aprueba una solicitud de vacaciones o permiso de un subordinado.
     * Este método es exclusivo de los gerentes.
     *
     * @param empleadoSolicitante El empleado que solicitó el permiso/vacaciones.
     * @param tipoSolicitud El tipo de solicitud ("VACACIONES" o "PERMISO").
     * @param rangoFechas El rango de fechas de la solicitud.
     * @param justificacion La justificación de la solicitud (si aplica).
     * @throws PermisoDenegadoException Si el gerente no puede aprobar la solicitud (por ejemplo, supera su autoridad).
     */
    void aprobarSolicitud(Permisionable empleadoSolicitante, String tipoSolicitud, LocalDate[] rangoFechas, String justificacion) throws PermisoDenegadoException;
}