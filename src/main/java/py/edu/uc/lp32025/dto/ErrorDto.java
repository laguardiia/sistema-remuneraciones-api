// src/main/java/py/edu/uc/lp32025/dto/ErrorDto.java
package py.edu.uc.lp32025.dto;

import java.time.LocalDateTime;

public class ErrorDto {
    private int codigoEstado;
    private String mensaje;
    private String mensajeUsuario; // Mensaje más amigable para el cliente
    private LocalDateTime timestamp;

    // Constructor vacío
    public ErrorDto() {
    }

    // Constructor con parámetros
    public ErrorDto(int codigoEstado, String mensaje, String mensajeUsuario) {
        this.codigoEstado = codigoEstado;
        this.mensaje = mensaje;
        this.mensajeUsuario = mensajeUsuario;
        this.timestamp = LocalDateTime.now(); // Fecha y hora actual
    }

    // Getters y Setters
    public int getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(int codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "codigoEstado=" + codigoEstado +
                ", mensaje='" + mensaje + '\'' +
                ", mensajeUsuario='" + mensajeUsuario + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}