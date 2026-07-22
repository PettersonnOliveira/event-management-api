# 🎟️ Event Management API

API REST desenvolvida para gerenciar eventos (workshops, palestras, hackathons, feiras) e o controle de inscrições dos participantes. Permite cadastrar usuários, criar eventos, gerenciar inscrições e consultar a lista de participantes de cada evento.

Este é o terceiro projeto do meu portfólio de estudos em Java e Spring Boot, com foco em relacionamentos N:N através de entidade intermediária e regras de negócio de controle de capacidade.

## 💡 Problema que resolve

Organizadores de eventos que controlam inscrições de forma manual ou dispersa enfrentam problemas como:

- Não saber quantas vagas ainda restam em tempo real
- Permitir inscrições duplicadas do mesmo participante
- Ultrapassar a capacidade máxima do evento
- Perder o controle de quem está de fato inscrito em cada evento

A Event Management API resolve isso centralizando o controle de eventos e inscrições, com regras de negócio que impedem operações inválidas automaticamente.

## 🛠️ Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web (REST API)
- Spring Data JPA
- MySQL
- Bean Validation (validação de dados de entrada)
- Maven

## 🏗️ Arquitetura

`Controller → Service → Repository → Banco de Dados`

- **Controller**: recebe requisições HTTP e devolve respostas
- **Service**: contém as regras de negócio, incluindo validações de capacidade e duplicidade
- **Repository**: comunicação com o banco de dados, incluindo consultas derivadas (`existsBy`, `countBy`, `findBy`)
- **DTOs**: protegem a API, controlando exatamente o que entra e sai — inclusive em relacionamentos, através de DTOs aninhados
- **Exceptions**: exceções customizadas capturadas globalmente, garantindo respostas de erro consistentes

## 📦 Modelo de dados

### User

| Campo | Tipo |
| --- | --- |
| id | Long |
| name | String |
| email | String |

### Event

| Campo | Tipo |
| --- | --- |
| id | Long |
| title | String |
| description | String |
---

> Versão em inglês disponível em `README.en.md`.
| description | String |
| location | String |
| date | LocalDateTime |
| capacity | Integer |

### Registration

| Field | Type |
| --- | --- |
| id | Long |
| registrationDate | LocalDateTime |
| user | User (@ManyToOne) |
| event | Event (@ManyToOne) |

## 🧠 Relationships

- A `User` can register for many `Event`s through multiple `Registration`s
- An `Event` can have many registered `User`s through multiple `Registration`s
- `Registration` is the intermediate entity that resolves this N:N relationship, allowing the registration itself to store its own data (such as the date) without relying on a direct `@ManyToMany`

## 📌 Available endpoints

### Users

| Action | Method | Route |
| --- | --- | --- |
| Create user | POST | `/users` |
| List users | GET | `/users` |
| Find user by ID | GET | `/users/{id}` |

### Events

| Action | Method | Route |
| --- | --- | --- |
| Create event | POST | `/events` |
| List events | GET | `/events` |
| Find event by ID | GET | `/events/{id}` |
| Update event | PUT | `/events/{id}` |
| Delete event | DELETE | `/events/{id}` |

### Registrations

| Action | Method | Route |
| --- | --- | --- |
| Register user to event | POST | `/registrations` |
| Cancel registration | DELETE | `/registrations/{id}` |
| List event participants | GET | `/registrations/events/{eventId}/participants` |

### Example — Register user to event

**POST** `/registrations`

```json
{
  "userId": 1,
  "eventId": 2
}
```

**Response (201 Created)**

```json
{
  "id": 3,
  "registrationDate": "2026-07-13T16:57:15.917115",
  "user": {
    "id": 1,
    "name": "João Pedro",
    "email": "joao.pedro@email.com"
  },
  "event": {
    "id": 2,
    "title": "Evento Teste Capacidade",
    "description": "Evento criado só para testar o limite de vagas",
    "location": "Sala de Testes",
    "date": "2026-09-10T10:00:00",
    "capacity": 2
  }
}
```

## 🔥 Business rules

| Rule | Behavior |
| --- | --- |
| Event is full (registrations = capacity) | does not accept new registrations `400 Bad Request` |
| User already registered for the same event | cannot register again `400 Bad Request` |
| Event does not exist | `404 Not Found` |
| User does not exist | `404 Not Found` |

Capacity is always checked by counting, in real time, how many `Registration`s already exist for that event — there is no separately stored counter, which avoids inconsistencies.

## ⚠️ Error handling

The API uses custom exceptions, handled globally through `@RestControllerAdvice`, ensuring consistent error responses:

- `ResourceNotFoundException` → returns `404 Not Found` (used when a resource by ID is not found)
- `BusinessRuleException` → returns `400 Bad Request` (used when a business rule is violated, even if the resource exists — such as full capacity or duplicate registration)

## ✅ Validation

Input DTOs use Bean Validation:

- `name`, `title`, `description`, `location` → `@NotBlank`
- `email` → `@Email`
- `userId`, `eventId` → `@NotNull`

Requests that violate these rules automatically return `400 Bad Request`.

## ⚙️ How to run the project locally

### Requirements

- Java 21 installed
- MySQL running locally
- Maven (or the included `mvnw` wrapper)

### Steps

1. Clone the repository

```bash
git clone <repository-url>
```

2. Configure the database in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event_management_db?createDatabaseIfNotExist=true
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

> ⚠️ This file contains sensitive credentials and should not be versioned with real production data.

3. Run the application:

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

### Testing

Postman or Insomnia is recommended for testing the endpoints.

## 🗺️ Next steps (roadmap)

- Move the participants endpoint to `/events/{id}/participants` (currently at `/registrations/events/{id}/participants`)
- Authentication and authorization with Spring Security + JWT
- Registration status (confirmed, canceled, waiting list)
- Email notification when a registration is confirmed

## 📝 What I learned building this project

- Modeling N:N relationships with an intermediate entity (`Registration`) instead of a direct `@ManyToMany` — and why this choice is more sustainable in the long run
- Spring Data JPA derived queries beyond the basics: `existsBy` (boolean check) and `countBy` (counting), combining multiple fields with `And`
- Business rules that depend on real-time counting, without storing duplicated state
- How to organize routes when an endpoint logically “belongs” to a different resource than where the code lives
- Clear separation between a “resource not found” exception and a “business rule violated” exception, and how to decide which one to use in each case
- Global error handling with `@RestControllerAdvice` and `@ExceptionHandler`

---

Developed by **[Petterson Oliveira]** as part of my Java and Spring Boot study portfolio.



