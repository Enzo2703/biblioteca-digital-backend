# Biblioteca Digital - Backend

Este repositorio contiene el **backend del proyecto Biblioteca Digital**, desarrollado con **Spring Boot** y **PostgreSQL**.  

---

## 🔹 Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

---

## 🔹 Estructura del proyecto

src/
├─ main/
│ ├─ java/com/biblioteca/biblioteca_digital/
│ │ ├─ config/ # Configuración de seguridad
│ │ ├─ controller/ # Controladores REST
│ │ ├─ dto/ # Data Transfer Objects
│ │ ├─ enums/ # Enumeraciones (categorías, roles, estado de préstamos)
│ │ ├─ exception/ # Clases de excepción personalizada
│ │ ├─ mapper/ # Clases de mapeo (DTO ↔ Entity)
│ │ ├─ model/ # Entidades (Book, User, Author, Loan)
│ │ ├─ repository/ # Repositorios JPA
│ │ └─ service/ # Servicios
│ └─ resources/
│ └─ application.properties


---

## 🔹 Funcionalidades implementadas

- CRUD completo para **libros**, **autores**, **usuarios** y **préstamos**.
- API REST para consumir los datos.
- Mapeo con DTOs y mappers para no exponer entidades directamente.
- Búsqueda de libros por:
  - Título (`findByTitleContainingIgnoreCase`)
  - Categoría (`findByCategory`)
  - ISBN (`findByIsbn`)
- Seguridad con Spring Security (login y roles) — todavía en pruebas.

---

## 🔹 Funcionalidades pendientes / errores conocidos

- **Login / autenticación:** Actualmente no está funcionando correctamente. Se delegará a otro integrante.
- **Spring Security completo:** Algunas rutas requieren autorización y aún pueden fallar si se prueba.
- **Búsqueda avanzada:** La búsqueda por autor, año y combinación de filtros está pendiente de implementación final.
- **PostgreSQL:** Se recomienda importar la base de datos SQL antes de probar el proyecto.

---

## 🔹 Instrucciones para ejecutar el backend

1. Clonar el repositorio:

```bash
git clone https://github.com/Enzo2703/biblioteca-digital-backend.git
