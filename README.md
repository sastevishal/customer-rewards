# Customer Rewards

This Spring Boot application calculates reward points for customers based on their transactions.

## 📌 Business Logic

Customers earn reward points as follows:
- 1 point for every dollar spent over $50 up to $100.
- 2 points for every dollar spent over $100.

**Example**:  
A purchase of $120 earns:  
- 2 × ($120 - $100) = 40 points  
- 1 × ($100 - $50) = 50 points  
- **Total = 90 points**

## Features
- Calculates 1 point for every dollar spent over $50 up to $100
- Calculates 2 points for every dollar spent over $100
- Calculates rewards per month and total for a selected timeframe
- Asynchronous API call
- Exception handling and input validation

---

  ## 🔧 Tech Stack
- Java 17
- Spring Boot 3.5.0
- Spring Web
- Spring Data JPA
- MySQL
- Maven
- JUnit 5

---

## API Endpoint
### GET /api/rewards/

#### Parameters:
- `endDate` (Query): End date of the timeframe (yyyy-MM-dd)

#### Example:
```
GET http://localhost:8081/api/rewards?endDate=2025-06-09
```

#### Response:
```json
[
    {
        "customerId": 1,
        "customerName": "Vishal Saste",
        "monthlyRewards": {
            "JUNE": 90,
            "MAY": 25,
            "APRIL": 250
        },
        "totalRewards": 365
    },
    {
        "customerId": 2,
        "customerName": "Ashu Wala",
        "monthlyRewards": {
            "JUNE": 0,
            "MAY": 70,
            "MARCH": 40
        },
        "totalRewards": 110
    },
    {
        "customerId": 3,
        "customerName": "Rohit Saste",
        "monthlyRewards": {
            "JUNE": 52,
            "MAY": 5,
            "APRIL": 110
        },
        "totalRewards": 167
    }
]
```

## Running the App
```
mvn clean install
mvn spring-boot:run
```

## Testing
JUnit tests are included.
Run with:
```
mvn test
```

## Author
Vishal Saste
