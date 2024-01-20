import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class BlackJack {

    ArrayList<Card> deck;
    Random random = new Random(); // shuffle deck

    // dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;
    double moneyAmount; // Added field to store the player's money
    JLabel moneyLabel; // Added label for displaying current money
    boolean stayPressed;

    // player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    // window
    int boardWidth = 600;
    int boardHeight = boardWidth;

    int cardWidth = 110; // ratio should be 1/1.4
    int cardHeight = 154;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                // draw hidden card
                Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                if (!stayButton.isEnabled()) {
                    hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
                }
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                // draw dealer's hand with sum
                for (int i = 0; i < dealerHand.size(); i++) {
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);
                }
                g.setFont(new Font("Arial", Font.PLAIN, 20));
                g.setColor(Color.white);
                if(stayPressed==true) {
                    g.drawString("Sum: " + dealerSum, cardWidth + 25, 190); // Display sum of dealer's cards
                }else{
                    g.drawString("Sum: " + (dealerSum - hiddenCard.getValue()), cardWidth + 25, 190);

                }

                // draw player's hand with sum
                for (int i = 0; i < playerHand.size(); i++) {
                    Card card = playerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 20 + (cardWidth + 5) * i, 320, cardWidth, cardHeight, null);
                }
                g.drawString("Sum: " + playerSum, cardWidth+25, 490); // Display sum of player's cards

                if (!stayButton.isEnabled()) {
                    dealerSum = reduceDealerAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.println(dealerSum);
                    System.out.println(playerSum);

                    String message = "";
                    if (playerSum > 21) {
                        message = "You Lose!";
                    } else if (dealerSum > 21) {
                        message = "You Win!";
                    }
                    // both you and dealer <= 21W
                    else if (playerSum == dealerSum) {
                        message = "Tie!";
                    } else if (playerSum > dealerSum) {
                        message = "You Win!";
                    } else if (playerSum < dealerSum) {
                        message = "You Lose!";
                    }

                    g.setFont(new Font("Arial", Font.PLAIN, 30));
                    g.setColor(Color.white);
                    g.drawString(message, 220, 250);

                    // Display current money
                    g.setFont(new Font("Arial", Font.PLAIN, 20));
                    g.setColor(Color.white);
                    g.drawString("Current Money: $" + moneyAmount, 20, 550);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton resetButton = new JButton("Reset");
    JButton doubleDownButton = new JButton("Double Down");

    // Betting system components
    double betAmount; // Added field to store the current bet
    JTextField betInputField;
    JButton betButton;

    BlackJack(double initialMoney) {
        startGame(initialMoney);

        moneyLabel = new JLabel(" $" + moneyAmount);
        moneyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        moneyLabel.setForeground(Color.BLACK);
        buttonPanel.add(moneyLabel);

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        resetButton.setFocusable(false);
        buttonPanel.add(resetButton);
        doubleDownButton.setFocusable(false);
        buttonPanel.add(doubleDownButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Betting system components
        betInputField = new JTextField(10);
        buttonPanel.add(betInputField);

        betButton = new JButton("Bet");
        betButton.setFocusable(false);
        buttonPanel.add(betButton);

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Card card = deck.remove(deck.size() - 1);
                playerSum += card.getValue();
                playerAceCount += card.isAce() ? 1 : 0;
                playerHand.add(card);
                if (reducePlayerAce() > 21) { // A + 2 + J --> 1 + 2 + J
                    hitButton.setEnabled(false);
                    doubleDownButton.setEnabled(false);
                }
                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                doubleDownButton.setEnabled(false);
                stayPressed=true;

                while (dealerSum < 17) {
                    Card card = deck.remove(deck.size() - 1);
                    dealerSum += card.getValue();
                    dealerAceCount += card.isAce() ? 1 : 0;
                    dealerHand.add(card);
                }

                // Determine the winner and adjust money accordingly
                determineWinner();

                // Display the result and updated money
                gamePanel.repaint();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        doubleDownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doubleDown();
            }
        });

        // Betting system components
        betButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double bet = Double.parseDouble(betInputField.getText());
                    if (bet > 0 && bet <= moneyAmount) {
                        betAmount = bet;
                        moneyAmount -= betAmount;

                        // Disable the bet button until the next reset
                        betButton.setEnabled(false);

                        // Enable other buttons for gameplay
                        hitButton.setEnabled(true);
                        stayButton.setEnabled(true);
                        doubleDownButton.setEnabled(true);

                        // Update the total money label
                        moneyLabel.setText(" $" + moneyAmount);

                        // Display the result and updated money
                        gamePanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid bet amount!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid bet amount!");
                }
            }
        });


        gamePanel.repaint();
    }

    public void startGame(double initialMoney) {
        //deck
        buildDeck();
        shuffleDeck();

        //dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        hiddenCard = deck.remove(deck.size() - 1); //remove card at last index
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size() - 1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        System.out.println("DEALER:");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAceCount);

        //player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        System.out.println("PLAYER:");
        System.out.println(playerHand);
        System.out.println(playerSum);
        System.out.println(playerAceCount);

        // Set initial money
        moneyAmount = initialMoney;
    }

    public void buildDeck() {
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }

        System.out.println("BUILD DECK:");
        System.out.println(deck);
    }

    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }

        System.out.println("AFTER SHUFFLE");
        System.out.println(deck);
    }

    public int reducePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }

    public void resetGame() {
        startGame(moneyAmount); // Pass the current money amount to startGame
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        doubleDownButton.setEnabled(true);

        // Reset bet amount to 0
        betAmount = 0;
        betButton.setEnabled(true);
        moneyLabel.setText(" $" + moneyAmount);

        // Display the result and updated money
        gamePanel.repaint();
    }

    public void doubleDown() {
        // Draw a card
        Card card = deck.remove(deck.size() - 1);

        // Update player's hand and sum
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);

        // Double the bet amount
        betAmount *= 2;

        // Disable buttons after doubling down
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        doubleDownButton.setEnabled(false);

        // Dealer's turn
        while (dealerSum < 17) {
            Card dealerCard = deck.remove(deck.size() - 1);
            dealerSum += dealerCard.getValue();
            dealerAceCount += dealerCard.isAce() ? 1 : 0;
            dealerHand.add(dealerCard);
        }

        // Determine the winner and adjust money accordingly
        determineWinner();

        // Display the result and updated money
        gamePanel.repaint();
    }


    private void determineWinner() {
        if (playerSum > 21) {
            // Player loses
            moneyAmount -= 0;
        } else if (dealerSum > 21) {
            // Player wins
            moneyAmount += betAmount * 2; // You get back your original bet and win an additional betAmount
        } else if (playerSum == dealerSum) {
            // It's a tie, player gets back the bet amount
            moneyAmount += betAmount;
        } else if (playerSum > dealerSum) {
            // Player wins
            moneyAmount += betAmount * 2; // You get back your original bet and win an additional betAmount
        } else {
            // Player loses
            moneyAmount -= 0;
        }

        // Reset bet amount to 0 for the next round
        betAmount = 0;

        // Update the total money label
        moneyLabel.setText(" $" + moneyAmount);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BlackJack(0.0));
    }
}
