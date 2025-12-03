# Desktop Banking Application

A Java Swing-based desktop application for banking management.

## Features

- User registration and login
- Account creation (Savings/Current)
- Deposit money
- Withdraw money
- Transfer money between accounts
- View account balance
- View transaction history
- In-memory data storage

## Building the Application

### Prerequisites
- JDK 17 or higher
- Java Swing (included in JDK)

### Build Instructions

```bash
cd desktop
./build.sh
```

### Running the Application

```bash
cd desktop
java -cp target/classes com.banking.desktop.ui.LoginFrame
```

## Default Credentials

For testing purposes, a default admin account is available:
- Username: `admin`
- Password: `admin123`
- Account Number: `1001`
- Balance: $10,000.00

## Project Structure

```
desktop/
├── src/main/java/com/banking/desktop/
│   ├── models/           # Data models (User, Account, Transaction)
│   ├── services/         # Business logic (BankingService)
│   └── ui/              # Swing UI components
│       ├── LoginFrame.java
│       ├── SignupFrame.java
│       ├── DashboardFrame.java
│       └── TransactionHistoryFrame.java
└── target/              # Compiled classes
```

## Usage

1. **Login/Register**: Start the application and login with existing credentials or create a new account
2. **Dashboard**: View all your accounts and perform operations
3. **Create Account**: Create new bank accounts (Savings or Current)
4. **Deposit**: Add money to your accounts
5. **Withdraw**: Remove money from your accounts
6. **Transfer**: Transfer money between accounts
7. **Transaction History**: View all transactions for any account

## Notes

- All data is stored in memory and will be lost when the application closes
- The application uses a singleton pattern for the BankingService
- Basic validation is implemented for all operations
