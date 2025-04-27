package fr.uge.yams;

import java.util.Scanner;

public class Yams {

    public static String initPlayer(Scanner scanner, int playerNumber) {
        System.out.println("Welcome, player " + playerNumber + ", please enter your name:");
        return scanner.nextLine();
    }

    private static int askReroll(Scanner scanner) {
        while (true) {
            System.out.println("Do you want to reroll a die? Type 0 for no, 1-5 to reroll this die.");
            var choice = scanner.nextLine();
            try {
                int value = Integer.parseInt(choice);
                if (value >= 0 && value <= 5) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // continue asking
            }
            System.out.println("Invalid input. Please enter a number between 0 and 5.");
        }
    }

    private static String askCombination(Scanner scanner, ScoreSheet scoreSheet) {
        while (true) {
            System.out.println("Please choose a combination to score (T/F/Y/S/L/C/P/D):");
            var choice = scanner.nextLine().toUpperCase();
            try {
                var combination = parseCombination(choice);
                if (!scoreSheet.alreadyUsed(combination)) {
                    return choice;
                } else {
                    System.out.println("This combination has already been used. Pick another one!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private static Combination parseCombination(String combinationName) {
        return switch (combinationName) {
            case "T" -> new ThreeOfAKind();
            case "F" -> new FullHouse();
            case "Y" -> new YamsCombination();
            case "S" -> new SmallStraight();
            case "L" -> new LargeStraight();
            case "C" -> new Chance();
            case "P" -> new Pair();
            case "D" -> new DoublePair();
            default -> throw new IllegalArgumentException("Unexpected value: " + combinationName);
        };
    }

    private static void playTurn(Scanner scanner, String playerName, ScoreSheet scoreSheet) {
        System.out.println("\n--- " + playerName + "'s Turn ---");
        var board = new Board();
        System.out.println(board);

        for (var updateCounter = 0; updateCounter < 2; updateCounter++) { // Max 2 rerolls
            var choice = askReroll(scanner);
            if (choice > 0) {
                board.reroll(choice);
                System.out.println(board);
            } else {
                break;
            }
        }

        var combinationChoice = parseCombination(askCombination(scanner, scoreSheet));
        scoreSheet.updateScore(combinationChoice, board);
        System.out.println("\n" + playerName + "'s Current Score Sheet:");
        System.out.println(scoreSheet);
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        // Initialize players
        var player1Name = initPlayer(scanner, 1);
        var player2Name = initPlayer(scanner, 2);

        var player1Sheet = new ScoreSheet();
        var player2Sheet = new ScoreSheet();
        final int TOTAL_ROUNDS = 13;

        // Game loop
        for (var roundCounter = 0; roundCounter < TOTAL_ROUNDS; roundCounter++) {
            System.out.println("\n=== ROUND " + (roundCounter + 1) + " ===");

            playTurn(scanner, player1Name, player1Sheet);
            YamsBot.playBotTurn(player2Name, player2Sheet);
            }

        // Game over, calculate scores
        int scorePlayer1 = player1Sheet.scoreTotal();
        int scorePlayer2 = player2Sheet.scoreTotal();

        System.out.println("\n=== GAME OVER ===");
        System.out.println(player1Name + "'s Final Score: " + scorePlayer1 + " points");
        System.out.println(player2Name + "'s Final Score: " + scorePlayer2 + " points");

        // Display the winner
        if (scorePlayer1 > scorePlayer2) {
            System.out.println("\n🏆 " + player1Name + " wins! Congratulations!");
        } else if (scorePlayer2 > scorePlayer1) {
            System.out.println("\n🏆 " + player2Name + " wins! Congratulations!");
        } else {
            System.out.println("\n🤝 It's a tie!");
        }

        scanner.close();
    }
}
