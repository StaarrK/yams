package fr.uge.yams2.controller;

import java.util.List;
import java.util.Random;
import fr.uge.yams2.model.Board;
import fr.uge.yams2.model.Chance;
import fr.uge.yams2.model.Combination;
import fr.uge.yams2.model.DoublePointsEvent;
import fr.uge.yams2.model.JokerEvent;
import fr.uge.yams2.model.ScoreSheet;
import fr.uge.yams2.model.ReRollAllJoker;
import fr.uge.yams2.model.SwapDieJoker;
import fr.uge.yams2.model.GhostScoreJoker;
import fr.uge.yams2.model.DoubleDownJoker;
import fr.uge.yams2.model.JokerHold;
import fr.uge.yams2.model.UndoJoker;
import fr.uge.yams2.model.BonusRollJoker;
import fr.uge.yams2.model.MirrorJoker;
import fr.uge.yams2.model.SacrificeJoker;
import fr.uge.yams2.model.SwapSheetJoker;
import fr.uge.yams2.model.ThreeOfAKind;
import fr.uge.yams2.model.FullHouse;
import fr.uge.yams2.model.GameEvent;
import fr.uge.yams2.model.LargeStraight;
import fr.uge.yams2.model.Pair;
import fr.uge.yams2.model.SmallStraight;
import fr.uge.yams2.model.YamsCombination;
import fr.uge.yams2.vue.CLIView;

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
        } else {
            player1Name = view.promptPlayerName(1);
            player2Name = view.promptPlayerName(2);
            player2Sheet = new ScoreSheet();
        }

        for (var round = 1; round <= TOTAL_ROUNDS; round++) {
            view.displayMessage("\n=== ROUND " + round + " ===\n");
            playTurn(player1Name, player1Sheet);
            if (!isSolo) {
                // Bot or second player turn...
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

        // Determine random game event.
        Random random = new Random();
        int eventChance = random.nextInt(100);
        var event = (eventChance < 20) ? new JokerEvent() :
                    (eventChance < 40) ? new DoublePointsEvent() : null;
        if (event != null) {
            System.out.println("Event triggered: " + event.description());
            if (event instanceof JokerEvent) {
                event.apply(playerName, board, sheet);
            }
        }

        // Consumable Joker integration.
        String jokerChoice = view.askConsumableJoker();
        if (!jokerChoice.isEmpty()) {
            GameEvent consumable = switch (jokerChoice) {
              case "R" -> new ReRollAllJoker();
              case "S" -> new SwapDieJoker();
              case "G" -> new GhostScoreJoker();
              case "D" -> new DoubleDownJoker();
              case "H" -> new JokerHold();
              case "U" -> new UndoJoker();
              case "B" -> new BonusRollJoker();
              case "M" -> new MirrorJoker();
              case "X" -> new SacrificeJoker();
              case "W" -> new SwapSheetJoker();
              default -> null;
            };
            if (consumable != null) {
              System.out.println("Consumable Joker triggered: " + consumable.description());
              consumable.apply(playerName, board, sheet);
            }
        }

        // Loop until a valid combination is chosen.
        Combination chosen = null;
        while (true) {
            String combChoice = view.askCombination(sheet);
            Combination chosen1;
            try {
                chosen1 = parseCombination(combChoice);
            } catch (IllegalArgumentException e) {
                view.displayMessage("Invalid combination. Please try again.");
                continue;
            }
            try {
                sheet.updateScore(chosen1, board);
                break;
            } catch (IllegalArgumentException e) {
                view.displayMessage("This combination has already been used. Please choose another one.");
            }
        }

        // Apply DoublePoints event bonus if needed.
        if (event instanceof DoublePointsEvent) {
            int bonus = chosen.score(board);
            sheet.addBonus(bonus);
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
            case "D" -> new fr.uge.yams2.model.DoublePair();
            default -> throw new IllegalArgumentException("Unexpected value: " + choice);
        };
    }

    private void displayFinalScores() {
        int player1Total = player1Sheet.scoreTotal();
        view.displayMessage("\n=== Final Scores ===\n");
        if (isSolo) {
            view.displayMessage(player1Name + "\'s Score: " + player1Total);
        } else {
            int player2Total = player2Sheet.scoreTotal();
            view.displayMessage(player1Name + "\'s Score: " + player1Total);
            view.displayMessage(player2Name + "\'s Score: " + player2Total);
            if (player1Total > player2Total) {
                view.displayMessage("Winner: " + player1Name);
            } else if (player2Total > player1Total) {
                view.displayMessage("Winner: " + player2Name);
            } else {
                view.displayMessage("It\'s a tie!");
            }
        }
    }

    public static void main(String[] args) {
        new YamsController().startGame();
    }
}
