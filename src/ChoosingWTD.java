import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoosingWTD {
    private JFrame choosingFrame;
    private JButton playButton;
    private JButton addMoneyButton;
    private JLabel moneyLabel;
    private double moneyAmount = 0.0;
    private AddMoney addMoneyInstance;

    public ChoosingWTD() {
        addMoneyInstance = new AddMoney();
        createChoosingScene();
    }

    public void showChoosingScene() {
        SwingUtilities.invokeLater(() -> {
            choosingFrame.setVisible(true);
            choosingFrame.setLocationRelativeTo(null); // Center the frame on the screen
        });
    }

    private void createChoosingScene() {
        choosingFrame = new JFrame("Choose Action");
        choosingFrame.setSize(300, 150);
        choosingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel choosingPanel = new JPanel();
        choosingPanel.setLayout(new GridLayout(3, 1));

        playButton = new JButton("Play");
        playButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new BlackJack(moneyAmount));
            choosingFrame.dispose();
        });

        addMoneyButton = new JButton("Add Money");
        addMoneyButton.addActionListener(e -> {
            addMoneyInstance.showAddMoneyScene();
            moneyAmount = addMoneyInstance.getMoneyAmount();
            updateMoneyLabel();
        });

        moneyLabel = new JLabel("Money: $" + moneyAmount);
        moneyLabel.setHorizontalAlignment(JLabel.CENTER);

        choosingPanel.add(playButton);
        choosingPanel.add(addMoneyButton);
        choosingPanel.add(moneyLabel);

        choosingFrame.add(choosingPanel);
        choosingFrame.setVisible(false);
    }

    private void updateMoneyLabel() {
        moneyLabel.setText("Money: $" + moneyAmount);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChoosingWTD().showChoosingScene());
    }
}
