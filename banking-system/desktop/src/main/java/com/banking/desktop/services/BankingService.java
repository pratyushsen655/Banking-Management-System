package com.banking.desktop.services;

import com.banking.desktop.models.Account;
import com.banking.desktop.models.Transaction;
import com.banking.desktop.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BankingService {
    private static BankingService instance;
    private Map<String, User> users; // username -> User
    private Map<String, Account> accounts; // accountNumber -> Account
    private List<Transaction> transactions;
    private Map<String, List<String>> userAccounts; // userId -> List of accountNumbers
    private String currentUserId;

    private BankingService() {
        users = new HashMap<>();
        accounts = new HashMap<>();
        transactions = new ArrayList<>();
        userAccounts = new HashMap<>();
        currentUserId = null;
        initializeSampleData();
    }

    public static BankingService getInstance() {
        if (instance == null) {
            instance = new BankingService();
        }
        return instance;
    }

    private void initializeSampleData() {
        // Create a sample user and account for testing
        User sampleUser = new User("admin", "admin123", "admin@bank.com", "Administrator");
        users.put(sampleUser.getUsername(), sampleUser);
        
        Account sampleAccount = new Account("1001", sampleUser.getUserId(), "Savings", 10000.0, "Administrator");
        accounts.put(sampleAccount.getAccountNumber(), sampleAccount);
        
        userAccounts.put(sampleUser.getUserId(), new ArrayList<>());
        userAccounts.get(sampleUser.getUserId()).add(sampleAccount.getAccountNumber());
    }

    // User operations
    public boolean registerUser(String username, String password, String email, String fullName) {
        if (users.containsKey(username)) {
            return false;
        }
        User user = new User(username, password, email, fullName);
        users.put(username, user);
        userAccounts.put(user.getUserId(), new ArrayList<>());
        return true;
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUserId = user.getUserId();
            return true;
        }
        return false;
    }

    public void logout() {
        currentUserId = null;
    }

    public User getCurrentUser() {
        if (currentUserId != null) {
            for (User user : users.values()) {
                if (user.getUserId().equals(currentUserId)) {
                    return user;
                }
            }
        }
        return null;
    }

    // Account operations
    public String createAccount(String accountType, double initialBalance, String accountHolderName) {
        if (currentUserId == null) {
            return null;
        }
        
        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, currentUserId, accountType, initialBalance, accountHolderName);
        accounts.put(accountNumber, account);
        
        userAccounts.get(currentUserId).add(accountNumber);
        
        if (initialBalance > 0) {
            Transaction transaction = new Transaction(accountNumber, "DEPOSIT", initialBalance, 
                "Initial deposit", initialBalance);
            transactions.add(transaction);
        }
        
        return accountNumber;
    }

    public List<Account> getUserAccounts() {
        if (currentUserId == null) {
            return new ArrayList<>();
        }
        
        List<String> accountNumbers = userAccounts.get(currentUserId);
        if (accountNumbers == null) {
            return new ArrayList<>();
        }
        
        return accountNumbers.stream()
                .map(accounts::get)
                .collect(Collectors.toList());
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    // Transaction operations
    public boolean deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null || amount <= 0) {
            return false;
        }
        
        if (account.deposit(amount)) {
            Transaction transaction = new Transaction(accountNumber, "DEPOSIT", amount, 
                "Deposit to account", account.getBalance());
            transactions.add(transaction);
            return true;
        }
        return false;
    }

    public boolean withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null || amount <= 0) {
            return false;
        }
        
        if (account.withdraw(amount)) {
            Transaction transaction = new Transaction(accountNumber, "WITHDRAW", amount, 
                "Withdrawal from account", account.getBalance());
            transactions.add(transaction);
            return true;
        }
        return false;
    }

    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accounts.get(fromAccountNumber);
        Account toAccount = accounts.get(toAccountNumber);
        
        if (fromAccount == null || toAccount == null || amount <= 0) {
            return false;
        }
        
        // Check if withdrawal is possible before attempting
        if (fromAccount.getBalance() < amount) {
            return false;
        }
        
        // Perform transfer atomically
        if (fromAccount.withdraw(amount)) {
            if (toAccount.deposit(amount)) {
                Transaction debitTransaction = new Transaction(fromAccountNumber, "TRANSFER", amount, 
                    "Transfer to " + toAccountNumber, fromAccount.getBalance());
                Transaction creditTransaction = new Transaction(toAccountNumber, "TRANSFER", amount, 
                    "Transfer from " + fromAccountNumber, toAccount.getBalance());
                
                transactions.add(debitTransaction);
                transactions.add(creditTransaction);
                return true;
            } else {
                // Rollback withdrawal if deposit fails
                fromAccount.deposit(amount);
                return false;
            }
        }
        return false;
    }

    public List<Transaction> getAccountTransactions(String accountNumber) {
        return transactions.stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
    }

    public double getBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }

    private String generateAccountNumber() {
        int accountCount = accounts.size() + 1000;
        return String.valueOf(accountCount + 1);
    }
}
