# Kafka Order Processing Project (order-tracking-system)

This project aims to **study and practice Apache Kafka with Java and Spring Boot**, using an **event-driven microservices architecture**.

It simulates a simple flow of **order creation**, **payment processing**, and **inventory reservation**, with Kafka acting as the asynchronous communication backbone between services.

---

## Architecture Overview

The system is composed of three independent services:

- **order-service**  
  Responsible for creating orders, persisting the order state, and reacting to events produced by other services.

- **payment-service**  
  Simulates payment processing and decides whether a payment is accepted or denied.

- **inventory-service**  
  Manages product stock using a relational database (PostgreSQL).

In addition to that:

- **Apache Kafka**  
  Handles asynchronous communication between services.

- **PostgreSQL**  
  Relational database used by the `inventory-service`, initialized with pre-populated data.

- **MongoDB**  
  NoSQL database used by the `order-service`.

---

## Event Flow

1. **order-service** creates a new order and publishes an `order-created` event.
2. **payment-service** consumes this event, processes the payment, and publishes `payment-processed` or `payment-denied`.
3. **inventory-service** consumes `payment-processed`, validates and updates inventory, then publishes `inventory-updated`.
4. **order-service** consumes the resulting events and updates the order status in its database.

Each service has its own **consumer group**, ensuring isolation and independent scaling.

---

## Technologies Used

- **Java 21**
- **Spring Boot 3.x**
- **Apache Kafka**
- **Spring Kafka**
- **PostgreSQL 15**
- **MongoDB 7.0**
- **Docker & Docker Compose**
- **Maven**

---

## Kafka Topics

| Topic               | Description                       |
|---------------------|-----------------------------------|
| `order-created`     | Order created by order-service    |
| `payment-processed` | Payment processing success result |
| `payment-denied`    | Payment processing fail result    |
| `inventory-updated` | Inventory update result           |

---

## Order Statuses

An order may assume the following states:

- `ORDER_CREATED`
- `PAYMENT_ACCEPTED`
- `PAYMENT_DENIED`
- `INVENTORY_RESERVED`
- `INVENTORY_REJECTED`
- `ORDER_COMPLETED`
- `ORDER_CANCELLED`

---

## Example Request (Create Order)

POST: http://localhost:8080/create

```json
{
  "amount": 999,
  "products": [
    {
      "id": "prod-001",
      "quantity": 1
    },
    {
      "id": "prod-002",
      "quantity": 1
    }
  ]
}
```

This request is sent to the **order-service**, which will publish an `order-created` event to Kafka.

---

## Databases

### PostgreSQL (Inventory Service)

The `inventory-service` uses PostgreSQL with a schema initialized automatically via an SQL script:

```sql
CREATE TABLE product_stock (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    available_quantity INTEGER NOT NULL
);
```

The database is **pre-populated with products** during container startup.

---

### MongoDB (Order Service)

The `order-service` uses **MongoDB** to persist orders and track their lifecycle as they move through the event-driven flow.

MongoDB was chosen because:

- Orders evolve over time (status updates)
- The schema is flexible and may change
- It avoids complex joins for this use case

---

## Running the Project with Docker

### Start all services

```bash
docker-compose up -d
```

### View logs

```bash
docker-compose logs -f
```

### Stop and remove containers + volumes (re-run SQL scripts)

```bash
docker-compose down -v
```

---

## Verifying Data

### PostgreSQL (Inventory Service)

Connect to PostgreSQL locally:

```bash
psql -h localhost -p 5432 -U postgres -d inventorydb
```

Query inventory:

```sql
SELECT * FROM product_stock;
```

---

### MongoDB (Order Service)

Connect to MongoDB using `mongosh`:

```bash
mongosh mongodb://localhost:27017
```
Or inside the container:
```bash
mongosh
```

Select the database used by the order service:

```js
use orderdb
```

List collections:

```js
show collections
```

Query saved orders:

```js
db.order.find().pretty()
```

You should see orders with their current status reflecting the latest processed events.

---

### Kafka

You can verify Kafka data by consuming messages directly from the topics.

List topics:

```bash
docker exec -it kafka kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --list
```

Consume messages from a topic:

```bash
docker exec -it kafka kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic order-created \
  --from-beginning
```

Replace `order-created` with other topics to inspect:

- `payment-processed`
- `inventory-updated`

This allows you to validate that events are being produced and consumed correctly across services.

---

## Next Steps (Ideas)

- Migrate messages from JSON to Avro
- Add Schema Registry
- Implement idempotency
- Implement retry mechanisms and DLQs

---

## Notes

This project is **not production-focused**. It is meant for learning and experimentation.