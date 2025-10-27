// src/main/java/py/edu/uc/lp32025/service/RemuneracionesService.java
package py.edu.uc.lp32025.service;

import py.edu.uc.lp32025.domain.*;
import py.edu.uc.lp32025.dto.EmpleadoDTO;
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

    /**
     * Método para listar todos los empleados de la jerarquía como DTOs
     */
    public List<EmpleadoDTO> listarTodosLosEmpleados() {
        logger.info("Iniciando proceso de obtención de todos los empleados de la jerarquía como DTOs");

        List<EmpleadoDTO> todosLosEmpleados = new ArrayList<>();

        // Obtener todos los empleados de tiempo completo
        List<EmpleadoTiempoCompleto> empleadosTiempoCompleto = empleadoTiempoCompletoRepository.findAll();
        logger.info("Se encontraron {} empleados de tiempo completo", empleadosTiempoCompleto.size());

        for (EmpleadoTiempoCompleto empleado : empleadosTiempoCompleto) {
            EmpleadoDTO dto = new EmpleadoDTO(
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getFechaNacimiento(),
                    empleado.getNumeroDocumento(),
                    "EmpleadoTiempoCompleto",
                    empleado.calcularSalario(),
                    "Departamento: " + empleado.getDepartamento() +
                            ", Salario Mensual: " + empleado.getSalarioMensual() +
                            ", Salario con Descuento: " + empleado.calcularSalario() +
                            ", Deducciones: " + empleado.calcularDeducciones() +
                            ", Impuestos: " + empleado.calcularImpuestos()
            );
            todosLosEmpleados.add(dto);
        }

        // Obtener todos los empleados por hora
        List<EmpleadoPorHora> empleadosPorHora = empleadoPorHoraRepository.findAll();
        logger.info("Se encontraron {} empleados por hora", empleadosPorHora.size());

        for (EmpleadoPorHora empleado : empleadosPorHora) {
            EmpleadoDTO dto = new EmpleadoDTO(
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getFechaNacimiento(),
                    empleado.getNumeroDocumento(),
                    "EmpleadoPorHora",
                    empleado.calcularSalario(),
                    "Tarifa por Hora: " + empleado.getTarifaPorHora() +
                            ", Horas Trabajadas: " + empleado.getHorasTrabajadas() +
                            ", Salario Total: " + empleado.calcularSalario() +
                            ", Deducciones: " + empleado.calcularDeducciones() +
                            ", Impuestos: " + empleado.calcularImpuestos()
            );
            todosLosEmpleados.add(dto);
        }

        // Obtener todos los contratistas
        List<Contratista> contratistas = contratistaRepository.findAll();
        logger.info("Se encontraron {} contratistas", contratistas.size());

        for (Contratista empleado : contratistas) {
            EmpleadoDTO dto = new EmpleadoDTO(
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getFechaNacimiento(),
                    empleado.getNumeroDocumento(),
                    "Contratista",
                    empleado.calcularSalario(),
                    "Monto por Proyecto: " + empleado.getMontoPorProyecto() +
                            ", Proyectos Completados: " + empleado.getProyectosCompletados() +
                            ", Fecha Fin Contrato: " + empleado.getFechaFinContrato() +
                            ", Salario Total: " + empleado.calcularSalario() +
                            ", Deducciones: " + empleado.calcularDeducciones() +
                            ", Impuestos: " + empleado.calcularImpuestos()
            );
            todosLosEmpleados.add(dto);
        }

        logger.info("Total de empleados DTOs obtenidos: {}", todosLosEmpleados.size());
        return todosLosEmpleados;
    }

    /**
     * Método para calcular el total de remuneraciones de todos los empleados
     */
    public BigDecimal calcularTotalRemuneraciones() {
        logger.info("Calculando total de remuneraciones de todos los empleados");

        List<EmpleadoDTO> empleados = listarTodosLosEmpleados();
        BigDecimal totalRemuneraciones = BigDecimal.ZERO;

        for (EmpleadoDTO empleado : empleados) {
            BigDecimal salario = empleado.getSalario();
            totalRemuneraciones = totalRemuneraciones.add(salario);
            logger.debug("Empleado: {} {} - Salario: {}",
                    empleado.getNombre(), empleado.getApellido(), salario);
        }

        logger.info("Total de remuneraciones calculado: {}", totalRemuneraciones);
        return totalRemuneraciones;
    }

    /**
     * Método para listar empleados DTO filtrados por tipo
     */
    public List<EmpleadoDTO> listarEmpleadosPorTipo(String tipoEmpleado) {
        logger.info("Obteniendo empleados DTO del tipo: {}", tipoEmpleado);

        switch (tipoEmpleado.toLowerCase()) {
            case "tiempocompleto":
                List<EmpleadoTiempoCompleto> tiempoCompleto = empleadoTiempoCompletoRepository.findAll();
                List<EmpleadoDTO> dtosTiempoCompleto = new ArrayList<>();
                for (EmpleadoTiempoCompleto empleado : tiempoCompleto) {
                    EmpleadoDTO dto = new EmpleadoDTO(
                            empleado.getId(),
                            empleado.getNombre(),
                            empleado.getApellido(),
                            empleado.getFechaNacimiento(),
                            empleado.getNumeroDocumento(),
                            "EmpleadoTiempoCompleto",
                            empleado.calcularSalario(),
                            "Departamento: " + empleado.getDepartamento() +
                                    ", Salario Mensual: " + empleado.getSalarioMensual() +
                                    ", Salario con Descuento: " + empleado.calcularSalario() +
                                    ", Deducciones: " + empleado.calcularDeducciones() +
                                    ", Impuestos: " + empleado.calcularImpuestos()
                    );
                    dtosTiempoCompleto.add(dto);
                }
                logger.info("Se encontraron {} empleados de tiempo completo DTO", dtosTiempoCompleto.size());
                return dtosTiempoCompleto;

            case "porhora":
                List<EmpleadoPorHora> porHora = empleadoPorHoraRepository.findAll();
                List<EmpleadoDTO> dtosPorHora = new ArrayList<>();
                for (EmpleadoPorHora empleado : porHora) {
                    EmpleadoDTO dto = new EmpleadoDTO(
                            empleado.getId(),
                            empleado.getNombre(),
                            empleado.getApellido(),
                            empleado.getFechaNacimiento(),
                            empleado.getNumeroDocumento(),
                            "EmpleadoPorHora",
                            empleado.calcularSalario(),
                            "Tarifa por Hora: " + empleado.getTarifaPorHora() +
                                    ", Horas Trabajadas: " + empleado.getHorasTrabajadas() +
                                    ", Salario Total: " + empleado.calcularSalario() +
                                    ", Deducciones: " + empleado.calcularDeducciones() +
                                    ", Impuestos: " + empleado.calcularImpuestos()
                    );
                    dtosPorHora.add(dto);
                }
                logger.info("Se encontraron {} empleados por hora DTO", dtosPorHora.size());
                return dtosPorHora;

            case "contratista":
                List<Contratista> contratistas = contratistaRepository.findAll();
                List<EmpleadoDTO> dtosContratista = new ArrayList<>();
                for (Contratista empleado : contratistas) {
                    EmpleadoDTO dto = new EmpleadoDTO(
                            empleado.getId(),
                            empleado.getNombre(),
                            empleado.getApellido(),
                            empleado.getFechaNacimiento(),
                            empleado.getNumeroDocumento(),
                            "Contratista",
                            empleado.calcularSalario(),
                            "Monto por Proyecto: " + empleado.getMontoPorProyecto() +
                                    ", Proyectos Completados: " + empleado.getProyectosCompletados() +
                                    ", Fecha Fin Contrato: " + empleado.getFechaFinContrato() +
                                    ", Salario Total: " + empleado.calcularSalario() +
                                    ", Deducciones: " + empleado.calcularDeducciones() +
                                    ", Impuestos: " + empleado.calcularImpuestos()
                    );
                    dtosContratista.add(dto);
                }
                logger.info("Se encontraron {} contratistas DTO", dtosContratista.size());
                return dtosContratista;

            default:
                logger.warn("Tipo de empleado no válido: {}", tipoEmpleado);
                return new ArrayList<>();
        }
    }

    // ✅ Nuevo método para filtrar personas por nombre (case insensitive)
    public List<Persona> filtrarPersonasPorNombre(String nombre) {
        logger.info("Filtrando personas por nombre (case insensitive): {}", nombre);

        List<Persona> personas = personaRepository.findByNombreContainingIgnoreCase(nombre);
        logger.info("Se encontraron {} personas con nombre que contiene: {}", personas.size(), nombre);

        return personas;
    }

    // ✅ Nuevo método para filtrar empleados DTO por nombre (case insensitive)
    public List<EmpleadoDTO> filtrarEmpleadosPorNombre(String nombre) {
        logger.info("Filtrando empleados DTO por nombre (case insensitive): {}", nombre);

        List<Persona> personas = filtrarPersonasPorNombre(nombre);
        List<EmpleadoDTO> dtos = new ArrayList<>();

        for (Persona persona : personas) {
            // Determinar el tipo específico y construir el DTO correspondiente
            String tipoEmpleado = "Persona";
            String informacionEspecifica = "Información no disponible";

            if (persona instanceof EmpleadoTiempoCompleto) {
                EmpleadoTiempoCompleto emp = (EmpleadoTiempoCompleto) persona;
                tipoEmpleado = "EmpleadoTiempoCompleto";
                informacionEspecifica = "Departamento: " + emp.getDepartamento() +
                        ", Salario Mensual: " + emp.getSalarioMensual() +
                        ", Salario con Descuento: " + emp.calcularSalario() +
                        ", Deducciones: " + emp.calcularDeducciones() +
                        ", Impuestos: " + emp.calcularImpuestos();
            } else if (persona instanceof EmpleadoPorHora) {
                EmpleadoPorHora emp = (EmpleadoPorHora) persona;
                tipoEmpleado = "EmpleadoPorHora";
                informacionEspecifica = "Tarifa por Hora: " + emp.getTarifaPorHora() +
                        ", Horas Trabajadas: " + emp.getHorasTrabajadas() +
                        ", Salario Total: " + emp.calcularSalario() +
                        ", Deducciones: " + emp.calcularDeducciones() +
                        ", Impuestos: " + emp.calcularImpuestos();
            } else if (persona instanceof Contratista) {
                Contratista emp = (Contratista) persona;
                tipoEmpleado = "Contratista";
                informacionEspecifica = "Monto por Proyecto: " + emp.getMontoPorProyecto() +
                        ", Proyectos Completados: " + emp.getProyectosCompletados() +
                        ", Fecha Fin Contrato: " + emp.getFechaFinContrato() +
                        ", Salario Total: " + emp.calcularSalario() +
                        ", Deducciones: " + emp.calcularDeducciones() +
                        ", Impuestos: " + emp.calcularImpuestos();
            }

            EmpleadoDTO dto = new EmpleadoDTO(
                    persona.getId(),
                    persona.getNombre(),
                    persona.getApellido(),
                    persona.getFechaNacimiento(),
                    persona.getNumeroDocumento(),
                    tipoEmpleado,
                    persona.calcularSalario(),
                    informacionEspecifica
            );
            dtos.add(dto);
        }

        logger.info("Se encontraron {} empleados DTO con nombre que contiene: {}", dtos.size(), nombre);
        return dtos;
    }

    // ✅ 4.2 Método calcularNominaTotal() que retorne Map<String, BigDecimal>
    public Map<String, BigDecimal> calcularNominaTotal() {
        logger.info("Calculando nómina total por tipo de empleado");

        Map<String, BigDecimal> nominaPorTipo = new HashMap<>();

        // Calcular nómina para EmpleadoTiempoCompleto
        List<EmpleadoTiempoCompleto> empleadosTiempoCompleto = empleadoTiempoCompletoRepository.findAll();
        BigDecimal totalTiempoCompleto = empleadosTiempoCompleto.stream()
                .map(EmpleadoTiempoCompleto::calcularSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        nominaPorTipo.put("EmpleadoTiempoCompleto", totalTiempoCompleto);
        logger.debug("Total nómina EmpleadoTiempoCompleto: {}", totalTiempoCompleto);

        // Calcular nómina para EmpleadoPorHora
        List<EmpleadoPorHora> empleadosPorHora = empleadoPorHoraRepository.findAll();
        BigDecimal totalPorHora = empleadosPorHora.stream()
                .map(EmpleadoPorHora::calcularSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        nominaPorTipo.put("EmpleadoPorHora", totalPorHora);
        logger.debug("Total nómina EmpleadoPorHora: {}", totalPorHora);

        // Calcular nómina para Contratista
        List<Contratista> contratistas = contratistaRepository.findAll();
        BigDecimal totalContratista = contratistas.stream()
                .map(Contratista::calcularSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        nominaPorTipo.put("Contratista", totalContratista);
        logger.debug("Total nómina Contratista: {}", totalContratista);

        logger.info("Cálculo de nómina total completado: {}", nominaPorTipo);
        return nominaPorTipo;
    }

    // ✅ 4.3 Método generarReporteCompleto() que use polimorfismo
    public List<String> generarReporteCompleto() {
        logger.info("Generando reporte completo de empleados usando polimorfismo");

        List<String> reporte = new ArrayList<>();

        // Obtener todas las personas (empleados de todos los tipos)
        List<Persona> todasLasPersonas = personaRepository.findAll();

        for (Persona persona : todasLasPersonas) {
            logger.debug("Procesando persona: {} {} (Tipo: {})",
                    persona.getNombre(), persona.getApellido(), persona.getClass().getSimpleName());

            // ✅ Uso de polimorfismo para llamar a métodos específicos
            String informacionCompleta = persona.obtenerInformacionCompleta();
            BigDecimal impuestos = persona.calcularImpuestos();
            boolean datosValidos = persona.validarDatosEspecificos();

            String lineaReporte = String.format(
                    "Empleado: %s - Info: %s - Impuestos: %s - Datos Válidos: %s",
                    persona.getClass().getSimpleName() + " " + persona.getNombre() + " " + persona.getApellido(),
                    informacionCompleta,
                    impuestos,
                    datosValidos ? "SÍ" : "NO"
            );

            reporte.add(lineaReporte);
            logger.debug("Agregada línea de reporte: {}", lineaReporte);
        }

        logger.info("Reporte completo generado con {} entradas", reporte.size());
        return reporte;
    }
    // ✅ Nuevo método que devuelve ReporteEmpleadoDto en lugar de String
    public List<ReporteEmpleadoDto> generarReporteCompletoDetallado() {
        logger.info("Generando reporte completo detallado de empleados usando polimorfismo");

        List<ReporteEmpleadoDto> reporte = new ArrayList<>();

        // Obtener todas las personas (empleados de todos los tipos)
        List<Persona> todasLasPersonas = personaRepository.findAll();

        for (Persona persona : todasLasPersonas) {
            logger.debug("Procesando persona: {} {} (Tipo: {})",
                    persona.getNombre(), persona.getApellido(), persona.getClass().getSimpleName());

            // ✅ Uso de polimorfismo para llamar a métodos específicos
            String informacionCompleta = persona.obtenerInformacionCompleta();
            BigDecimal impuestos = persona.calcularImpuestos();
            boolean datosValidos = persona.validarDatosEspecificos();

            ReporteEmpleadoDto dto = new ReporteEmpleadoDto(
                    persona.getId(),
                    persona.getNombre(),
                    persona.getApellido(),
                    persona.getClass().getSimpleName(),
                    informacionCompleta,
                    persona.calcularSalario(),
                    impuestos,
                    datosValidos,
                    persona.getFechaNacimiento(),
                    persona.getNumeroDocumento()
            );

            reporte.add(dto);
            logger.debug("Agregado DTO de reporte: {}", dto);
        }

        logger.info("Reporte completo detallado generado con {} entradas", reporte.size());
        return reporte;
    }
    public List<Persona> listarTodasLasPersonas() {
        List<Persona> todasLasPersonas = new ArrayList<>();

        todasLasPersonas.addAll(empleadoTiempoCompletoRepository.findAll());
        todasLasPersonas.addAll(empleadoPorHoraRepository.findAll());
        todasLasPersonas.addAll(contratistaRepository.findAll());

        return todasLasPersonas;
    }
}