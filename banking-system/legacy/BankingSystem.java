import java.util.ArrayList;
import java.util.Scanner;

class Account {
    private int accNo;
    private String name;
    private String type;
    private double balance;

    public Account(int accNo, String name, String type, double balance) {
        this.accNo = accNo;
        this.name = name;
        this.type = type;
        this.balance = balance;
    }

    public int getAccNo() { return accNo; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getBalance() { return balance; }

    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setBalance(double balance) { this.balance = balance; }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
        else System.out.println("Deposit must be positive.");
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return false;
        }
        balance -= amount;
        return true;
    }

    public void display() {
        System.out.printf("%-10d %-15s %-10s %-10.2f\n", accNo, name, type, balance);
    }
}

public class BankingSystem {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Account> accounts = new ArrayList<>();

    // Search account
    public static Account searchAccount(int accNo) {
        for (Account a : accounts) {
            if (a.getAccNo() == accNo)
                return a;
        }
        return null;
    }

    // Create account
    public static void createAccount() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        if (searchAccount(accNo) != null) {
            System.out.println("Account already exists!");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Account Type (Savings/Current): ");
        String type = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        accounts.add(new Account(accNo, name, type, balance));
        System.out.println("Account created successfully!");
    }

    // Display all accounts
    public static void displayAll() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.printf("%-10s %-15s %-10s %-10s\n", "AccNo", "Name", "Type", "Balance");
        for (Account a : accounts) a.display();
    }

    // Deposit money
    public static void depositAmount() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account a = searchAccount(accNo);
        if (a == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Amount to Deposit: ");
        double amt = sc.nextDouble();
        a.deposit(amt);
        System.out.println("Amount deposited successfully!");
    }

    // Withdraw money
    public static void withdrawAmount() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account a = searchAccount(accNo);
        if (a == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Amount to Withdraw: ");
        double amt = sc.nextDouble();
        if (a.withdraw(amt))
            System.out.println("Withdrawal successful!");
    }

    // Modify account
    public static void modifyAccount() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account a = searchAccount(accNo);
        if (a == null) {
            System.out.println("Account not found!");
            return;
        }

        sc.nextLine();
        System.out.print("Enter New Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Type: ");
        String type = sc.nextLine();

        a.setName(name);
        a.setType(type);

        System.out.println("Account updated successfully!");
    }

    // Delete account
    public static void deleteAccount() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account a = searchAccount(accNo);
        if (a == null) {
            System.out.println("Account not found!");
            return;
        }

        accounts.remove(a);
        System.out.println("Account deleted successfully!");
    }

    // Menu
    public static void menu() {
        while (true) {
            System.out.println("\n===== BANKING MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Display All Accounts");
            System.out.println("3. Search Account");
            System.out.println("4. Deposit Money");
            System.out.println("5. Withdraw Money");
            System.out.println("6. Modify Account");
            System.out.println("7. Delete Account");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1: createAccount(); break;
                case 2: displayAll(); break;
                case 3:
                    System.out.print("Enter Account Number: ");
                    int accNo = sc.nextInt();
                    Account a = searchAccount(accNo);
                    if (a != null)
                        a.display();
                    else
                        System.out.println("Account not found!");
                    break;
                case 4: depositAmount(); break;
                case 5: withdrawAmount(); break;
                case 6: modifyAccount(); break;
                case 7: deleteAccount(); break;
                case 8:
                    System.out.println("Thank you for using the system!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void main(String[] args) {
        menu();
    }
}
