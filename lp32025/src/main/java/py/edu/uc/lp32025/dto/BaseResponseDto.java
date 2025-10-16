// src/main/java/py/edu/uc/lp32025/dto/BaseResponseDto.java
package py.edu.uc.lp32025.dto;

public class BaseResponseDto {

    private int codigoEstado;
    private String mensajeErrorTecnico;
    private String mensajeErrorUsuario;
    private String mensaje;

    // Constructor vacío
    public BaseResponseDto() {
        this.codigoEstado = 200;
    }

    // Constructor con parámetros
    public BaseResponseDto(int codigoEstado, String mensajeErrorTecnico, String mensajeErrorUsuario) {
        this.codigoEstado = codigoEstado;
        this.mensajeErrorTecnico = mensajeErrorTecnico;
        this.mensajeErrorUsuario = mensajeErrorUsuario;
    }

    // Getters y Setters
    public int getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(int codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getMensajeErrorTecnico() {
        return mensajeErrorTecnico;
    }

    public void setMensajeErrorTecnico(String mensajeErrorTecnico) {
        this.mensajeErrorTecnico = mensajeErrorTecnico;
    }

    public String getMensajeErrorUsuario() {
        return mensajeErrorUsuario;
    }

    public void setMensajeErrorUsuario(String mensajeErrorUsuario) {
        this.mensajeErrorUsuario = mensajeErrorUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "BaseResponseDto{" +
                "codigoEstado=" + codigoEstado +
                ", mensajeErrorTecnico='" + mensajeErrorTecnico + '\'' +
                ", mensajeErrorUsuario='" + mensajeErrorUsuario + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}