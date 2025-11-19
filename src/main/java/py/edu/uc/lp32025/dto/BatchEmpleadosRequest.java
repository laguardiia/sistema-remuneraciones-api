// src/main/java/py/edu/uc/lp32025/dto/BatchEmpleadosRequest.java
package py.edu.uc.lp32025.dto;

import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;
import py.edu.uc.lp32025.domain.EmpleadoPorHora;
import py.edu.uc.lp32025.domain.Contratista;

import java.util.List;

public class BatchEmpleadosRequest {
    private List<EmpleadoTiempoCompleto> empleadosTiempoCompleto;
    private List<EmpleadoPorHora> empleadosPorHora;
    private List<Contratista> contratistas;

    // Constructor vacío
    public BatchEmpleadosRequest() {
    }

    // Constructor con parámetros
    public BatchEmpleadosRequest(List<EmpleadoTiempoCompleto> empleadosTiempoCompleto,
                                 List<EmpleadoPorHora> empleadosPorHora,
                                 List<Contratista> contratistas) {
        this.empleadosTiempoCompleto = empleadosTiempoCompleto;
        this.empleadosPorHora = empleadosPorHora;
        this.contratistas = contratistas;
    }

    // Getters y Setters
    public List<EmpleadoTiempoCompleto> getEmpleadosTiempoCompleto() {
        return empleadosTiempoCompleto;
    }

    public void setEmpleadosTiempoCompleto(List<EmpleadoTiempoCompleto> empleadosTiempoCompleto) {
        this.empleadosTiempoCompleto = empleadosTiempoCompleto;
    }

    public List<EmpleadoPorHora> getEmpleadosPorHora() {
        return empleadosPorHora;
    }

    public void setEmpleadosPorHora(List<EmpleadoPorHora> empleadosPorHora) {
        this.empleadosPorHora = empleadosPorHora;
    }

    public List<Contratista> getContratistas() {
        return contratistas;
    }

    public void setContratistas(List<Contratista> contratistas) {
        this.contratistas = contratistas;
    }

    @Override
    public String toString() {
        return "BatchEmpleadosRequest{" +
                "empleadosTiempoCompleto=" + (empleadosTiempoCompleto != null ? empleadosTiempoCompleto.size() : 0) +
                ", empleadosPorHora=" + (empleadosPorHora != null ? empleadosPorHora.size() : 0) +
                ", contratistas=" + (contratistas != null ? contratistas.size() : 0) +
                '}';
    }
}