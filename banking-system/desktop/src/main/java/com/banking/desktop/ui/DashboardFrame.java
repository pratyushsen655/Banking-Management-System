package com.banking.desktop.ui;

import com.banking.desktop.models.Account;
import com.banking.desktop.models.User;
import com.banking.desktop.services.BankingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame {
    private BankingService bankingService;
    private JLabel welcomeLabel;
    private JTable accountTable;
    private DefaultTableModel tableModel;

    public DashboardFrame() {
        bankingService = BankingService.getInstance();
        initComponents();
        loadAccounts();
    }

    private void initComponents() {
        setTitle("Banking System - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel with welcome message
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 102, 204));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        User currentUser = bankingService.getCurrentUser();
        welcomeLabel = new JLabel("Welcome, " + (currentUser != null ? currentUser.getFullName() : "User"));
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(204, 0, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> handleLogout());
        topPanel.add(logoutButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel with account list
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel accountsLabel = new JLabel("Your Accounts");
        accountsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(accountsLabel, BorderLayout.NORTH);

        // Table for accounts
        String[] columnNames = {"Account Number", "Account Type", "Balance", "Account Holder"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        accountTable = new JTable(tableModel);
        accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(accountTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton createAccountButton = createStyledButton("Create Account", new Color(0, 153, 76));
        createAccountButton.addActionListener(e -> openCreateAccountDialog());
        buttonPanel.add(createAccountButton);

        JButton depositButton = createStyledButton("Deposit", new Color(0, 102, 204));
        depositButton.addActionListener(e -> openDepositDialog());
        buttonPanel.add(depositButton);

        JButton withdrawButton = createStyledButton("Withdraw", new Color(255, 102, 0));
        withdrawButton.addActionListener(e -> openWithdrawDialog());
        buttonPanel.add(withdrawButton);

        JButton transferButton = createStyledButton("Transfer", new Color(102, 0, 204));
        transferButton.addActionListener(e -> openTransferDialog());
        buttonPanel.add(transferButton);

        JButton balanceButton = createStyledButton("View Balance", new Color(0, 153, 153));
        balanceButton.addActionListener(e -> viewBalance());
        buttonPanel.add(balanceButton);

        JButton historyButton = createStyledButton("Transaction History", new Color(153, 102, 0));
        historyButton.addActionListener(e -> openTransactionHistory());
        buttonPanel.add(historyButton);

        JButton refreshButton = createStyledButton("Refresh", new Color(76, 153, 0));
        refreshButton.addActionListener(e -> loadAccounts());
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    private void loadAccounts() {
        tableModel.setRowCount(0);
        List<Account> accounts = bankingService.getUserAccounts();
        for (Account account : accounts) {
            Object[] row = {
                account.getAccountNumber(),
                account.getAccountType(),
                String.format("$%.2f", account.getBalance()),
                account.getAccountHolderName()
            };
            tableModel.addRow(row);
        }
    }

    private void openCreateAccountDialog() {
        JDialog dialog = new JDialog(this, "Create New Account", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Account Holder Name:"), gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        User currentUser = bankingService.getCurrentUser();
        nameField.setText(currentUser != null ? currentUser.getFullName() : "");
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Account Type:"), gbc);

        gbc.gridx = 1;
        String[] accountTypes = {"Savings", "Current"};
        JComboBox<String> typeCombo = new JComboBox<>(accountTypes);
        formPanel.add(typeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Initial Deposit:"), gbc);

        gbc.gridx = 1;
        JTextField depositField = new JTextField(20);
        depositField.setText("0.00");
        formPanel.add(depositField, gbc);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String type = (String) typeCombo.getSelectedItem();
                double amount = Double.parseDouble(depositField.getText());

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please enter account holder name");
                    return;
                }

                if (amount < 0) {
                    JOptionPane.showMessageDialog(dialog, "Initial deposit cannot be negative");
                    return;
                }

                String accountNumber = bankingService.createAccount(type, amount, name);
                if (accountNumber != null) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Account created successfully!\nAccount Number: " + accountNumber);
                    loadAccounts();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to create account");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid amount");
            }
        });
        buttonPanel.add(createButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void openDepositDialog() {
        String accountNumber = getSelectedAccountNumber();
        if (accountNumber == null) return;

        String amountStr = JOptionPane.showInputDialog(this, 
            "Enter amount to deposit:", "Deposit", JOptionPane.QUESTION_MESSAGE);
        
        if (amountStr != null && !amountStr.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (bankingService.deposit(accountNumber, amount)) {
                    JOptionPane.showMessageDialog(this, "Deposit successful!");
                    loadAccounts();
                } else {
                    JOptionPane.showMessageDialog(this, "Deposit failed. Please enter a valid amount.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number");
            }
        }
    }

    private void openWithdrawDialog() {
        String accountNumber = getSelectedAccountNumber();
        if (accountNumber == null) return;

        String amountStr = JOptionPane.showInputDialog(this, 
            "Enter amount to withdraw:", "Withdraw", JOptionPane.QUESTION_MESSAGE);
        
        if (amountStr != null && !amountStr.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (bankingService.withdraw(accountNumber, amount)) {
                    JOptionPane.showMessageDialog(this, "Withdrawal successful!");
                    loadAccounts();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Withdrawal failed. Insufficient balance or invalid amount.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number");
            }
        }
    }

    private void openTransferDialog() {
        String fromAccountNumber = getSelectedAccountNumber();
        if (fromAccountNumber == null) return;

        JDialog dialog = new JDialog(this, "Transfer Money", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("To Account Number:"), gbc);

        gbc.gridx = 1;
        JTextField toAccountField = new JTextField(20);
        formPanel.add(toAccountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        JTextField amountField = new JTextField(20);
        formPanel.add(amountField, gbc);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> {
            try {
                String toAccount = toAccountField.getText().trim();
                double amount = Double.parseDouble(amountField.getText());

                if (toAccount.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please enter destination account number");
                    return;
                }

                if (bankingService.transfer(fromAccountNumber, toAccount, amount)) {
                    JOptionPane.showMessageDialog(dialog, "Transfer successful!");
                    loadAccounts();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, 
                        "Transfer failed. Check account number and balance.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid amount");
            }
        });
        buttonPanel.add(transferButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void viewBalance() {
        String accountNumber = getSelectedAccountNumber();
        if (accountNumber == null) return;

        double balance = bankingService.getBalance(accountNumber);
        JOptionPane.showMessageDialog(this, 
            String.format("Current Balance: $%.2f", balance), 
            "Account Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openTransactionHistory() {
        String accountNumber = getSelectedAccountNumber();
        if (accountNumber == null) return;

        new TransactionHistoryFrame(accountNumber).setVisible(true);
    }

    private String getSelectedAccountNumber() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an account first");
            return null;
        }
        return (String) tableModel.getValueAt(selectedRow, 0);
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Logout", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            bankingService.logout();
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
