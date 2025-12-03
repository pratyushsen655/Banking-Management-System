# API Testing Guide - Banking Web Application

This guide provides comprehensive examples for testing all REST API endpoints.

## Base URL
```
http://localhost:8080/api
```

## Authentication Endpoints

### 1. Register a New User

**Endpoint:** `POST /api/register`

**Request:**
```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "secure123",
    "email": "john@example.com",
    "fullName": "John Doe"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 2. Login

**Endpoint:** `POST /api/login`

**Request:**
```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "user": {
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "username": "admin",
    "email": "admin@bank.com",
    "fullName": "Administrator"
  }
}
```

## Account Management Endpoints

### 3. Create Account

**Endpoint:** `POST /api/accounts/create`

**Request:**
```bash
curl -X POST http://localhost:8080/api/accounts/create \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "accountType": "Savings",
    "initialBalance": 5000.00,
    "accountHolderName": "John Doe"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Account created successfully",
  "accountNumber": "1002"
}
```

### 4. Get User Accounts

**Endpoint:** `GET /api/accounts/{userId}`

**Request:**
```bash
curl http://localhost:8080/api/accounts/550e8400-e29b-41d4-a716-446655440000
```

**Response:**
```json
[
  {
    "accountNumber": "1001",
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "accountType": "Savings",
    "balance": 10000.0,
    "accountHolderName": "Administrator"
  },
  {
    "accountNumber": "1002",
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "accountType": "Current",
    "balance": 5000.0,
    "accountHolderName": "Administrator"
  }
]
```

### 5. Get Account Details

**Endpoint:** `GET /api/account/{accountNumber}`

**Request:**
```bash
curl http://localhost:8080/api/account/1001
```

**Response:**
```json
{
  "accountNumber": "1001",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "accountType": "Savings",
  "balance": 10000.0,
  "accountHolderName": "Administrator"
}
```

### 6. Get Account Balance

**Endpoint:** `GET /api/balance/{accountNumber}`

**Request:**
```bash
curl http://localhost:8080/api/balance/1001
```

**Response:**
```json
{
  "success": true,
  "accountNumber": "1001",
  "balance": 10000.0
}
```

## Transaction Endpoints

### 7. Deposit Money

**Endpoint:** `POST /api/deposit`

**Request:**
```bash
curl -X POST http://localhost:8080/api/deposit \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "1001",
    "amount": 2500.50
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Deposit successful",
  "newBalance": 12500.50
}
```

### 8. Withdraw Money

**Endpoint:** `POST /api/withdraw`

**Request:**
```bash
curl -X POST http://localhost:8080/api/withdraw \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "1001",
    "amount": 1000.00
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Withdrawal successful",
  "newBalance": 11500.50
}
```

**Error Response (Insufficient Balance):**
```json
{
  "success": false,
  "message": "Withdrawal failed - insufficient balance"
}
```

### 9. Transfer Money

**Endpoint:** `POST /api/transfer`

**Request:**
```bash
curl -X POST http://localhost:8080/api/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "fromAccountNumber": "1001",
    "toAccountNumber": "1002",
    "amount": 500.00
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Transfer successful",
  "newBalance": 11000.50
}
```

### 10. Get Transaction History

**Endpoint:** `GET /api/transactions/{accountNumber}`

**Request:**
```bash
curl http://localhost:8080/api/transactions/1001
```

**Response:**
```json
[
  {
    "transactionId": "123e4567-e89b-12d3-a456-426614174000",
    "accountNumber": "1001",
    "transactionType": "DEPOSIT",
    "amount": 10000.0,
    "timestamp": "2024-12-03T10:30:00",
    "description": "Initial deposit",
    "balanceAfter": 10000.0
  },
  {
    "transactionId": "223e4567-e89b-12d3-a456-426614174001",
    "accountNumber": "1001",
    "transactionType": "DEPOSIT",
    "amount": 2500.50,
    "timestamp": "2024-12-03T11:15:00",
    "description": "Deposit to account",
    "balanceAfter": 12500.50
  },
  {
    "transactionId": "323e4567-e89b-12d3-a456-426614174002",
    "accountNumber": "1001",
    "transactionType": "WITHDRAW",
    "amount": 1000.0,
    "timestamp": "2024-12-03T11:30:00",
    "description": "Withdrawal from account",
    "balanceAfter": 11500.50
  },
  {
    "transactionId": "423e4567-e89b-12d3-a456-426614174003",
    "accountNumber": "1001",
    "transactionType": "TRANSFER",
    "amount": 500.0,
    "timestamp": "2024-12-03T11:45:00",
    "description": "Transfer to 1002",
    "balanceAfter": 11000.50
  }
]
```

## Complete Test Workflow

Here's a complete workflow to test all functionality:

```bash
# 1. Register a new user
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"test123","email":"test@example.com","fullName":"Test User"}'

# 2. Login and get userId
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"test123"}'

# Use the userId from the response in subsequent requests
USER_ID="YOUR_USER_ID_FROM_LOGIN"

# 3. Create an account
curl -X POST http://localhost:8080/api/accounts/create \
  -H "Content-Type: application/json" \
  -d "{\"userId\":\"$USER_ID\",\"accountType\":\"Savings\",\"initialBalance\":1000,\"accountHolderName\":\"Test User\"}"

# Use the accountNumber from the response
ACCOUNT_NUMBER="YOUR_ACCOUNT_NUMBER"

# 4. Check balance
curl http://localhost:8080/api/balance/$ACCOUNT_NUMBER

# 5. Deposit money
curl -X POST http://localhost:8080/api/deposit \
  -H "Content-Type: application/json" \
  -d "{\"accountNumber\":\"$ACCOUNT_NUMBER\",\"amount\":500}"

# 6. Check new balance
curl http://localhost:8080/api/balance/$ACCOUNT_NUMBER

# 7. Withdraw money
curl -X POST http://localhost:8080/api/withdraw \
  -H "Content-Type: application/json" \
  -d "{\"accountNumber\":\"$ACCOUNT_NUMBER\",\"amount\":200}"

# 8. View transaction history
curl http://localhost:8080/api/transactions/$ACCOUNT_NUMBER

# 9. Create second account for transfer
curl -X POST http://localhost:8080/api/accounts/create \
  -H "Content-Type: application/json" \
  -d "{\"userId\":\"$USER_ID\",\"accountType\":\"Current\",\"initialBalance\":500,\"accountHolderName\":\"Test User\"}"

ACCOUNT_NUMBER_2="YOUR_SECOND_ACCOUNT_NUMBER"

# 10. Transfer between accounts
curl -X POST http://localhost:8080/api/transfer \
  -H "Content-Type: application/json" \
  -d "{\"fromAccountNumber\":\"$ACCOUNT_NUMBER\",\"toAccountNumber\":\"$ACCOUNT_NUMBER_2\",\"amount\":100}"

# 11. Check all accounts
curl http://localhost:8080/api/accounts/$USER_ID
```

## Using with Postman

1. Import the following environment variables:
   - `baseUrl`: `http://localhost:8080/api`
   - `userId`: (obtained from login)
   - `accountNumber`: (obtained from create account)

2. Create a collection with all the endpoints listed above

3. Set request headers:
   - `Content-Type`: `application/json`

4. Use variables in your requests:
   - `{{baseUrl}}/login`
   - `{{baseUrl}}/accounts/{{userId}}`

## Error Responses

### Invalid Credentials
```json
{
  "success": false,
  "message": "Invalid credentials"
}
```

### Account Not Found
```json
{
  "success": false,
  "message": "Account not found"
}
```

### Insufficient Balance
```json
{
  "success": false,
  "message": "Withdrawal failed - insufficient balance"
}
```

### Invalid Amount
```json
{
  "success": false,
  "message": "Error: Invalid amount"
}
```

## Notes

- All monetary values use double precision
- Account numbers are auto-generated starting from 1001
- Transaction IDs and User IDs are UUIDs
- Timestamps are in ISO 8601 format
- All data is stored in-memory and will be lost on application restart
