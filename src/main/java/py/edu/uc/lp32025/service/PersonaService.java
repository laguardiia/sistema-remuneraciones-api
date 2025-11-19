// src/main/java/py/edu/uc/lp32025/service/PersonaService.java
package py.edu.uc.lp32025.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.domain.Persona;
import py.edu.uc.lp32025.repository.PersonaRepository;
import py.edu.uc.lp32025.exception.FechaFuturaException;

import java.time.LocalDate;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public Persona guardarPersona(Persona persona) {
        // Validación: fecha de nacimiento no puede estar en el futuro
        LocalDate fechaNacimiento = persona.getFechaNacimiento();
        LocalDate fechaActual = LocalDate.now();

        if (fechaNacimiento != null && fechaNacimiento.isAfter(fechaActual)) {
            throw new FechaFuturaException("La fecha de nacimiento no puede ser en el futuro");
        }

        return personaRepository.save(persona);
    }
}