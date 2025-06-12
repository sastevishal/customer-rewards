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
### GET /api/rewards/{customerId}

#### Parameters:
- `customerId` (Path): ID of the customer
- `startDate` (Query): Start date of the timeframe (yyyy-MM-dd)
- `endDate` (Query): End date of the timeframe (yyyy-MM-dd)

#### Example:
```
GET /api/rewards/C001?startDate=2025-03-01&endDate=2025-05-31
```

#### Response:
```json
{
  "customerId": "C001",
  "customerName": "Vishal Saste",
  "startDate": "2025-03-01",
  "endDate": "2025-05-31",
  "totalRewards": 150,
  "monthlyRewards": {
    "2025-03": 90,
    "2025-05": 60
  },
  "transactions": [
    {
      "amount": 120.0,
      "date": "2025-03-15",
      "rewardPoints": 90
    },
    {
      "amount": 90.0,
      "date": "2025-05-05",
      "rewardPoints": 40
    }
  ]
}
```

## Running the App
```
mvn clean install
mvn spring-boot:run
```

## Testing
JUnit tests are included in `RewardServiceImplTest and RewardControllerTest`.
Run with:
```
mvn test
```

## Author
Vishal Saste
