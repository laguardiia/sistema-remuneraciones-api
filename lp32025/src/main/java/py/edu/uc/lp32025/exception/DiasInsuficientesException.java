// src/main/java/py/edu/uc/lp32025/exception/DiasInsuficientesException.java
package py.edu.uc.lp32025.exception;

/**
 * Excepción lanzada cuando un empleado no tiene días disponibles para solicitar
 * vacaciones o permisos.
 */
public class DiasInsuficientesException extends Exception {

    private String tipoSolicitud; // "VACACIONES", "PERMISO"
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String numeroEmpleado;

    /**
     * Constructor para DiasInsuficientesException.
     *
     * @param mensaje Mensaje descriptivo del error.
     * @param tipoSolicitud Tipo de solicitud ("VACACIONES", "PERMISO").
     * @param nombreEmpleado Nombre del empleado.
     * @param apellidoEmpleado Apellido del empleado.
     * @param numeroEmpleado Número de empleado.
     */
    public DiasInsuficientesException(String mensaje, String tipoSolicitud, String nombreEmpleado, String apellidoEmpleado, String numeroEmpleado) {
        super(mensaje);
        this.tipoSolicitud = tipoSolicitud;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.numeroEmpleado = numeroEmpleado;
    }

    // Getters
    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    @Override
    public String toString() {
        return "DiasInsuficientesException{" +
                "mensaje='" + getMessage() + '\'' +
                ", tipoSolicitud='" + tipoSolicitud + '\'' +
                ", empleado='" + nombreEmpleado + " " + apellidoEmpleado + '\'' +
                ", numeroEmpleado='" + numeroEmpleado + '\'' +
                '}';
    }
}