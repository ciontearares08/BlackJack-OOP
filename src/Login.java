import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JFrame loginFrame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {
        showLoginScene();
    }

    public void showLoginScene() {
        loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 150);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        emailField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // For simplicity, you can add any logic for checking the credentials here
                // For example, you can check if the email and password are not empty
                if (!email.isEmpty() && !password.isEmpty()) {
                    JOptionPane.showMessageDialog(loginFrame, "Login successful!");

                    // Close the login window
                    loginFrame.dispose();

                    // Start the Blackjack game
                    SwingUtilities.invokeLater(() -> new ChoosingWTD().showChoosingScene());
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid email or password. Please try again.");
                }
            }
        });

        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Empty label for layout
        loginPanel.add(loginButton);

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
