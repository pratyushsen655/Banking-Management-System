# Web Banking Application

A Spring Boot-based web application for banking management with REST API and Thymeleaf frontend.

## Features

- User registration and login
- Account creation (Savings/Current)
- Deposit money
- Withdraw money
- Transfer money between accounts
- View account balance
- View transaction history
- REST API endpoints for all operations
- Responsive web interface using Thymeleaf
- In-memory data storage

## Building the Application

### Prerequisites
- JDK 17 or higher
- Maven 3.6 or higher

### Build Instructions

```bash
cd web
mvn clean install
```

### Running the Application

```bash
cd web
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Default Credentials

For testing purposes, a default admin account is available:
- Username: `admin`
- Password: `admin123`
- Account Number: `1001`
- Balance: $10,000.00

## REST API Endpoints

### Authentication
- `POST /api/register` - Register a new user
- `POST /api/login` - Login with credentials

### Account Management
- `POST /api/accounts/create` - Create a new account
- `GET /api/accounts/{userId}` - Get all accounts for a user
- `GET /api/account/{accountNumber}` - Get account details
- `GET /api/balance/{accountNumber}` - Get account balance

### Transactions
- `POST /api/deposit` - Deposit money
- `POST /api/withdraw` - Withdraw money
- `POST /api/transfer` - Transfer money between accounts
- `GET /api/transactions/{accountNumber}` - Get transaction history

## Web Interface Routes

- `/` - Redirect to login
- `/login` - Login page
- `/register` - Registration page
- `/dashboard` - User dashboard (requires login)
- `/create-account` - Create new account page
- `/deposit` - Deposit money page
- `/withdraw` - Withdraw money page
- `/transfer` - Transfer money page
- `/history/{accountNumber}` - Transaction history page
- `/logout` - Logout

## Project Structure

```
web/
├── src/main/
│   ├── java/com/banking/web/
│   │   ├── models/              # Data models
│   │   ├── services/            # Business logic
│   │   ├── controllers/         # REST and Web controllers
│   │   └── BankingWebApplication.java
│   └── resources/
│       ├── templates/           # Thymeleaf HTML templates
│       ├── static/css/          # CSS stylesheets
│       └── application.properties
├── pom.xml                      # Maven configuration
└── target/                      # Compiled classes
```

## API Request Examples

### Register User
```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"pass123","email":"john@example.com","fullName":"John Doe"}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Create Account
```bash
curl -X POST http://localhost:8080/api/accounts/create \
  -H "Content-Type: application/json" \
  -d '{"userId":"user-id","accountType":"Savings","initialBalance":1000,"accountHolderName":"John Doe"}'
```

### Deposit Money
```bash
curl -X POST http://localhost:8080/api/deposit \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"1001","amount":500}'
```

### Get Transaction History
```bash
curl http://localhost:8080/api/transactions/1001
```

## Notes

- All data is stored in memory and will be lost when the application restarts
- Session-based authentication is used for the web interface
- The application runs on port 8080 by default
- Both REST API and web interface can be used simultaneously
