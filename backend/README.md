# Task Manager API

Una API REST para la gestión de tareas, desarrollada con **Spring Boot**, **JPA** y **H2/MySQL**. Permite crear, consultar, actualizar, eliminar y filtrar tareas según prioridad, año de vencimiento y estado de completitud. Perfecta para integrarla en aplicaciones web o móviles que necesiten una base sólida para el manejo de tareas.

---

## Características

- Crear tareas con validación.
- Obtener todas las tareas o filtrarlas por:
  - Prioridad
  - Año de vencimiento
  - Estado de completitud (completadas / no completadas)
- Ordenamiento personalizado (por campo y dirección).
- Ver detalles de una tarea por ID.
- Actualizar tareas existentes.
- Eliminar tareas.
- Arquitectura organizada con capas: `Controller`, `Service`, `Repository`.

---

## Arquitectura

- **Controller:** Gestiona las peticiones HTTP.
- **Service:** Contiene la lógica de negocio y coordina con el repositorio.
- **Repository:** Abstracción de la base de datos usando Spring Data JPA.
- **Model:** Representa las entidades (en este caso, `Task`).

---

## Tecnologías

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database (por defecto) o MySQL
- Maven

---
