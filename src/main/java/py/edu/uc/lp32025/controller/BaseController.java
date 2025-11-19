// src/main/java/py/edu/uc/lp32025/controller/BaseController.java
package py.edu.uc.lp32025.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public abstract class BaseController {

    // Logger disponible para todas las subclases
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // --- Métodos de Éxito ---

    protected <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    protected <T> ResponseEntity<T> created(URI uri, T body) {
        return ResponseEntity.created(uri).body(body);
    }

    protected ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    // --- Métodos de Error (Corregidos con Genéricos) ---

    @SuppressWarnings("unchecked")
    protected <T> ResponseEntity<T> notFound() {
        return (ResponseEntity<T>) ResponseEntity.notFound().build();
    }

    @SuppressWarnings("unchecked")
    protected <T> ResponseEntity<T> badRequest() {
        return (ResponseEntity<T>) ResponseEntity.badRequest().build();
    }
}