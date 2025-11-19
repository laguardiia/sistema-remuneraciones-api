// src/main/java/py/edu/uc/lp32025/exception/PermisoDenegadoException.java
package py.edu.uc.lp32025.exception;

public class PermisoDenegadoException extends Exception {

    private String motivo;
    private String tipoSolicitud; // "VACACIONES" o "PERMISO"
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String numeroEmpleado; // ✅ Nuevo campo

    public PermisoDenegadoException(String mensaje, String motivo, String tipoSolicitud, String nombreEmpleado, String apellidoEmpleado, String numeroEmpleado) {
        super(mensaje);
        this.motivo = motivo;
        this.tipoSolicitud = tipoSolicitud;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.numeroEmpleado = numeroEmpleado; // ✅ Asignar el número de empleado
    }

    public String getMotivo() {
        return motivo;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public String getNumeroEmpleado() { // ✅ Getter para el número de empleado
        return numeroEmpleado;
    }

    @Override
    public String toString() {
        return "PermisoDenegadoException{" +
                "mensaje='" + getMessage() + '\'' +
                ", motivo='" + motivo + '\'' +
                ", tipoSolicitud='" + tipoSolicitud + '\'' +
                ", empleado='" + nombreEmpleado + " " + apellidoEmpleado + '\'' +
                ", numeroEmpleado='" + numeroEmpleado + '\'' + // ✅ Incluir número de empleado en toString
                '}';
    }
}