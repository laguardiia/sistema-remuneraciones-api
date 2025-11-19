// src/main/java/py/edu/uc/lp32025/service/RemuneracionesService.java
package py.edu.uc.lp32025.service;

import py.edu.uc.lp32025.domain.*;
import py.edu.uc.lp32025.dto.EmpleadoDTO;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.EmpleadoNoEncontradoException;
import py.edu.uc.lp32025.exception.PermisoDenegadoException;
import py.edu.uc.lp32025.mappers.ContratistaMapper;
import py.edu.uc.lp32025.mappers.EmpleadoPorHoraMapper;
import py.edu.uc.lp32025.mappers.EmpleadoTiempoCompletoMapper;
import py.edu.uc.lp32025.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.uc.lp32025.dto.ReporteEmpleadoDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class RemuneracionesService {

    private static final Logger logger = LoggerFactory.getLogger(RemuneracionesService.class);

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private EmpleadoTiempoCompletoRepository empleadoTiempoCompletoRepository;

    @Autowired
    private EmpleadoPorHoraRepository empleadoPorHoraRepository;

    @Autowired
    private ContratistaRepository contratistaRepository;

    // ✅ Inyección de los nuevos Mappers (Jerarquía)
    @Autowired
    private EmpleadoTiempoCompletoMapper tiempoCompletoMapper;

    @Autowired
    private EmpleadoPorHoraMapper porHoraMapper;

    @Autowired
    private ContratistaMapper contratistaMapper;


    public List<EmpleadoDTO> listarTodosLosEmpleados() {
        logger.info("Iniciando proceso de obtención de todos los empleados de la jerarquía como DTOs");

        List<EmpleadoDTO> todosLosEmpleados = new ArrayList<>();

        List<EmpleadoTiempoCompleto> empleadosTiempoCompleto = empleadoTiempoCompletoRepository.findAll();
        // ✅ Uso del mapper inyectado (método genérico heredado)
        todosLosEmpleados.addAll(tiempoCompletoMapper.mapToDtoList(empleadosTiempoCompleto));

        List<EmpleadoPorHora> empleadosPorHora = empleadoPorHoraRepository.findAll();
        // ✅ Uso del mapper inyectado
        todosLosEmpleados.addAll(porHoraMapper.mapToDtoList(empleadosPorHora));

        List<Contratista> contratistas = contratistaRepository.findAll();
        // ✅ Uso del mapper inyectado
        todosLosEmpleados.addAll(contratistaMapper.mapToDtoList(contratistas));

        logger.info("Total de empleados DTOs obtenidos: {}", todosLosEmpleados.size());
        return todosLosEmpleados;
    }

    public BigDecimal calcularTotalRemuneraciones() {
        logger.info("Calculando total de remuneraciones de todos los empleados");
        List<EmpleadoDTO> empleados = listarTodosLosEmpleados();
        BigDecimal totalRemuneraciones = BigDecimal.ZERO;

        for (EmpleadoDTO empleado : empleados) {
            BigDecimal salario = empleado.getSalario() != null ? empleado.getSalario() : BigDecimal.ZERO;
            totalRemuneraciones = totalRemuneraciones.add(salario);
        }
        return totalRemuneraciones;
    }

    public List<EmpleadoDTO> listarEmpleadosPorTipo(String tipoEmpleado) {
        switch (tipoEmpleado.toLowerCase()) {
            case "tiempocompleto":
                return tiempoCompletoMapper.mapToDtoList(empleadoTiempoCompletoRepository.findAll());
            case "porhora":
                return porHoraMapper.mapToDtoList(empleadoPorHoraRepository.findAll());
            case "contratista":
                return contratistaMapper.mapToDtoList(contratistaRepository.findAll());
            default:
                return new ArrayList<>();
        }
    }

    public List<Persona> filtrarPersonasPorNombre(String nombre) {
        return personaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<EmpleadoDTO> filtrarEmpleadosPorNombre(String nombre) {
        List<Persona> personas = filtrarPersonasPorNombre(nombre);
        List<EmpleadoDTO> dtos = new ArrayList<>();

        for (Persona persona : personas) {
            if (persona instanceof EmpleadoTiempoCompleto) {
                dtos.add(tiempoCompletoMapper.mapToDto((EmpleadoTiempoCompleto) persona));
            } else if (persona instanceof EmpleadoPorHora) {
                dtos.add(porHoraMapper.mapToDto((EmpleadoPorHora) persona));
            } else if (persona instanceof Contratista) {
                dtos.add(contratistaMapper.mapToDto((Contratista) persona));
            }
        }
        return dtos;
    }

    // ... (Otros métodos de reporte se mantienen igual si no usan mappers estáticos) ...

    public Map<String, Object> calcularNominaConDiasSolicitados() {
        Map<String, Object> nominaConDias = new HashMap<>();
        nominaConDias.put("totalRemuneraciones", calcularTotalRemuneraciones());
        // Simulación de datos
        nominaConDias.put("totalDiasSolicitados", 0L);
        return nominaConDias;
    }

    public void solicitarDias(Long empleadoId, String tipoSolicitud, LocalDate[] rangoFechas, String justificacion) throws DiasInsuficientesException, EmpleadoNoEncontradoException {
        Persona empleado = personaRepository.findById(empleadoId).orElse(null);

        if (empleado == null) {
            throw new EmpleadoNoEncontradoException("Empleado no encontrado", String.valueOf(empleadoId));
        }

        if (!(empleado instanceof Permisionable)) {
            throw new IllegalArgumentException("Este empleado no puede solicitar días.");
        }

        Permisionable permisionable = (Permisionable) empleado;
        long dias = java.time.temporal.ChronoUnit.DAYS.between(rangoFechas[0], rangoFechas[1]) + 1;

        // Regla de Negocio: Solo Gerentes > 20 días
        if (dias > 20 && !(empleado instanceof Gerente)) {
            throw new DiasInsuficientesException(
                    "Empleado regular no puede solicitar más de 20 días",
                    tipoSolicitud,
                    empleado.getNombre(),
                    empleado.getApellido(),
                    permisionable.getNumeroEmpleado()
            );
        }

        try {
            if ("VACACIONES".equalsIgnoreCase(tipoSolicitud)) {
                permisionable.solicitarVacaciones(rangoFechas);
            } else {
                permisionable.solicitarPermiso(tipoSolicitud, rangoFechas, justificacion);
            }
        } catch (PermisoDenegadoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}