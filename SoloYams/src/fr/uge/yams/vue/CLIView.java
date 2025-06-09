// Language: Java
package fr.uge.yams.vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import fr.uge.yams.model.Board;
import fr.uge.yams.model.ScoreSheet;

public class CLIView {
    private final Scanner scanner = new Scanner(System.in);

    public int askGameMode() {
        while (true) {
            System.out.println("Select game mode: 1 \\- Solo, 2 \\- Multiplayer");
            String input = scanner.nextLine();
            try {
                int mode = Integer.parseInt(input);
                if (mode == 1 || mode == 2) {
                    return mode;
                }
            } catch (NumberFormatException e) {
                // continue asking
            }
            System.out.println("Invalid input. Please enter 1 or 2.");
        }
    }

    public String promptPlayerName(int playerNumber) {
        System.out.println("Welcome, player " + playerNumber + ". Please enter your name:");
        return scanner.nextLine();
    }

    public void displayBoard(Board board) {
        System.out.println(board);
    }

    public List<Integer> askRerollDice() {
        System.out.println("Enter the dice numbers (1\\-5) separated by spaces to reroll (or press enter to skip):");
        String input = scanner.nextLine().trim();
        List<Integer> diceToReroll = new ArrayList<>();
        if (input.isEmpty()) {
            return diceToReroll;
        }
        String[] tokens = input.split("\\s+");
        for (String token : tokens) {
            try {
                int value = Integer.parseInt(token);
                if (value >= 1 && value <= 5) {
                    diceToReroll.add(value);
                } else {
                    System.out.println("Dice number must be between 1 and 5. Ignoring: " + token);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input '" + token + "'. Ignoring.");
            }
        }
        return diceToReroll;
    }

    public String askCombination(ScoreSheet sheet) {
        System.out.println("Choose a combination to score (T/F/Y/S/L/C/P/D):");
        String choice = scanner.nextLine().toUpperCase();
        return choice;
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
