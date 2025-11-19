// src/main/java/py/edu/uc/lp32025/repository/PersonaRepository.java
package py.edu.uc.lp32025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.edu.uc.lp32025.domain.Persona;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    // ✅ Método para buscar personas por nombre (case insensitive)
    List<Persona> findByNombreContainingIgnoreCase(String nombre);

    // ✅ Método alternativo usando JPQL
    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Persona> buscarPorNombreContaining(@Param("nombre") String nombre);
}