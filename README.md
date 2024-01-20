# Java Blackjack Game

## Introduction

This Java project is a simple implementation of the popular card game Blackjack. It includes classes and methods to manage the game mechanics, player interactions, and a graphical user interface using Swing.

## Project Structure

### `BlackJack` Class

The `BlackJack` class serves as the heart of the Blackjack game, orchestrating the core game logic and user interactions. Key features include:

- **Deck Management:** The class includes methods for initializing and shuffling the deck, essential for fair gameplay.
  
- **Card Dealing:** It manages the distribution of cards to both the player and the dealer, adhering to Blackjack rules.

- **Player Actions:** The class handles player actions such as hitting, staying, and doubling down, influencing the flow of the game.

- **Winner Determination:** It includes logic for determining the winner based on the final hands of the player and dealer.

- **Graphical User Interface (GUI):** Utilizing the Swing library, the class creates an interactive GUI that displays cards, game status, and allows user actions.

- **Betting System:** The game incorporates a betting system, enhancing the overall experience and allowing players to manage their in-game funds.

- **Integration with Other Classes:** It seamlessly integrates with the `ChoosingWTD` class, initializing the game with the current money amount when the "Play" button is clicked.

### `Card` Class

The `Card` class represents a standard playing card, providing a structured representation for game-related operations. It encapsulates properties such as value, type (suit), and methods to retrieve the card's image path, contributing to the visual representation of cards in the GUI.

### `Login` Class

The `Login` class introduces a basic login system implemented with Swing. Users are prompted to provide their email and password, and upon successful login, the system transitions to the Blackjack game. This class enhances the project by incorporating user authentication, ensuring a personalized and secure experience for players.

### `ChoosingWTD` Class

The `ChoosingWTD` class functions as the decision-making interface for users, offering distinct actions such as "Play" and "Add Money." It utilizes Swing components to create an intuitive user interface, providing visibility into the player's financial state through a money label. This class works in tandem with the `BlackJack` and `AddMoney` classes, offering a smooth transition between gameplay and managing in-game finances.

### `AddMoney` Class

The `AddMoney` class extends the functionality of the game by enabling users to add money to their account. It includes secure text fields for entering card number and CVV, performs validation checks, and updates the displayed money amount upon successful transactions. Integrated with the `ChoosingWTD` class, it enhances the overall user experience by allowing seamless management of in-game funds.

## Usage

### Blackjack

To run the Blackjack game, instantiate the `BlackJack` class with the initial money amount:

```java
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new BlackJack(0.0));
}
