package fr.uge.yams.vue;

import java.util.Scanner;
import fr.uge.yams.model.Board;
import fr.uge.yams.model.ScoreSheet;

public class CLIView {
    private final Scanner scanner = new Scanner(System.in);

    public String promptPlayerName(int playerNumber) {
        System.out.println("Welcome, player " + playerNumber + ". Please enter your name:");
        return scanner.nextLine();
    }

    public void displayBoard(Board board) {
        System.out.println(board);
    }

    public int askReroll() {
        while (true) {
            System.out.println("Do you want to reroll a die? Type 0 for no, or 1-5 for the die to reroll:");
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0 && value <= 5) {
                    return value;
                }
            } catch (NumberFormatException ex) {
                // continue
            }
            System.out.println("Invalid input. Enter a number between 0 and 5.");
        }
    }

    public String askCombination(ScoreSheet sheet) {
        while (true) {
            System.out.println("Choose a combination to score (T/F/Y/S/L/C/P/D):");
            String choice = scanner.nextLine().toUpperCase();
            return choice;
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
