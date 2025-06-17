# 🏆 Customer Rewards API

This Spring Boot application calculates monthly and total reward points for customers based on their transaction history
over a selected time period.

---

## 📈 Business Logic

Reward points are calculated per transaction as follows:

- ✅ 1 point for every whole dollar spent **over $50 up to $100**
- ✅ 2 points for every whole dollar spent **over $100**
- ❌ Amounts are floored to discard cents (e.g., `$120.75` → `120`)

### Example

For a transaction of `$120.75`:

- Points for amount over $100: `(120 - 100) * 2 = 40`
- Points for amount between $50–$100: `(100 - 50) * 1 = 50`
- **Total = 90 points**

---

## ✨ Features

- ✅ Calculates monthly and total reward points per customer
- ✅ Supports date range filtering (`startDate`, `endDate`)
- ✅ Transaction details are included in the response
- ✅ Exception handling and input validation
- ✅ Modular structure following clean code principles
- ✅ Unit and integration testing with JUnit 5 and MockMvc

---

## 🛠 Tech Stack

| Layer       | Technology        |
|-------------|-------------------|
| Language    | Java 17           |
| Framework   | Spring Boot 3.3.7 |
| Web Layer   | Spring Web        |
| Persistence | Spring Data JPA   |
| Database    | MySQL             |
| Build Tool  | Maven             |
| Testing     | JUnit 5, MockMvc  |

---

## 📡 API Endpoints

### 🔹 `GET /api/rewards`

Get all customer reward summaries between a given start and end date.

#### Parameters:

- `startDate` (query param) – e.g., `2024-03-01`
- `endDate` (query param) – e.g., `2024-05-01`

#### Example:

```
GET http://localhost:8081/api/rewards?startDate=2024-03-01&endDate=2024-05-01
```

#### Response:

```json
[
  {
    "customerId": 1,
    "customerName": "Vishal Saste",
    "monthlyRewards": {
      "MARCH": 90,
      "APRIL": 45,
      "MAY": 70
    },
    "totalRewards": 205,
    "transactions": [
      {
        "transactionId": 101,
        "amount": 120.75,
        "transactionDate": "2024-03-10"
      },
      {
        "transactionId": 102,
        "amount": 95.00,
        "transactionDate": "2024-04-18"
      },
      {
        "transactionId": 103,
        "amount": 110.00,
        "transactionDate": "2024-05-12"
      }
    ]
  },
  {
    "customerId": 2,
    "customerName": "Rohit Saste",
    "monthlyRewards": {
      "MARCH": 50,
      "MAY": 90
    },
    "totalRewards": 140,
    "transactions": [
      {
        "transactionId": 104,
        "amount": 100.00,
        "transactionDate": "2024-03-25"
      },
      {
        "transactionId": 105,
        "amount": 120.00,
        "transactionDate": "2024-05-09"
      }
    ]
  },
  {
    "customerId": 3,
    "customerName": "Mahesh More",
    "monthlyRewards": {
      "MARCH": 25,
      "APRIL": 150,
      "MAY": 110
    },
    "totalRewards": 255,
    "transactions": [
      {
        "transactionId": 110,
        "amount": 75.50,
        "transactionDate": "2024-03-05"
      },
      {
        "transactionId": 111,
        "amount": 150.00,
        "transactionDate": "2024-04-10"
      },
      {
        "transactionId": 112,
        "amount": 130.25,
        "transactionDate": "2024-05-01"
      }
    ]
  },
  {
    "customerId": 4,
    "customerName": "Suraj Korade",
    "monthlyRewards": {
      "APRIL": 74
    },
    "totalRewards": 74,
    "transactions": [
      {
        "transactionId": 120,
        "amount": 112.00,
        "transactionDate": "2024-04-14"
      }
    ]
  },
  {
    "customerId": 5,
    "customerName": "Komal Saste",
    "monthlyRewards": {},
    "totalRewards": 0,
    "transactions": []
  }
]

```

---

### 🔹 `GET /api/rewards/{customerId}`

Get a specific customer's reward summary within a date range.

#### Parameters:

- `customerId` (path param)
- `startDate`, `endDate` (query params)

#### Example:

```
GET http://localhost:8081/api/rewards/1?startDate=2024-03-01&endDate=2024-05-01
```

#### Response:


```json
{
  "customerId": 1,
  "customerName": "Vishal Saste",
  "monthlyRewards": {
    "MARCH": 90,
    "APRIL": 45,
    "MAY": 70
  },
  "totalRewards": 205,
  "transactions": [
    {
      "transactionId": 101,
      "amount": 120.75,
      "transactionDate": "2024-03-10"
    },
    {
      "transactionId": 102,
      "amount": 95.00,
      "transactionDate": "2024-04-18"
    },
    {
      "transactionId": 103,
      "amount": 110.00,
      "transactionDate": "2024-05-12"
    }
  ]
}


```

---

## 🧪 Running Tests

```
mvn test
```

---

## 🚀 Running the Application

```
mvn clean install
mvn spring-boot:run
```

---

## 👤 Author

**Vishal Saste**  
