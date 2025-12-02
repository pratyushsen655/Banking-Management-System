import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankSignupPage extends JFrame implements ActionListener {
    JTextField nameField, emailField;
    JPasswordField passwordField;
    JComboBox<String> accountTypeBox;
    JButton signupButton;

    public BankSignupPage() {
        setTitle("Bank Signup Page");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Components
        add(new JLabel("Full Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        add(new JLabel("Account Type:"));
        String[] accountTypes = {"Savings", "Current", "Fixed Deposit"};
        accountTypeBox = new JComboBox<>(accountTypes);
        add(accountTypeBox);

        signupButton = new JButton("Sign Up");
        signupButton.addActionListener(this);
        add(signupButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String accountType = (String) accountTypeBox.getSelectedItem();

        // Basic validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
        } else {
            // Simulate saving to database
            JOptionPane.showMessageDialog(this,
                    "Signup Successful!\nName: " + name +
                            "\nEmail: " + email +
                            "\nAccount Type: " + accountType);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankSignupPage::new);
    }
}
