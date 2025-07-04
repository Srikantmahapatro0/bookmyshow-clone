![CI](https://github.com/Srikantmahapatro0/BookMyShow/actions/workflows/ci.yml/badge.svg)

# 🎟️ BookMyShow Clone – Backend (Spring Boot)

A complete backend clone of BookMyShow built using **Java**, **Spring Boot**, and **JPA**. It supports user signup, show scheduling, seat selection, booking, and dynamic price calculation — all with proper entity relationships, validation, and service abstraction.

---

## 📌 Features

- 🧑 User registration with password encryption
- 🏟️ Theatres, Screens, Seats, and Regions
- 🎬 Movie scheduling across regions and screens
- 🪑 Seat booking with locking and validation
- 💵 Price calculation based on seat type and show
- 💳 Payment handling with status tracking

---

## 🧱 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- (H2/MySQL for persistence)

---

## 📁 Project Structure

```

com.example.bookmyshow
├── controllers/
│   ├── UserController.java
│   └── BookingController.java
├── dtos/
│   ├── SignUpRequestDto.java
│   ├── IssueTicketRequestDto.java
│   └── ResponseStatus.java
├── services/
│   ├── UserService.java
│   ├── BookingService.java
│   └── PriceCalculatorService.java
├── models/
│   ├── BaseModel.java
│   ├── Booking.java, BookingStatus.java
│   ├── Movie.java, Feature.java
│   ├── Region.java, Theatre.java, Screen.java
│   ├── Seat.java, SeatType.java
│   ├── Show\.java, ShowSeat.java, ShowSeatStatus.java, ShowSeatType.java
│   ├── User.java
│   ├── Payment.java, PaymentStatus.java, PaymentProvider.java
├── repositories/
│   ├── BookingRepository.java
│   ├── ShowRepository.java
│   ├── ShowSeatRepository.java
│   ├── ShowSeatTypeRepository.java
│   └── UserRepository.java

```

---

## 🔄 Entity Relationships (Simplified)

```

User           ─┬─> Booking (1\:N)
└─> Payment (1\:N)

Booking        ──> Show (N:1)
└─> ShowSeat (N\:M)

Show           ──> Screen (N:1)
└─> ShowSeat (1\:N)

ShowSeat       ──> Seat (N:1)
└─> ShowSeatType (N:1)

Screen         ──> Theatre (N:1)
Seat           ──> SeatType (N:1)

Theatre        ──> Region (N:1)

````

---

## 🚀 How to Run

### Prerequisites

- Java 17+
- Maven installed

### Steps

```bash
git clone https://github.com/Srikantmahapatro0/bookmyshow-clone.git
cd bookmyshow-clone
mvn spring-boot:run
````

* App runs at: `http://localhost:8080`

---

## 🔗 Key APIs

### 1. 🧾 Sign Up User

`POST /signup`

**Request:**

```json
{
  "email": "test@example.com",
  "password": "secure123"
}
```

**Response:**

```json
{
  "userId": 1,
  "responseStatus": "SUCCESS"
}
```

---

### 2. 🎟️ Issue Ticket

`POST /booking/issue`

**Request:**

```json
{
  "userId": 1,
  "showId": 12,
  "showSeatIds": [101, 102, 103]
}
```

**Response:**

```json
{
  "bookingId": 987,
  "amount": 750,
  "responseStatus": "SUCCESS"
}
```

---

## 🛠️ Core Logic Highlights

* Passwords are hashed using **BCryptPasswordEncoder**
* Seat status is validated with 15-minute **lock timeout**
* Bookings use **Serializable isolation level** to avoid race conditions
* Modular services for booking, user, price calculation
* Ready to scale with microservices or frontend

---


## 📝 License

This project is licensed under the MIT License.

---

💡 *Built for learning scalable backend system design using Spring Boot.*

```
