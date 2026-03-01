# URL Shortener – Spring Boot + PostgreSQL

A production-style URL Shortener REST API built using Java, Spring Boot, Spring Data JPA, and PostgreSQL.

The application allows users to:
- Create short links
- Redirect using short codes
- Track click analytics
- Generate expiring URLs

This project demonstrates backend design, REST API development, database modeling, and clean architecture.

---

## Features

### Core Features
- Create short URLs from long URLs
- Redirect using short code
- PostgreSQL persistence
- Clean layered architecture (Controller → Service → Repository)

### Advanced Features
- Click count tracking (basic analytics)
- URL expiry with selectable duration
- Custom exception handling
- DTO validation
- Environment variable based configuration

---

## Tech Stack

| Category | Technology |
|-----------|------------|
| Language | Java 23 |
| Framework | Spring Boot |
| Database | PostgreSQL |
| ORM | Spring Data JPA (Hibernate) |
| Build Tool | Maven |
| Validation | Jakarta Validation |
| Architecture | REST API |

---

## API Endpoints

### 1. Shorten URL

**POST** `/api/shorten`

Request:

```json
{
  "originalUrl": "https://google.com",
  "expiryDuration": "ONE_DAY"
}
```

Response:

```json
{
  "shortUrl": "http://localhost:8081/aB3dE1"
}
```

---

### 2. Redirect to Original URL

**GET** `/{shortCode}`

Example:

```
http://localhost:8081/aB3dE1
```

- Redirects to original URL
- Returns `410 GONE` if expired
- Returns `404 NOT FOUND` if short code does not exist

---

## Expiry Options

| Option | Duration |
|--------|----------|
| ONE_HOUR | 1 hour |
| ONE_DAY | 24 hours |
| SEVEN_DAYS | 7 days |
| THIRTY_DAYS | 30 days |

---

## Click Analytics

Each redirect increments a click counter stored in the database.

This demonstrates read-modify-write database operations and basic analytics tracking.

---

## Project Structure

```
url-shortener
│
├── src/main/java/com/example/urlshortener
│   ├── controller
│   │   ├── RedirectController.java
│   │   └── UrlController.java
│   │
│   ├── dto
│   │   ├── UrlRequest.java
│   │   └── UrlResponse.java
│   │
│   ├── entity
│   │   └── UrlRecord.java
│   │
│   ├── exception
│   │   ├── ShortUrlNotFoundException.java
│   │   └── UrlExpiredException.java
│   │
│   ├── repository
│   │   └── UrlRecordRepository.java
│   │
│   ├── service
│   │   ├── UrlService.java
│   │   └── UrlServiceImplementation.java
│   │
│   ├── util
│   │   └── ExpiryDuration.java
│   │
│   └── UrlShortenerApplication.java
│
├── src/main/resources
│   └── application.yml
│
└── pom.xml
```

---

## How to Run Locally

### 1. Clone Repository

```bash
git clone https://github.com/nsahil992/URL-Shortener.git
```

### 2. Setup Environment Variables

Create a `.env` file. Make sure to have the following dependency in pom.xml file:
```
    <dependency>
            <groupId>me.paulschwarz</groupId>
            <artifactId>spring-dotenv</artifactId>
            <version>4.0.0</version>
    </dependency>
```

```
DB_URL=jdbc:postgresql://localhost:5432/url_shortener_db
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

### 3. Create Database

```sql
CREATE DATABASE url_shortener_db;
```

### 4. Run Application

```bash
./mvnw spring-boot:run
```

Application runs at:

```
http://localhost:8081
```

---

## Future Improvements

- Base62 encoding for short codes
- Rate limiting
- Redis caching
- Dockerization and deployment on K8s
- Adding CI/CD pipeling
- User authentication using Spring Security
- Adding tests

---

## Learning Outcomes

This project demonstrates:

- REST API design using Spring Boot
- Database schema design with JPA and ORM
- Exception handling best practices
- Environment-based configuration
- Evolving an MVP into a production-style backend service

---

## Author

Sahil Naik
