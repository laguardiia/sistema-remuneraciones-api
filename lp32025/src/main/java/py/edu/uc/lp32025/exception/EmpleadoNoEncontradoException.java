// src/main/java/py/edu/uc/lp32025/exception/EmpleadoNoEncontradoException.java
package py.edu.uc.lp32025.exception;

/**
 * Excepción lanzada cuando no se encuentra un empleado con un identificador específico.
 */
public class EmpleadoNoEncontradoException extends RuntimeException {

    private String numeroEmpleado; // O ID, o cualquier otro identificador único

    /**
     * Constructor para EmpleadoNoEncontradoException.
     *
     * @param mensaje Mensaje descriptivo del error.
     */
    public EmpleadoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor para EmpleadoNoEncontradoException que incluye el número de empleado.
     *
     * @param mensaje Mensaje descriptivo del error.
     * @param numeroEmpleado Número de empleado que no se encontró.
     */
    public EmpleadoNoEncontradoException(String mensaje, String numeroEmpleado) {
        super(mensaje);
        this.numeroEmpleado = numeroEmpleado;
    }

    // Getter
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    @Override
    public String toString() {
        return "EmpleadoNoEncontradoException{" +
                "mensaje='" + getMessage() + '\'' +
                ", numeroEmpleado='" + numeroEmpleado + '\'' +
                '}';
    }
}