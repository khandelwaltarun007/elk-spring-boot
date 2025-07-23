# 👤 User Service with Centralized Logging & Full DevOps Stack

A robust, containerized microservice stack featuring:
- ✅ User Service (Spring Boot)
- 📈 Centralized logging with the **ELK Stack (Elasticsearch, Logstash, Kibana)**
- 🐳 Docker-based orchestration
- 🔐 HTTPS SSL via **Nginx reverse proxy**
- ⚙️ Kafka + Kafka UI
- 🧰 Jenkins CI/CD Server
- 🗃️ MongoDB
- ⚡ Redis cache

---

## 📦 Features

- RESTful APIs for user CRUD operations
- Centralized JSON logging into ELK Stack
- Reverse proxy with **HTTPS via Nginx**
- Kafka + Kafka UI for event streaming
- Jenkins server for CI/CD
- Redis for caching
- MongoDB as NoSQL storage
- Docker Compose for orchestration

---

## 🧰 Tech Stack

| Component         | Technology               |
|------------------|--------------------------|
| Backend Service  | Spring Boot              |
| Logging          | JSON + Logstash          |
| Central Logging  | ELK Stack (Docker)       |
| Reverse Proxy    | Nginx + SSL              |
| CI/CD            | Jenkins (Dockerized)     |
| Messaging        | Apache Kafka + Kafka UI  |
| Cache            | Redis                    |
| Database         | MongoDB                  |
| Orchestration    | Docker Compose           |

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/khandelwaltarun007/elk-spring-boot.git
cd elk-spring-boot
```
### 2. Generate SSL Certificates for Nginx
You can generate self-signed certificates for jenkins.localhost, kafka.localhost, and other subdomains:

```bash
mkdir -p nginx/certs

# Generate key
openssl genrsa -out nginx/certs/selfsigned.key 2048

# Generate certificate
openssl req -x509 -new -nodes -key nginx/certs/selfsigned.key \
  -sha256 -days 365 -out nginx/certs/selfsigned.crt \
  -subj "/C=IN/ST=Delhi/L=Delhi/O=Dev/OU=DevOps/CN=localhost"
```
You can optionally use tools like mkcert for local domain-specific SSL.

### 3. Configure Local Subdomains
Edit your local hosts file (/etc/hosts on Linux/macOS, C:\Windows\System32\drivers\etc\hosts on Windows) and add:

```bash
127.0.0.1   jenkins.localhost
127.0.0.1   kafka.localhost
```
### 4. Start All Services
```bash
docker-compose up --build
```

### 5. Components and Their Ports

| Component      | Port   |
|----------------|--------|
| user-service   | `9091` |
| Logstash       | `5000` |
| Elasticsearch  | `9200` |
| Jenkins CI/CD         | `8443` |
| Kafka UI         | `8443` |
| Redis         | `6379` |
| MongoDB         | `27017` |

### 6. Endpoints

| Method | Endpoint      | Description       |
| ------ | ------------- | ----------------- |
| POST   | `/users`      | Register new user |
| GET    | `/users/{id}` | Get user by ID    |
| GET    | `/users`      | Get all user      |
| PUT    | `/users`      | Patch user        |
| DELETE | `/users/{id}` | Delete user by ID |

### 7. Sample Rest Curls

#### Create User
```bash
curl -X POST http://localhost:8000/users \
  -H "Content-Type: application/json" \
  -d '{"username": "johndoe", "email": "john@example.com"}'
```

#### Get all Users
```bash
curl --location 'localhost:9091/api/users'
```

#### Get user by ID
```bash
curl --location 'localhost:9091/api/users/1'
```
#### Update user
```bash
curl -X PUT http://localhost:8000/users \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "username": "johndoe", "email": "john@example.com"}'
```

#### Delete User
```bash
curl -X DELETE http://localhost:8000/users/1
```

### 8. Tear down the container

```bash
docker-compose down -v
```
### 🧾 Jenkins Notes
Jenkins is pre-configured to run behind Nginx with path /jenkins

If stuck in redirect loop, ensure Jenkins URL is correctly set in:

Manage Jenkins → Configure System → Jenkins URL
➤ https://jenkins.localhost:8443/jenkins

### 🛡️ Nginx Configuration (nginx/nginx.conf)

```bash
events {}

http {
    server {
        listen 443 ssl;
        server_name jenkins.localhost;

        ssl_certificate /etc/nginx/certs/selfsigned.crt;
        ssl_certificate_key /etc/nginx/certs/selfsigned.key;

        location /jenkins/ {
            proxy_pass http://jenkins:8080/jenkins/;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-Proto $scheme;
            proxy_buffering off;
        }
    }

    server {
        listen 443 ssl;
        server_name kafka.localhost;

        ssl_certificate /etc/nginx/certs/selfsigned.crt;
        ssl_certificate_key /etc/nginx/certs/selfsigned.key;

        location / {
            proxy_pass http://kafka-ui:8080/;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-Proto $scheme;
        }
    }

    server {
        listen 80;
        return 301 https://$host$request_uri;
    }
}
```

### 🌍 Access URLs
| Service          | URL               |
|------------------|--------------------------|
| Jenkins CI/CD    | https://jenkins.localhost:8443/jenkins/ |
| Kafka UI         | https://kafka.localhost:8443  |
| Spring Boot API  | http://localhost:9091       |
| Kibana Dashboard    | http://localhost:5601     |
| Redis           | redis://localhost:6379    |
| MongoDB        | mongodb://localhost:27017  |