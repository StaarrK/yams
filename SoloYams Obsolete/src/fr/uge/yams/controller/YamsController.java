// Language: Java
package fr.uge.yams.controller;

import java.util.List;
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
import fr.uge.yams.model.YamsBot;
import fr.uge.yams.model.YamsCombination;
import fr.uge.yams.vue.CLIView;

public class YamsController {

    private final CLIView view = new CLIView();
    private ScoreSheet player1Sheet;
    private ScoreSheet player2Sheet;
    private String player1Name;
    private String player2Name;
    private final int TOTAL_ROUNDS = 13;
    private boolean isSolo;
    private boolean isBotMode;

    public void startGame() {
        int gameMode = view.askGameMode();
        isSolo = (gameMode == 1);
        isBotMode = (gameMode == 3);

        player1Sheet = new ScoreSheet();
        if (isSolo || isBotMode) {
            player1Name = view.promptPlayerName(1);
            player2Name = isBotMode ? "Bot" : "";
            if (isBotMode) {
                player2Sheet = new ScoreSheet();
            }
        } else { // Multiplayer with two human players
            player1Name = view.promptPlayerName(1);
            player2Name = view.promptPlayerName(2);
            player2Sheet = new ScoreSheet();
        }

        for (var round = 1; round <= TOTAL_ROUNDS; round++) {
            view.displayMessage("\n=== ROUND " + round + " ===\n");
            playTurn(player1Name, player1Sheet);
            if (!isSolo) {
                if (isBotMode) {
                    YamsBot.playBotTurn(player2Name, player2Sheet);
                } else {
                    playTurn(player2Name, player2Sheet);
                }
            }
        }
        displayFinalScores();
    }

    private void playTurn(String playerName, ScoreSheet sheet) {
        view.displayMessage("\n--- " + playerName + "'s Turn ---");
        Board board = new Board();
        view.displayBoard(board);

        // Allow up to 2 reroll rounds.
        for (int i = 0; i < 2; i++) {
            List<Integer> rerolls = view.askRerollDice();
            if (rerolls.isEmpty()) {
                break;
            }
            for (Integer pos : rerolls) {
                board.reroll(pos);
            }
            view.displayBoard(board);
        }

        // Loop until a valid combination is chosen.
        while (true) {
            String combChoice = view.askCombination(sheet);
            Combination combination = parseCombination(combChoice);
            try {
                sheet.updateScore(combination, board);
                break;
            } catch (IllegalArgumentException e) {
                view.displayMessage("This combination has already been used. Please choose another one.");
            }
        }
        view.displayMessage("\n" + playerName + "'s Score Sheet:");
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
        if (isSolo) {
            int score1 = player1Sheet.scoreTotal();
            view.displayMessage("\n=== GAME OVER ===");
            view.displayMessage(player1Name + "'s Final Score: " + score1 + " points");
        } else if (isBotMode) {
            int s1 = player1Sheet.scoreTotal();
            int s2 = player2Sheet.scoreTotal();
            view.displayMessage("\n=== GAME OVER ===");
            view.displayMessage(player1Name + "'s Final Score: " + s1 + " points");
            view.displayMessage(player2Name + "'s Final Score: " + s2 + " points");
            if (s1 > s2) {
                view.displayMessage("\n🏆 " + player1Name + " wins! Congratulations!");
            } else if (s2 > s1) {
                view.displayMessage("\n🏆 " + player2Name + " wins! Congratulations!");
            } else {
                view.displayMessage("\n🤝 It's a tie!");
            }
        } else {
            int s1 = player1Sheet.scoreTotal();
            int s2 = player2Sheet.scoreTotal();
            view.displayMessage("\n=== GAME OVER ===");
            view.displayMessage(player1Name + "'s Final Score: " + s1 + " points");
            view.displayMessage(player2Name + "'s Final Score: " + s2 + " points");
            if (s1 > s2) {
                view.displayMessage("\n🏆 " + player1Name + " wins! Congratulations!");
            } else if (s2 > s1) {
                view.displayMessage("\n🏆 " + player2Name + " wins! Congratulations!");
            } else {
                view.displayMessage("\n🤝 It's a tie!");
            }
        }
    }

    public static void main(String[] args) {
        new YamsController().startGame();
    }
}
