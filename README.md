# Trabajo Práctico Lenguajes de Programación 3 - 2025

## Descripción

Este proyecto implementa una aplicación Spring Boot robusta para la gestión de recursos humanos. Demuestra el dominio de conceptos avanzados de Programación Orientada a Objetos y diseño de software, incluyendo:

* **Herencia y Polimorfismo:** Jerarquías complejas en el modelo de dominio (Personas, Empleados, Gerentes) y en capas técnicas (Controladores y Mappers).
* **Interfaces y Contratos:** Uso de `Permisionable` y `Mapeable` para definir comportamientos transversales.
* **Patrones de Diseño:** Uso de *Template Method* en mappers y *Strategy* implícito en los cálculos de nómina.
* **Manejo de Errores Global:** Centralización de excepciones de negocio y técnicas.
* **Persistencia:** Operaciones CRUD y procesamiento Batch (lotes).

## Arquitectura del Sistema

### 1. Capa de Dominio (Modelo)
Jerarquía rica que modela la realidad del negocio:
* **`Persona` (Abstracta)**: Entidad base.
    * **`EmpleadoTiempoCompleto`**: Empleado regular.
        * **`Gerente`**: Extiende de Tiempo Completo. Implementa `PermisionableGerente` para aprobar solicitudes.
    * **`EmpleadoPorHora`**: Cálculo de salario basado en tarifa/hora.
    * **`Contratista`**: Pago por proyecto y gestión de contratos.
* **Interfaces**:
    * `Permisionable`: Define la capacidad de solicitar vacaciones/permisos.
    * `Mapeable`: Interfaz polimórfica implementada por `Persona`, `Vehiculo` y `Edificio` para geolocalización y avatares (Mock).

### 2. Capa de Controladores (REST)
Implementación de herencia para reutilización de código:
* **`BaseController`**: Clase abstracta con utilidades de respuesta HTTP estandarizadas y logging.
* **Controladores Específicos**: `GerenteController`, `EmpleadoTiempoCompletoController`, etc., heredan del base.
* **`RemuneracionesController`**: Gestión de nómina y lógica de negocio transversal.

### 3. Capa de Mappers (DTOs)
Jerarquía de conversión de datos para desacoplar la API de la base de datos:
* **`BaseMapper<E, D>`**: Interfaz genérica.
* **`AbstractBaseMapper`**: Clase base que maneja conversiones de Listas automáticamente.
* **Implementaciones**: `EmpleadoTiempoCompletoMapper`, `ExternalIntegrationMapper` (simulación de sistemas legacy), etc.

### 4. Servicios y Lógica de Negocio
* **`RemuneracionesService`**: Orquestador principal. Calcula nóminas polimórficas y valida reglas de negocio (ej. límite de 20 días de vacaciones).
* **`NominaUtils`**: Utilitario para reportes JSON y cálculos estadísticos.
* **Validaciones**: Reglas de negocio estrictas (ej. >20 días solo Gerentes) lanzando excepciones personalizadas (`DiasInsuficientesException`).

---

## Instrucciones de Ejecución

### Prerrequisitos
- Java 21
- Maven 3.6+
- Git

### Pasos para levantar el proyecto

1.  **Clonar el Repositorio**:
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    ```

2.  **Construir el Proyecto**:
    ```bash
    mvn clean install
    ```

3.  **Ejecutar**:
    ```bash
    mvn spring-boot:run
    ```
    La aplicación iniciará en `http://localhost:8080`.

4.  **Base de Datos (H2)**:
    * La base de datos se guarda en archivo local: `C:/data/lp32025db`.
    * Consola H2: `http://localhost:8080/h2-console`
    * JDBC URL: `jdbc:h2:file:C:/data/lp32025db`
    * User: `sa` / Password: `password`

---

## Guía de Pruebas y Endpoints (cURL)

A continuación, se presentan los comandos para probar el flujo completo del sistema, desde la carga de datos hasta la validación de excepciones de negocio.

### 1. Carga Inicial de Datos (Setup)

Primero, poblamos la base de datos utilizando la nueva jerarquía de controladores.

**1.1. Crear un Gerente (ID: 1)**
*Nota: Tiene autoridad para aprobar y derecho a >20 días de vacaciones.*
```bash
curl -X POST http://localhost:8080/api/gerentes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Carlos",
    "apellido": "Jefe",
    "fechaNacimiento": "1980-05-20",
    "numeroDocumento": "10001",
    "numeroEmpleado": "G001",
    "salarioMensual": 15000000,
    "departamento": "Dirección",
    "nivelAutoridad": 5
  }'
```

**1.2. Crear Empleado Tiempo Completo (ID: 2)**

```bash
curl -X POST http://localhost:8080/api/empleados \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ana",
    "apellido": "Dev",
    "fechaNacimiento": "1995-08-15",
    "numeroDocumento": "20001",
    "numeroEmpleado": "E001",
    "salarioMensual": 6500000,
    "departamento": "IT"
  }'
```

**1.3. Crear Empleado Por Hora (ID: 3)**

```bash
curl -X POST http://localhost:8080/api/empleados-por-hora \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Pedro",
    "apellido": "Parttime",
    "fechaNacimiento": "1998-01-10",
    "numeroDocumento": "30001",
    "numeroEmpleado": "H001",
    "tarifaPorHora": 50000,
    "horasTrabajadas": 45
  }'
```

**1.4. Crear Contratista (ID: 4)**

```bash
curl -X POST http://localhost:8080/api/contratistas \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Laura",
    "apellido": "Externa",
    "fechaNacimiento": "1990-03-20",
    "numeroDocumento": "40001",
    "numeroEmpleado": "C001",
    "montoPorProyecto": 5000000,
    "proyectosCompletados": 2,
    "fechaFinContrato": "2025-12-31"
  }'
```

**1.5. Carga Batch (Lotes)**

```bash
curl -X POST http://localhost:8080/api/empleados/batch \
  -H "Content-Type: application/json" \
  -d '[
    { "nombre": "Batch1", "apellido": "User", "fechaNacimiento": "1992-01-01", "numeroDocumento": "90001", "numeroEmpleado": "B001", "salarioMensual": 3000000, "departamento": "Ventas" },
    { "nombre": "Batch2", "apellido": "User", "fechaNacimiento": "1993-01-01", "numeroDocumento": "90002", "numeroEmpleado": "B002", "salarioMensual": 3100000, "departamento": "Ventas" }
  ]'
```

-----

### 2\. Pruebas de Polimorfismo y Mappers

**2.1. Listar Nómina Completa**
*Prueba la inyección de la jerarquía de mappers. Devuelve una lista polimórfica de DTOs.*

```bash
curl -X GET http://localhost:8080/api/remuneraciones/empleados
```

**2.2. Consultar Impuestos (Solo Tiempo Completo)**
*Calcula impuestos para el empleado ID 2.*

```bash
curl -X GET http://localhost:8080/api/empleados/2/impuestos
```

-----

### 3\. Pruebas de Reglas de Negocio y Excepciones

**3.1. Caso Éxito: Solicitud Válida**
*Empleado regular solicita 15 días (permitido).*

```bash
curl -X POST "http://localhost:8080/api/remuneraciones/empleados/2/solicitar-dias?tipoSolicitud=VACACIONES&fechaInicio=2025-07-01&fechaFin=2025-07-15"
```

**3.2. Caso Error: Excepción de Negocio (`DiasInsuficientesException`)**
*Regla: Empleado regular NO puede pedir \> 20 días. Debe retornar error 400 personalizado.*

```bash
curl -X POST "http://localhost:8080/api/remuneraciones/empleados/2/solicitar-dias?tipoSolicitud=VACACIONES&fechaInicio=2025-08-01&fechaFin=2025-08-25"
```

*Respuesta esperada:* JSON `ErrorDto` indicando que excede el límite.

**3.3. Caso Éxito: Privilegio de Gerente**
*El Gerente (ID 1) SÍ puede solicitar más de 20 días.*

```bash
curl -X POST "http://localhost:8080/api/remuneraciones/empleados/1/solicitar-dias?tipoSolicitud=VACACIONES&fechaInicio=2025-09-01&fechaFin=2025-09-25"
```

**3.4. Caso Error: Recurso No Encontrado (`EmpleadoNoEncontradoException`)**

```bash
curl -X POST "http://localhost:8080/api/remuneraciones/empleados/999/solicitar-dias?tipoSolicitud=VACACIONES&fechaInicio=2025-01-01&fechaFin=2025-01-10"
```

```
```