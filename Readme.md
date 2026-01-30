# Movies API

A RESTful API for managing a movie database for a local film society, built with Spring Boot, Spring Data JPA, and SQLite. Supports movies, genres, actors, and their many-to-many relationships.

## Author

**Lauri Musto**  
Email: [Laurimusto@gmail.com](mailto:Laurimusto@gmail.com)  
GitHub: [github.com/laurimusto](https://github.com/laurimusto)

---

## Features

- **Entities:** `Movie`, `Actor`, `Genre`
- **Relationships:** Many-to-Many between Movies ↔ Actors, Movies ↔ Genres
- **CRUD operations:** Create, read, update, delete for all entities
- **Filtering & Search:**
    - Movies by genre, year, actor, or title
    - Actors by name
- **Pagination & Sorting:** `page`, `size`, `sortBy`, `ascending`
- **Validation & Error Handling:**
    - Bean Validation on DTOs
    - Global exception handler for 400, 404, and 500 responses
    - Validation errors return a clear JSON body per field
- **Relationship-aware deletion:** Optional `?force=true` to delete entities with existing relationships
- **Sample Data:** Populated via `DatabaseSeeder` on first run

---

## Tech Stack

- Java 17+
- Spring Boot
- Spring Web, Spring Data JPA
- SQLite via JDBC
- Maven

---

## Project Structure

- `entity/` — JPA entities
- `repository/` — Spring Data JPA repositories
- `service/` — Business logic
- `controller/` — REST controllers
- `dto/` — Request/response DTOs
- `mapper/` — Entity ↔ DTO mappers
- `exceptions/` — Custom exceptions & global handler
- `DatabaseSeeder` — Sample data initializer
- `PaginationValidator` — Validates pagination params

---

## Setup

1. **Clone repository**
```bash
git clone <repo-url>
cd movies-api
```

2. **Configure SQLite** in `application.properties`
```properties
spring.datasource.url=jdbc:sqlite:movies.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.SQLiteDialect
```

3. **Build & Run**
```bash
mvn clean install
mvn spring-boot:run
```

API available at `http://localhost:8080`.

---

## API Overview

- Base paths:
    - Movies: `/api/movies`
    - Actors: `/api/actors`
    - Genres: `/api/genres`

- **Query Parameters:** `page`, `size`, `sortBy`, `ascending`
- **Filtering:** optional parameters like `genre`, `year`, `actor`, `title` for movies; `name` for actors

- **CRUD Highlights:**
    - Create: `POST /api/movies` (or `/actors`, `/genres`)
    - Read: `GET /api/movies/{id}`
    - Update (partial): `PATCH /api/movies/{id}`
    - Delete: `DELETE /api/movies/{id}?force=true` for forced removal

- **Error Responses:**
```json
{
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "fieldName": "Validation message"
  }
}
```

- **Sorting:** Supported via `sortBy` and `ascending` query parameters, e.g., `GET /api/movies?page=0&size=10&sortBy=releaseYear&ascending=false`

---

## Notes

- IDs are auto-generated and immutable.
- DTO validation ensures input correctness before hitting business logic.
- Force deletion ensures relationships are properly handled to avoid dangling references.