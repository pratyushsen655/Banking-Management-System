package com.banking.web.services;

import com.banking.web.models.Account;
import com.banking.web.models.Transaction;
import com.banking.web.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BankingService {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Account> accounts = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();
    private Map<String, List<String>> userAccounts = new HashMap<>();

    public BankingService() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        User sampleUser = new User("admin", "admin123", "admin@bank.com", "Administrator");
        users.put(sampleUser.getUsername(), sampleUser);
        
        Account sampleAccount = new Account("1001", sampleUser.getUserId(), "Savings", 10000.0, "Administrator");
        accounts.put(sampleAccount.getAccountNumber(), sampleAccount);
        
        userAccounts.put(sampleUser.getUserId(), new ArrayList<>());
        userAccounts.get(sampleUser.getUserId()).add(sampleAccount.getAccountNumber());
    }

    public boolean registerUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        userAccounts.put(user.getUserId(), new ArrayList<>());
        return true;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public User getUserById(String userId) {
        for (User user : users.values()) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public String createAccount(String userId, String accountType, double initialBalance, String accountHolderName) {
        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, userId, accountType, initialBalance, accountHolderName);
        accounts.put(accountNumber, account);
        
        if (!userAccounts.containsKey(userId)) {
            userAccounts.put(userId, new ArrayList<>());
        }
        userAccounts.get(userId).add(accountNumber);
        
        if (initialBalance > 0) {
            Transaction transaction = new Transaction(accountNumber, "DEPOSIT", initialBalance, 
                "Initial deposit", initialBalance);
            transactions.add(transaction);
        }
        
        return accountNumber;
    }

    public List<Account> getUserAccounts(String userId) {
        List<String> accountNumbers = userAccounts.get(userId);
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
        
        if (fromAccount.withdraw(amount) && toAccount.deposit(amount)) {
            Transaction debitTransaction = new Transaction(fromAccountNumber, "TRANSFER", amount, 
                "Transfer to " + toAccountNumber, fromAccount.getBalance());
            Transaction creditTransaction = new Transaction(toAccountNumber, "TRANSFER", amount, 
                "Transfer from " + fromAccountNumber, toAccount.getBalance());
            
            transactions.add(debitTransaction);
            transactions.add(creditTransaction);
            return true;
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
