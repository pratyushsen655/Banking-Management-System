# Quick Start Guide - Banking Management System

This guide will help you quickly get started with the Banking Management System.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher (for web application)

## Desktop Application (Quickest way to get started)

### Build and Run

```bash
# Navigate to desktop directory
cd desktop

# Build the application
./build.sh

# Run the application
java -cp target/classes com.banking.desktop.ui.LoginFrame
```

### Login
Use the default credentials:
- **Username:** admin
- **Password:** admin123

Or create a new account using the "Sign Up" button.

### What You Can Do
1. **Create Accounts** - Create Savings or Current accounts
2. **Deposit** - Add money to your accounts
3. **Withdraw** - Remove money from your accounts
4. **Transfer** - Transfer money between accounts
5. **View Balance** - Check your account balance
6. **Transaction History** - View all transactions for any account

## Web Application

### Build and Run

```bash
# Navigate to web directory
cd web

# Build and run with Maven
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Access the Web Interface
1. Open your browser and navigate to: `http://localhost:8080`
2. You'll be redirected to the login page
3. Use the default credentials:
   - **Username:** admin
   - **Password:** admin123
4. Or register a new account

### Test the REST API

```bash
# Login
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Get accounts (use userId from login response)
curl http://localhost:8080/api/accounts/YOUR_USER_ID

# Deposit money
curl -X POST http://localhost:8080/api/deposit \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"1001","amount":500}'

# Check balance
curl http://localhost:8080/api/balance/1001

# View transaction history
curl http://localhost:8080/api/transactions/1001
```

## Sample Workflows

### Creating Your First Account

**Desktop:**
1. Login → Dashboard → Create Account button
2. Fill in account holder name
3. Select account type (Savings/Current)
4. Enter initial deposit amount
5. Click Create

**Web:**
1. Login → Dashboard → Create Account button
2. Fill in the form
3. Click Create Account
4. You'll be redirected to the dashboard with your new account

### Making a Transfer

**Desktop:**
1. Dashboard → Select source account → Transfer button
2. Enter destination account number
3. Enter amount
4. Click Transfer

**Web:**
1. Dashboard → Transfer button
2. Select source account from dropdown
3. Enter destination account number
4. Enter amount
5. Click Transfer

### Viewing Transaction History

**Desktop:**
1. Dashboard → Select an account → View History link in Actions column

**Web:**
1. Dashboard → Click "View History" link for any account

## Troubleshooting

### Desktop Application
- **Issue:** Application won't start
  - **Solution:** Make sure Java 17 or higher is installed: `java -version`
  
- **Issue:** Build fails
  - **Solution:** Check that all source files are present in the correct directory structure

### Web Application
- **Issue:** Port 8080 already in use
  - **Solution:** Either stop the process using port 8080 or change the port in `application.properties`
  
- **Issue:** Maven build fails
  - **Solution:** Make sure Maven is installed: `mvn -version`
  - Try: `mvn clean install` to rebuild from scratch

## Key Features to Explore

1. **Multiple Accounts** - Each user can have multiple bank accounts
2. **Real-time Balance Updates** - See balances update immediately after transactions
3. **Transaction History** - Complete audit trail of all operations
4. **Transfer Between Accounts** - Move money between any two accounts
5. **Account Types** - Support for both Savings and Current accounts

## Default Test Data

The system comes pre-loaded with:
- **User:** Administrator (username: admin, password: admin123)
- **Account:** 1001 (Savings, $10,000.00)

You can use this to test all features immediately without creating new data.

## Next Steps

1. Explore the user interface and try different operations
2. Create your own user account
3. Create multiple accounts and practice transfers
4. View transaction history to see how operations are recorded
5. Test the REST API if using the web application

For more detailed information, refer to:
- [Desktop Application README](desktop/README.md)
- [Web Application README](web/README.md)
- [Main README](README.md)

## Need Help?

If you encounter any issues:
1. Check the README files for detailed information
2. Review the troubleshooting section above
3. Open an issue on GitHub with details about the problem
