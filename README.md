# Trabajo Práctico Lenguajes de Programación 3 - 2025

## Descripción

Este proyecto implementa una aplicación Spring Boot para la gestión de empleados, demostrando conceptos avanzados de programación orientada a objetos como herencia, polimorfismo, métodos abstractos, concretos y template, validación de datos, persistencia en batch y manejo de errores.

## Arquitectura Implementada

### Capa de Dominio
- **`Persona` (Clase Abstracta)**: Clase base que define la estructura común y comportamientos abstractos.
  - **`EmpleadoTiempoCompleto`**: Empleado con salario fijo y departamento.
  - **`EmpleadoPorHora`**: Empleado pagado por horas trabajadas.
  - **`Contratista`**: Profesional contratado por proyectos.

### Capa de Persistencia
- **Repositorios**: `PersonaRepository`, `EmpleadoTiempoCompletoRepository`, `EmpleadoPorHoraRepository`, `ContratistaRepository`.
- **Base de Datos**: H2 en modo archivo para persistencia local.

### Capa de Servicio
- **`EmpleadoTiempoCompletoService`**: Gestión de operaciones CRUD y batch para empleados de tiempo completo.
- **`RemuneracionesService`**: Cálculos de nómina, generación de reportes y filtrado de empleados.
- **`EmpleadoPorHoraService`**, **`ContratistaService`**: Servicios específicos para otros tipos de empleados.

### Capa de Controlador
- **`PersonaController`**: CRUD de personas base.
- **`EmpleadoTiempoCompletoController`**: CRUD y operaciones específicas para empleados de tiempo completo (impuestos, batch).
- **`EmpleadoPorHoraController`**, **`ContratistaController`**: CRUD para otros tipos de empleados.
- **`RemuneracionesController`**: Endpoints para cálculos de nómina, reportes y filtrado por nombre.

### Capa de DTOs
- `EmpleadoDTO`, `BatchResponse`, `ReporteEmpleadoDto`, `ImpuestosResponse`, `BatchEmpleadosRequest`, `BaseResponseDto`.

### Manejo de Errores
- **`GlobalExceptionHandler`**: Manejo centralizado de excepciones con respuestas estructuradas.

## Instrucciones de Ejecución

1.  **Prerrequisitos**:
    - Java 21
    - Maven 3.6.x o superior
    - Git

2.  **Clonar el Repositorio**:
    ```bash
    git clone <https://github.com/laguardiia/glaguardia-tp-lp3-2025>
    cd <lp32025>
    ```

3.  **Construir el Proyecto**:
    ```bash
    mvn clean install
    ```

4.  **Ejecutar la Aplicación**:
    ```bash
    mvn spring-boot:run
    ```
    La aplicación se ejecutará en `http://localhost:8080`.

5.  **Acceder a la Consola H2 (opcional)**:
    - URL: `http://localhost:8080/h2-console`
    - Driver Class: `org.h2.Driver`
    - JDBC URL: `jdbc:h2:file:./data/lp32025db`
    - User Name: `sa`
    - Password: `password` (o dejar vacío si no se configuró)

## Ejemplos de comandos cURL

### 1. Operación Batch con datos válidos
```bash
curl -X POST http://localhost:8080/api/empleados/batch \
  -H "Content-Type: application/json" \
  -d '[
    {
      "nombre": "Ana",
      "apellido": "Rodríguez",
      "fechaNacimiento": "1990-05-15",
      "numeroDocumento": "12345678",
      "salarioMensual": 3500000,
      "departamento": "Marketing"
    },
    {
      "nombre": "Pedro",
      "apellido": "López",
      "fechaNacimiento": "1988-12-20",
      "numeroDocumento": "87654321",
      "salarioMensual": 4000000,
      "departamento": "Ventas"
    }
  ]'
### 2. Operación Batch con datos inválidos
curl -X POST http://localhost:8080/api/empleados/batch \
  -H "Content-Type: application/json" \
  -d '[
    {
      "nombre": "Carlos",
      "apellido": "Mendoza",
      "fechaNacimiento": "1985-06-15",
      "numeroDocumento": "123456789012345678901", // Inválido (más de 20 dígitos)
      "salarioMensual": 2000000, // Inválido (menor al mínimo)
      "departamento": "Soporte"
    }
  ]'
### 3. Consulta de nómina total
curl -X GET http://localhost:8080/api/remuneraciones/total-remuneraciones

### 4. Filtrar personas por nombre
curl -X GET "http://localhost:8080/api/personas?nombre=ana"

### 5. Consultar impuestos de un empleado específico
curl -X GET http://localhost:8080/api/empleados/1/impuestos



