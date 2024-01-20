import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMoney {
    private JFrame addMoneyFrame;
    private JTextField cardNumberField;
    private JPasswordField cvvField;
    private JButton addMoneyButton;
    private JLabel moneyLabel;
    private double moneyAmount;

    public AddMoney() {
        createAddMoneyScene();
    }

    public void showAddMoneyScene() {
        SwingUtilities.invokeLater(() -> {
            addMoneyFrame.setVisible(true);
            addMoneyFrame.setLocationRelativeTo(null); // Center the frame on the screen
        });
    }

    private void createAddMoneyScene() {
        addMoneyFrame = new JFrame("Add Money");
        addMoneyFrame.setSize(300, 200);
        addMoneyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel addMoneyPanel = new JPanel();
        addMoneyPanel.setLayout(new GridLayout(4, 2));

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField();

        JLabel cvvLabel = new JLabel("CVV:");
        cvvField = new JPasswordField();

        moneyLabel = new JLabel("Money: $" + moneyAmount);
        moneyLabel.setHorizontalAlignment(JLabel.CENTER);

        addMoneyButton = new JButton("Add Money");
        addMoneyButton.addActionListener(e -> addMoney());

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> addMoneyFrame.dispose());

        addMoneyPanel.add(cardNumberLabel);
        addMoneyPanel.add(cardNumberField);
        addMoneyPanel.add(cvvLabel);
        addMoneyPanel.add(cvvField);
        addMoneyPanel.add(new JLabel()); // Empty label for layout
        addMoneyPanel.add(new JLabel()); // Empty label for layout
        addMoneyPanel.add(addMoneyButton);
        addMoneyPanel.add(closeButton);

        addMoneyFrame.add(addMoneyPanel);
        addMoneyFrame.setVisible(false);
    }

    private void addMoney() {
        String cardNumber = cardNumberField.getText();
        char[] cvvChars = cvvField.getPassword();
        String cvv = new String(cvvChars);

        if (isValidCardNumber(cardNumber) && isValidCVV(cvv)) {
            String moneyInput = JOptionPane.showInputDialog(addMoneyFrame, "Enter Amount:");

            if (moneyInput != null) {
                try {
                    double moneyToAdd = Double.parseDouble(moneyInput);
                    if (moneyToAdd > 0) {
                        moneyAmount += moneyToAdd;
                        updateMoneyLabel();
                        JOptionPane.showMessageDialog(addMoneyFrame, "Money added successfully!");
                    } else {
                        JOptionPane.showMessageDialog(addMoneyFrame, "Please enter a valid positive amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(addMoneyFrame, "Invalid input. Please enter a valid number.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(addMoneyFrame, "Invalid card number or CVV. Please check your input.");
        }
    }

    private void updateMoneyLabel() {
        moneyLabel.setText("Money: $" + moneyAmount);
    }

    private boolean isValidCardNumber(String cardNumber) {
        // Add your card number validation logic here
        return cardNumber.length() >= 16;
    }

    private boolean isValidCVV(String cvv) {
        // Add your CVV validation logic here
        return cvv.length() == 3;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddMoney().showAddMoneyScene());
    }
}