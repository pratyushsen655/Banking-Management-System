package com.banking.web.controllers;

import com.banking.web.models.Account;
import com.banking.web.models.Transaction;
import com.banking.web.models.User;
import com.banking.web.services.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BankingWebController {

    @Autowired
    private BankingService bankingService;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        User user = bankingService.login(username, password);
        if (user != null) {
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("fullName", user.getFullName());
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String email,
                          @RequestParam String fullName,
                          RedirectAttributes redirectAttributes) {
        User user = new User(username, password, email, fullName);
        if (bankingService.registerUser(user)) {
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Username already exists");
            return "redirect:/register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        String fullName = (String) session.getAttribute("fullName");
        List<Account> accounts = bankingService.getUserAccounts(userId);
        
        model.addAttribute("fullName", fullName);
        model.addAttribute("accounts", accounts);
        model.addAttribute("userId", userId);
        return "dashboard";
    }

    @GetMapping("/create-account")
    public String createAccountPage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("userId", userId);
        return "create-account";
    }

    @PostMapping("/create-account")
    public String createAccount(@RequestParam String accountType,
                               @RequestParam double initialBalance,
                               @RequestParam String accountHolderName,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        String accountNumber = bankingService.createAccount(userId, accountType, initialBalance, accountHolderName);
        redirectAttributes.addFlashAttribute("success", "Account created successfully! Account Number: " + accountNumber);
        return "redirect:/dashboard";
    }

    @GetMapping("/deposit")
    public String depositPage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        List<Account> accounts = bankingService.getUserAccounts(userId);
        model.addAttribute("accounts", accounts);
        return "deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam String accountNumber,
                         @RequestParam double amount,
                         RedirectAttributes redirectAttributes) {
        if (bankingService.deposit(accountNumber, amount)) {
            redirectAttributes.addFlashAttribute("success", "Deposit successful!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Deposit failed");
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/withdraw")
    public String withdrawPage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        List<Account> accounts = bankingService.getUserAccounts(userId);
        model.addAttribute("accounts", accounts);
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String accountNumber,
                          @RequestParam double amount,
                          RedirectAttributes redirectAttributes) {
        if (bankingService.withdraw(accountNumber, amount)) {
            redirectAttributes.addFlashAttribute("success", "Withdrawal successful!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Withdrawal failed - insufficient balance");
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/transfer")
    public String transferPage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        List<Account> accounts = bankingService.getUserAccounts(userId);
        model.addAttribute("accounts", accounts);
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String fromAccountNumber,
                          @RequestParam String toAccountNumber,
                          @RequestParam double amount,
                          RedirectAttributes redirectAttributes) {
        if (bankingService.transfer(fromAccountNumber, toAccountNumber, amount)) {
            redirectAttributes.addFlashAttribute("success", "Transfer successful!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Transfer failed");
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/history/{accountNumber}")
    public String transactionHistory(@PathVariable String accountNumber,
                                     HttpSession session,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        Account account = bankingService.getAccount(accountNumber);
        if (account == null) {
            redirectAttributes.addFlashAttribute("error", "Account not found");
            return "redirect:/dashboard";
        }
        
        // Check if the account belongs to the logged-in user
        if (!account.getUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized access to account");
            return "redirect:/dashboard";
        }
        
        List<Transaction> transactions = bankingService.getAccountTransactions(accountNumber);
        
        model.addAttribute("account", account);
        model.addAttribute("transactions", transactions);
        return "history";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
