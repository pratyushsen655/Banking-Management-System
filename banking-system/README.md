# Banking Management System

## Overview

The **Banking Management System** is a comprehensive Java-based application with both desktop (Java Swing) and web (Spring Boot) interfaces. It handles banking operations such as account creation, login, deposits, withdrawals, transfers, and transaction history management. The system provides user-friendly interfaces for customers to manage their banking needs digitally.

## Features

### Core Banking Operations
- **User Registration & Login:** Secure authentication system
- **Account Creation:** Create Savings or Current accounts
- **Deposit Money:** Add funds to accounts
- **Withdraw Money:** Remove funds from accounts
- **Transfer Money:** Transfer funds between accounts
- **View Balance:** Check current account balance
- **Transaction History:** View complete transaction records

### Desktop Application (Java Swing)
- Rich GUI using Java Swing
- Interactive forms and tables
- Real-time updates
- In-memory data storage

### Web Application (Spring Boot)
- RESTful API endpoints
- Thymeleaf-based web interface
- Session-based authentication
- Responsive design
- In-memory data storage

## Technologies Used

- **Core:** Java 17
- **Desktop:** Java Swing
- **Web Framework:** Spring Boot 3.1.5
- **Template Engine:** Thymeleaf
- **Build Tools:** Maven, Shell scripts
- **Data Storage:** In-memory (HashMap, ArrayList)

## Project Structure

```
banking-system/
├── desktop/              # Desktop application (Java Swing)
│   ├── src/
│   │   └── main/java/com/banking/desktop/
│   │       ├── models/      # Data models
│   │       ├── services/    # Business logic
│   │       └── ui/          # Swing UI components
│   ├── build.sh         # Build script
│   └── README.md
├── web/                 # Web application (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/banking/web/
│   │   │   │   ├── models/         # Data models
│   │   │   │   ├── services/       # Business logic
│   │   │   │   ├── controllers/    # REST & Web controllers
│   │   │   │   └── BankingWebApplication.java
│   │   │   └── resources/
│   │   │       ├── templates/      # Thymeleaf templates
│   │   │       ├── static/css/     # Stylesheets
│   │   │       └── application.properties
│   ├── pom.xml          # Maven configuration
│   ├── README.md
│   └── API_TESTING.md
├── QUICKSTART.md        # Quick start guide
└── IMPLEMENTATION_SUMMARY.md  # Detailed implementation summary
```

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 17 or higher**
- **Maven 3.6 or higher** (for web application)
- **Git** (for cloning the repository)

## Running the Desktop Application

```bash
cd banking-system/desktop
./build.sh
java -cp target/classes com.banking.desktop.ui.LoginFrame
```

For detailed instructions, see [desktop/README.md](desktop/README.md)

## Running the Web Application

```bash
cd banking-system/web
mvn clean install
mvn spring-boot:run
```

Then open your browser and navigate to: `http://localhost:8080`

For detailed instructions and API documentation, see [web/README.md](web/README.md)

## Default Test Credentials

Both applications come with a pre-configured test account:
- **Username:** admin
- **Password:** admin123
- **Account Number:** 1001
- **Initial Balance:** $10,000.00

## Key Models

### User
- userId, username, password, email, fullName

### Account
- accountNumber, userId, accountType, balance, accountHolderName

### Transaction
- transactionId, accountNumber, transactionType, amount, timestamp, description, balanceAfter

## API Endpoints (Web Application)

- `POST /api/register` - Register new user
- `POST /api/login` - Login
- `POST /api/accounts/create` - Create account
- `GET /api/accounts/{userId}` - Get user accounts
- `POST /api/deposit` - Deposit money
- `POST /api/withdraw` - Withdraw money
- `POST /api/transfer` - Transfer money
- `GET /api/transactions/{accountNumber}` - Get transaction history
- `GET /api/balance/{accountNumber}` - Get account balance

## Documentation

- [QUICKSTART.md](QUICKSTART.md) - Quick start guide
- [IMPLEMENTATION_SUMMARY.md](IMPLEMENTATION_SUMMARY.md) - Detailed implementation summary
- [desktop/README.md](desktop/README.md) - Desktop app documentation
- [web/README.md](web/README.md) - Web app documentation
- [web/API_TESTING.md](web/API_TESTING.md) - Complete API testing guide

## Contributing

Contributions are welcome! Please feel free to:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## Notes

- Both applications use in-memory storage (data is not persisted)
- Each application maintains its own separate data store
- The desktop and web applications can run simultaneously on the same machine
- All monetary values are handled as double precision floating-point numbers

## License

This project is currently unlicensed. You may choose to add one as appropriate.

## Author

- [pratyushsen655](https://github.com/pratyushsen655)

---

For questions or support, please open an issue on GitHub.
