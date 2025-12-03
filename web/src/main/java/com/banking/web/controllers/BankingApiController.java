package com.banking.web.controllers;

import com.banking.web.models.Account;
import com.banking.web.models.Transaction;
import com.banking.web.models.User;
import com.banking.web.services.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BankingApiController {

    @Autowired
    private BankingService bankingService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        if (bankingService.registerUser(user)) {
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("userId", user.getUserId());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Username already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        User user = bankingService.login(username, password);
        if (user != null) {
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("user", user);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/accounts/create")
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody Map<String, Object> accountData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String userId = (String) accountData.get("userId");
            String accountType = (String) accountData.get("accountType");
            double initialBalance = Double.parseDouble(accountData.get("initialBalance").toString());
            String accountHolderName = (String) accountData.get("accountHolderName");
            
            String accountNumber = bankingService.createAccount(userId, accountType, initialBalance, accountHolderName);
            
            response.put("success", true);
            response.put("message", "Account created successfully");
            response.put("accountNumber", accountNumber);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to create account: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/accounts/{userId}")
    public ResponseEntity<List<Account>> getUserAccounts(@PathVariable String userId) {
        List<Account> accounts = bankingService.getUserAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = bankingService.getAccount(accountNumber);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable String accountNumber) {
        Map<String, Object> response = new HashMap<>();
        Account account = bankingService.getAccount(accountNumber);
        
        if (account != null) {
            response.put("success", true);
            response.put("accountNumber", accountNumber);
            response.put("balance", account.getBalance());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Account not found");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<Map<String, Object>> deposit(@RequestBody Map<String, Object> depositData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String accountNumber = (String) depositData.get("accountNumber");
            double amount = Double.parseDouble(depositData.get("amount").toString());
            
            if (bankingService.deposit(accountNumber, amount)) {
                response.put("success", true);
                response.put("message", "Deposit successful");
                response.put("newBalance", bankingService.getBalance(accountNumber));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Deposit failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Map<String, Object>> withdraw(@RequestBody Map<String, Object> withdrawData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String accountNumber = (String) withdrawData.get("accountNumber");
            double amount = Double.parseDouble(withdrawData.get("amount").toString());
            
            if (bankingService.withdraw(accountNumber, amount)) {
                response.put("success", true);
                response.put("message", "Withdrawal successful");
                response.put("newBalance", bankingService.getBalance(accountNumber));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Withdrawal failed - insufficient balance");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transfer(@RequestBody Map<String, Object> transferData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String fromAccountNumber = (String) transferData.get("fromAccountNumber");
            String toAccountNumber = (String) transferData.get("toAccountNumber");
            double amount = Double.parseDouble(transferData.get("amount").toString());
            
            if (bankingService.transfer(fromAccountNumber, toAccountNumber, amount)) {
                response.put("success", true);
                response.put("message", "Transfer successful");
                response.put("newBalance", bankingService.getBalance(fromAccountNumber));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Transfer failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountNumber) {
        List<Transaction> transactions = bankingService.getAccountTransactions(accountNumber);
        return ResponseEntity.ok(transactions);
    }
}
