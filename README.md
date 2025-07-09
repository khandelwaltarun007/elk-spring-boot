# 👤 User Service

A lightweight RESTful User Service for user creation and retrieval with centralized logging powered by the **ELK Stack (Elasticsearch, Logstash, Kibana)**. The service and ELK stack run via **Docker Compose**.

---

## 📦 Features

- RESTful API using **Spring Boot**
- User creation and retrieval
- Centralized structured logging to **ELK stack**
- Containerized with Docker
- Health check endpoint
- Scalable and configurable

---

## 🧰 Tech Stack

| Component      | Technology         |
|----------------|--------------------|
| Backend        | Spring Boot        |
| Logging        | JSON + Logstash    |
| Central Logging| ELK Stack (Docker) |
| Containerization | Docker Compose   |

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/khandelwaltarun007/elk-spring-boot.git
cd elk-spring-boot
```


### 2. Start All Services
```bash
docker-compose up --build
```

### 3. Components and Their Ports

| Component      | Port   |
|----------------|--------|
| user-service   | `9091` |
| Logstash       | `5000` |
| Elasticsearch  | `9200` |
| Kibana         | `5601` |

### 4. Endpoints

| Method | Endpoint      | Description       |
| ------ | ------------- | ----------------- |
| POST   | `/users`      | Register new user |
| GET    | `/users/{id}` | Get user by ID    |
| GET    | `/users`      | Get all user      |

### 5. Sample

```bash
curl -X POST http://localhost:8000/users \
  -H "Content-Type: application/json" \
  -d '{"username": "johndoe", "email": "john@example.com"}'
```

```bash
curl --location 'localhost:9091/api/users'
```

```bash
curl --location 'localhost:9091/api/users/1'
```

### 6. Tear down the container

```bash
docker-compose down -v
```




