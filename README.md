# Learning Kafka
## Learning message broker on Microservices with Kafka

Project technology practices:
- Microservices architecture.
- Project profiling
- Multimodule Springboot project
- Database caching (Redis)
- Apache Kafka

## Project Problem
### Cause
- Different services with different databases.

### Problem
- Databases synchronization.

### Solution
Using Microservices Pattern : CQRS (Command Query Responsibility Segregation)
- Define a view database (replica which is read-only). The apps keep replica up to date by subscribing to domain events published by the service that own the data.