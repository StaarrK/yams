package fr.uge.yams.controller;

import fr.uge.yams.model.Board;
import fr.uge.yams.model.Chance;
import fr.uge.yams.model.Combination;
import fr.uge.yams.model.DoublePair;
import fr.uge.yams.model.FullHouse;
import fr.uge.yams.model.LargeStraight;
import fr.uge.yams.model.Pair;
import fr.uge.yams.model.ScoreSheet;
import fr.uge.yams.model.SmallStraight;
import fr.uge.yams.model.ThreeOfAKind;
import fr.uge.yams.model.YamsCombination;
import fr.uge.yams.vue.CLIView;

public class YamsController {

    private final CLIView view = new CLIView();
    private final ScoreSheet player1Sheet = new ScoreSheet();
    private final ScoreSheet player2Sheet = new ScoreSheet();
    private String player1Name;
    private String player2Name;
    private final int TOTAL_ROUNDS = 13;

    public void startGame() {
        player1Name = view.promptPlayerName(1);
        player2Name = view.promptPlayerName(2);

        for (var round = 1; round <= TOTAL_ROUNDS; round++) {
            view.displayMessage("\n=== ROUND " + round + " ===\n");
            playTurn(player1Name, player1Sheet);
            playTurn(player2Name, player2Sheet);
        }
        displayFinalScores();
    }

    private void playTurn(String playerName, ScoreSheet sheet) {
        view.displayMessage("\n--- " + playerName + "'s Turn ---");
        Board board = new Board();
        view.displayBoard(board);

        // Allow up to 2 rerolls
        for (int i = 0; i < 2; i++) {
            int choice = view.askReroll();
            if (choice > 0) {
                board.reroll(choice);
                view.displayBoard(board);
            } else {
                break;
            }
        }

        String combChoice = view.askCombination(sheet);
        Combination combination = parseCombination(combChoice);
        sheet.updateScore(combination, board);
        view.displayMessage("\n" + playerName + "\'s Score Sheet:");
        view.displayMessage(sheet.toString());
    }

    private Combination parseCombination(String choice) {
        return switch (choice) {
            case "T" -> new ThreeOfAKind();
            case "F" -> new FullHouse();
            case "Y" -> new YamsCombination();
            case "S" -> new SmallStraight();
            case "L" -> new LargeStraight();
            case "C" -> new Chance();
            case "P" -> new Pair();
            case "D" -> new DoublePair();
            default -> throw new IllegalArgumentException("Unexpected value: " + choice);
        };
    }

    private void displayFinalScores() {
        int score1 = player1Sheet.scoreTotal();
        int score2 = player2Sheet.scoreTotal();
        view.displayMessage("\n=== GAME OVER ===");
        view.displayMessage(player1Name + "\'s Final Score: " + score1 + " points");
        view.displayMessage(player2Name + "\'s Final Score: " + score2 + " points");

        if (score1 > score2) {
            view.displayMessage("\n🏆 " + player1Name + " wins! Congratulations!");
        } else if (score2 > score1) {
            view.displayMessage("\n🏆 " + player2Name + " wins! Congratulations!");
        } else {
            view.displayMessage("\n🤝 It's a tie!");
        }
    }

    public static void main(String[] args) {
        new YamsController().startGame();
    }
}
