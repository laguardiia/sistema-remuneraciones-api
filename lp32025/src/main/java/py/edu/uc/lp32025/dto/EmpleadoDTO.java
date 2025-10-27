// src/main/java/py/edu/uc/lp32025/dto/EmpleadoDTO.java
package py.edu.uc.lp32025.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmpleadoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String numeroDocumento;
    private String tipoEmpleado; // "EmpleadoTiempoCompleto", "EmpleadoPorHora", "Contratista"
    private BigDecimal salario; // Salario calculado
    private String informacionEspecifica; // Información específica según el tipo

    public EmpleadoDTO(Long id, String nombre, String apellido, LocalDate fechaNacimiento,
                       String numeroDocumento, String tipoEmpleado, BigDecimal salario,
                       String informacionEspecifica) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroDocumento = numeroDocumento;
        this.tipoEmpleado = tipoEmpleado;
        this.salario = salario;
        this.informacionEspecifica = informacionEspecifica;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getInformacionEspecifica() {
        return informacionEspecifica;
    }

    public void setInformacionEspecifica(String informacionEspecifica) {
        this.informacionEspecifica = informacionEspecifica;
    }
}