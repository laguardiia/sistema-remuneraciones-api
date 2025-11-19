// src/main/java/py/edu/uc/lp32025/dto/BatchResponse.java
package py.edu.uc.lp32025.dto;

import java.util.List;

public class BatchResponse {
    private int guardados;
    private int errores;
    private List<String> mensajesError;

    public BatchResponse(int guardados, int errores, List<String> mensajesError) {
        this.guardados = guardados;
        this.errores = errores;
        this.mensajesError = mensajesError;
    }

    // Getters y Setters
    public int getGuardados() {
        return guardados;
    }

    public void setGuardados(int guardados) {
        this.guardados = guardados;
    }

    public int getErrores() {
        return errores;
    }

    public void setErrores(int errores) {
        this.errores = errores;
    }

    public List<String> getMensajesError() {
        return mensajesError;
    }

    public void setMensajesError(List<String> mensajesError) {
        this.mensajesError = mensajesError;
    }
}