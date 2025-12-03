package com.banking.desktop.ui;

import com.banking.desktop.models.Transaction;
import com.banking.desktop.services.BankingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransactionHistoryFrame extends JFrame {
    private BankingService bankingService;
    private String accountNumber;
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionHistoryFrame(String accountNumber) {
        this.bankingService = BankingService.getInstance();
        this.accountNumber = accountNumber;
        initComponents();
        loadTransactions();
    }

    private void initComponents() {
        setTitle("Transaction History - Account " + accountNumber);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 102, 204));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Transaction History - Account: " + accountNumber);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);

        // Table for transactions
        String[] columnNames = {"Transaction ID", "Type", "Amount", "Description", "Balance After", "Date & Time"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        transactionTable = new JTable(tableModel);
        transactionTable.setRowHeight(25);
        transactionTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        transactionTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        transactionTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        transactionTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        transactionTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 153, 76));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadTransactions());
        buttonPanel.add(refreshButton);

        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(204, 0, 0));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadTransactions() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = bankingService.getAccountTransactions(accountNumber);
        
        if (transactions.isEmpty()) {
            Object[] row = {"No transactions", "", "", "", "", ""};
            tableModel.addRow(row);
        } else {
            for (Transaction transaction : transactions) {
                String shortId = transaction.getTransactionId().length() > 8 
                    ? transaction.getTransactionId().substring(0, 8) + "..." 
                    : transaction.getTransactionId();
                Object[] row = {
                    shortId,
                    transaction.getTransactionType(),
                    String.format("$%.2f", transaction.getAmount()),
                    transaction.getDescription(),
                    String.format("$%.2f", transaction.getBalanceAfter()),
                    transaction.getFormattedTimestamp()
                };
                tableModel.addRow(row);
            }
        }
    }
}
