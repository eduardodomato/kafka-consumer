# 🚀 Kafka Consumer Application

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-2.x-black.svg)
![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

*A high-performance, production-ready Kafka consumer built with Spring Boot*

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Project Structure](#-project-structure)
- [Usage](#-usage)
- [API Endpoints](#-api-endpoints)
- [Testing](#-testing)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎯 Overview

This is a **Spring Boot-based Kafka Consumer** application designed to consume messages from Kafka topics efficiently. The application supports consuming both **raw string messages** and **JSON-serialized objects** (specifically `Customer` objects), making it versatile for various messaging scenarios.

### Key Highlights

- ✅ **Dual Consumer Support**: Handles both string and object messages
- ✅ **JSON Serialization**: Built-in support for JSON deserialization
- ✅ **Production Ready**: Includes Spring Boot Actuator for monitoring
- ✅ **Modern Java**: Built with Java 21 features
- ✅ **Clean Architecture**: Well-structured, maintainable codebase

---

## ✨ Features

- 🔄 **Multiple Message Types**: Supports string messages and custom objects
- 📦 **JSON Deserialization**: Automatic deserialization of JSON messages to POJOs
- 🎯 **Consumer Groups**: Configurable consumer group support for load balancing
- 📊 **Actuator Endpoints**: Health checks and metrics via Spring Boot Actuator
- 🪵 **Structured Logging**: Comprehensive logging with SLF4J
- ⚡ **High Performance**: Efficient message processing with Spring Kafka
- 🔧 **Easy Configuration**: YAML-based configuration for easy customization

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Programming Language |
| **Spring Boot** | 3.5.7 | Application Framework |
| **Spring Kafka** | 3.x | Kafka Integration |
| **Apache Kafka** | 2.x+ | Message Broker |
| **Lombok** | Latest | Boilerplate Reduction |
| **Maven** | 3.6+ | Build Tool |
| **Spring Boot Actuator** | 3.5.7 | Monitoring & Health Checks |

---

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- ☕ **Java Development Kit (JDK) 21** or higher
- 🔧 **Apache Maven 3.6+**
- 🐳 **Apache Kafka 2.x+** (running locally or accessible remotely)
- 📝 Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

### Kafka Setup (Quick Start)

If you don't have Kafka running, here's a quick way to get started:

```bash
# Using Docker Compose (Recommended)
docker-compose up -d

# Or download and run Kafka manually
# 1. Download Kafka from https://kafka.apache.org/downloads
# 2. Start Zookeeper: bin/zookeeper-server-start.sh config/zookeeper.properties
# 3. Start Kafka: bin/kafka-server-start.sh config/server.properties
```

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd kafka-consumer
```

### 2. Configure Kafka Connection

Update `src/main/resources/application.yml` with your Kafka broker details:

```yaml
spring:
  kafka:
    consumer:
      bootstrap-servers: localhost:9092  # Update if different
      group-id: group-1
```

### 3. Build the Project

```bash
# Using Maven Wrapper (Recommended)
./mvnw clean install

# Or using system Maven
mvn clean install
```

### 4. Run the Application

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or using system Maven
mvn spring-boot:run

# Or run the JAR directly
java -jar target/kafka-0.0.1-SNAPSHOT.jar
```

### 5. Verify It's Running

The application will start on **port 9292** by default. You should see:

```
Started KafkaApplication in X.XXX seconds
```

---

## ⚙️ Configuration

### Application Configuration (`application.yml`)

```yaml
server:
  port: 9292

spring:
  kafka:
    consumer:
      bootstrap-servers: localhost:9092
      group-id: group-1
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.support.serializer.JsonDeserializer
```

### Environment Variables

You can override configuration using environment variables:

```bash
export SPRING_KAFKA_CONSUMER_BOOTSTRAP_SERVERS=localhost:9092
export SPRING_KAFKA_CONSUMER_GROUP_ID=group-1
```

### Profile-specific Configuration

Create profile-specific files for different environments:
- `application-dev.yml` - Development environment
- `application-prod.yml` - Production environment

Run with a specific profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 📁 Project Structure

```
kafka-consumer/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── messaging/
│   │   │           └── kafka/
│   │   │               ├── KafkaApplication.java      # Main application class
│   │   │               ├── consumer/
│   │   │               │   └── MessageListener.java   # Kafka consumers
│   │   │               └── dto/
│   │   │                   └── Customer.java          # Customer DTO
│   │   └── resources/
│   │       ├── application.yml                        # Application configuration
│   │       └── application.properties                 # Alternative config format
│   └── test/
│       └── java/
│           └── com/
│               └── messaging/
│                   └── kafka/
│                       └── KafkaApplicationTests.java  # Test classes
├── pom.xml                                             # Maven dependencies
├── mvnw                                                # Maven wrapper (Unix)
├── mvnw.cmd                                            # Maven wrapper (Windows)
└── README.md                                           # This file
```

---

## 💻 Usage

### Consuming Messages

The application automatically consumes messages from the `kafka-passthrough` topic once started.

#### 1. String Message Consumer

Consumes raw string messages:

```java
@KafkaListener(topics = "kafka-passthrough", groupId = "group-1")
public void consume(String message){
    log.info("Consumer has pulled the message: {}", message);
}
```

#### 2. Customer Object Consumer

Consumes JSON-serialized `Customer` objects:

```java
@KafkaListener(topics = "kafka-passthrough", groupId = "group-1")
public void consume(Customer customer){
    log.info("Consumer has pulled the object: {}", customer);
}
```

### Customer DTO

```java
@Data
public class Customer {
    private String id;
    private String name;
    private String email;
    private String phone;
}
```

### Sending Test Messages

#### Using Kafka Console Producer

```bash
# Send a string message
kafka-console-producer --broker-list localhost:9092 --topic kafka-passthrough
> Hello Kafka!

# Send a JSON message (Customer object)
kafka-console-producer --broker-list localhost:9092 --topic kafka-passthrough
> {"id":"123","name":"John Doe","email":"john@example.com","phone":"123-456-7890"}
```

#### Using cURL (if you have a producer API)

```bash
curl -X POST http://localhost:9292/api/messages \
  -H "Content-Type: application/json" \
  -d '{"topic":"kafka-passthrough","message":"Hello from API"}'
```

---

## 🔌 API Endpoints

### Spring Boot Actuator

The application includes Spring Boot Actuator for monitoring:

| Endpoint | Description | URL |
|----------|-------------|-----|
| Health Check | Application health status | `http://localhost:9292/actuator/health` |
| Info | Application information | `http://localhost:9292/actuator/info` |
| Metrics | Application metrics | `http://localhost:9292/actuator/metrics` |

### Example: Check Health

```bash
curl http://localhost:9292/actuator/health
```

Response:
```json
{
  "status": "UP"
}
```

---

## 🧪 Testing

### Run Unit Tests

```bash
./mvnw test
```

### Run Integration Tests

```bash
./mvnw verify
```

### Test with Embedded Kafka

The project includes `spring-kafka-test` dependency for testing with embedded Kafka brokers.

---

## 🐛 Troubleshooting

### Common Issues

#### 1. Kafka Connection Failed

**Problem**: Cannot connect to Kafka broker

**Solution**:
- Ensure Kafka is running: `docker ps` or check Kafka logs
- Verify `bootstrap-servers` in `application.yml`
- Check firewall settings

#### 2. Topic Not Found

**Problem**: Consumer cannot find the topic

**Solution**:
- Create the topic manually:
  ```bash
  kafka-topics --create --topic kafka-passthrough --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
  ```
- Or configure auto-creation in Kafka server settings

#### 3. Deserialization Error

**Problem**: JSON deserialization fails

**Solution**:
- Ensure message format matches `Customer` DTO structure
- Check JSON syntax validity
- Verify `value-deserializer` configuration

---

## 📊 Monitoring & Logs

### View Application Logs

```bash
# Logs are printed to console by default
# Check application logs for:
# - Consumer messages
# - Connection status
# - Error messages
```

### Log Configuration

Add to `application.yml` for file logging:

```yaml
logging:
  file:
    name: logs/kafka-consumer.log
  level:
    com.messaging.kafka: DEBUG
```

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. 🍴 Fork the repository
2. 🌿 Create a feature branch (`git checkout -b feature/amazing-feature`)
3. 💾 Commit your changes (`git commit -m 'Add amazing feature'`)
4. 📤 Push to the branch (`git push origin feature/amazing-feature`)
5. 🔀 Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Your Name**
- GitHub: [@eduardodomato](https://github.com/eduardodomato)
- Email: edudomato@gmail.com

---

## 🙏 Acknowledgments

- [Spring Kafka](https://spring.io/projects/spring-kafka) - For excellent Kafka integration
- [Apache Kafka](https://kafka.apache.org/) - For the powerful messaging platform
- [Spring Boot](https://spring.io/projects/spring-boot) - For the amazing framework

---

<div align="center">

**Made with ❤️ using Spring Boot and Apache Kafka**

⭐ Star this repo if you find it helpful!

</div>

