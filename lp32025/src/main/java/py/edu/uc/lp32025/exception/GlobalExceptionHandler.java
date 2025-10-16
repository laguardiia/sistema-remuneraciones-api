// src/main/java/py/edu/uc/lp32025/exception/GlobalExceptionHandler.java
package py.edu.uc.lp32025.exception;

import py.edu.uc.lp32025.dto.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FechaFuturaException.class)
    public ResponseEntity<BaseResponseDto> handleFechaFuturaException(FechaFuturaException ex) {
        BaseResponseDto response = new BaseResponseDto();
        response.setCodigoEstado(400);
        response.setMensajeErrorTecnico(ex.getMessage());
        response.setMensajeErrorUsuario("La fecha de nacimiento no puede ser en el futuro");
        response.setMensaje("Error: " + ex.getMessage()); //En está linea me tira error "setMensaje"

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        BaseResponseDto response = new BaseResponseDto();
        response.setCodigoEstado(400);
        response.setMensajeErrorTecnico(ex.getMessage());
        response.setMensajeErrorUsuario(ex.getMessage());
        response.setMensaje("Error de validación: " + ex.getMessage()); //En está linea me tira error "setMensaje"

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}