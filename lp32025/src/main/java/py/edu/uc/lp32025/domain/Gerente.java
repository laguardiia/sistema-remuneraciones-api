// src/main/java/py/edu/uc/lp32025/domain/Gerente.java
package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import py.edu.uc.lp32025.exception.PermisoDenegadoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gerentes")
public class Gerente extends EmpleadoTiempoCompleto implements PermisionableGerente { // ✅ Extiende EmpleadoTiempoCompleto e implementa PermisionableGerente

    @Column(name = "nivel_autoridad") // Ejemplo de campo específico de gerente
    private int nivelAutoridad; // Puede representar el nivel jerárquico o la cantidad de empleados a cargo

    @OneToMany(mappedBy = "gerente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmpleadoTiempoCompleto> subordinados; // Relación con empleados a cargo

    // Getters y Setters
    public int getNivelAutoridad() {
        return nivelAutoridad;
    }

    public void setNivelAutoridad(int nivelAutoridad) {
        this.nivelAutoridad = nivelAutoridad;
    }

    public List<EmpleadoTiempoCompleto> getSubordinados() {
        if (subordinados == null) {
            subordinados = new ArrayList<>();
        }
        return subordinados;
    }

    public void setSubordinados(List<EmpleadoTiempoCompleto> subordinados) {
        this.subordinados = subordinados;
    }

    // Constructor vacío (necesario para JPA)
    public Gerente() {
        super(); // Llama al constructor de la clase padre
    }

    // Constructor con parámetros (opcional, útil para creación manual)
    public Gerente(String nombre, String apellido, String numeroDocumento, String numeroEmpleado, BigDecimal salarioMensual, String departamento, int nivelAutoridad) {
        super();
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setNumeroDocumento(numeroDocumento);
        this.setNumeroEmpleado(numeroEmpleado);
        this.setSalarioMensual(salarioMensual);
        this.setDepartamento(departamento);
        this.nivelAutoridad = nivelAutoridad;
    }

    // ✅ Implementación del método exclusivo de gerentes
    @Override
    public void aprobarSolicitud(Permisionable empleadoSolicitante, String tipoSolicitud, LocalDate[] rangoFechas, String justificacion) throws PermisoDenegadoException {
        // Lógica de aprobación (simplificada)
        // Por ejemplo, un gerente puede aprobar solicitudes de subordinados directos
        // y solo hasta cierta cantidad de días basado en su nivel de autoridad.

        // Validar que el empleado solicitante sea un subordinado (simplificado)
        boolean esSubordinado = this.getSubordinados().stream()
                .anyMatch(sub -> sub.getId().equals(((Persona) empleadoSolicitante).getId()));

        if (!esSubordinado) {
            throw new PermisoDenegadoException(
                    "El empleado solicitante no es un subordinado directo.",
                    "El gerente solo puede aprobar solicitudes de sus subordinados directos.",
                    "APROBACION",
                    this.getNombreEmpleado(),
                    this.getApellidoEmpleado(),
                    this.getNumeroEmpleado()
            );
        }

        // Validar duración basada en nivel de autoridad (ejemplo simplificado)
        long diasSolicitados = java.time.temporal.ChronoUnit.DAYS.between(rangoFechas[0], rangoFechas[1]) + 1;
        if (diasSolicitados > (this.nivelAutoridad * 5)) { // Ej: nivel 1 = 5 días max, nivel 2 = 10 días max
            throw new PermisoDenegadoException(
                    "Duración de solicitud excede la autoridad del gerente.",
                    "El gerente de nivel " + this.nivelAutoridad + " puede aprobar hasta " + (this.nivelAutoridad * 5) + " días.",
                    "APROBACION",
                    this.getNombreEmpleado(),
                    this.getApellidoEmpleado(),
                    this.getNumeroEmpleado()
            );
        }

        // Si pasa las validaciones, se considera aprobada
        System.out.println("Solicitud de " + tipoSolicitud + " aprobada por el gerente " + this.getNombreEmpleado() + " para " + empleadoSolicitante.getNombreEmpleado() + ".");
    }

    // ✅ Opcional: Sobrescribir métodos heredados si se necesita lógica específica de gerente
    // Por ejemplo, calcular salario con bonificación de gerencia
    @Override
    public BigDecimal calcularSalario() {
        BigDecimal salarioBase = super.calcularSalario(); // Llama al cálculo de EmpleadoTiempoCompleto
        // Añadir bonificación de gerencia (ejemplo)
        BigDecimal bonificacion = salarioBase.multiply(BigDecimal.valueOf(0.10)); // 10%
        return salarioBase.add(bonificacion);
    }

    // ✅ Opcional: Sobrescribir para aplicar reglas específicas de gerencia
    @Override
    public void solicitarVacaciones(LocalDate[] rangoFechas) throws PermisoDenegadoException {
        // Pueden aplicarse reglas diferentes para vacaciones de gerentes
        // Por ejemplo, tal vez necesiten aprobación de un superior o tengan límites diferentes
        System.out.println("El gerente " + this.getNombreEmpleado() + " está solicitando vacaciones. Puede requerir aprobación adicional.");
        // Llama a la lógica general (o se puede sobrescribir por completo)
        super.solicitarVacaciones(rangoFechas);
    }

    @Override
    public void solicitarPermiso(String tipoPermiso, LocalDate[] rangoFechas, String justificacion) throws PermisoDenegadoException {
        // Pueden aplicarse reglas diferentes para permisos de gerentes
        System.out.println("El gerente " + this.getNombreEmpleado() + " está solicitando un permiso. Puede requerir aprobación adicional.");
        // Llama a la lógica general (o se puede sobrescribir por completo)
        super.solicitarPermiso(tipoPermiso, rangoFechas, justificacion);
    }
}