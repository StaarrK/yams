package fr.uge.yams.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamsBot {
  
    public static void playBotTurn(String botName, ScoreSheet sheet) {
        System.out.println("\n--- " + botName + "'s Turn (Bot) ---");
        Board board = new Board();
        System.out.println(board);

        // Use two reroll rounds
        for (int round = 0; round < 2; round++) {
            int target = getMostFrequentValue(board);
            List<Integer> positionsToReroll = new ArrayList<>();
            var dice = board.getFiveDice();
            for (int i = 0; i < dice.size(); i++) {
                if (dice.get(i).value() != target) {
                    positionsToReroll.add(i + 1);
                }
            }
            // If no rerolls needed, break early
            if (positionsToReroll.isEmpty()) {
                break;
            }
            for (Integer pos : positionsToReroll) {
                board.reroll(pos);
            }
            System.out.println("After reroll round " + (round + 1) + ":");
            System.out.println(board);
        }
        
        // Decide combination to choose based on heuristic.
        Combination chosen;
        int countTarget = countOccurrences(board, getMostFrequentValue(board));
        if (countTarget == 5) {
            chosen = new YamsCombination();
            System.out.println("Bot chooses YamsCombination.");
        } else if (new FullHouse().isValid(board)) {
            chosen = new FullHouse();
            System.out.println("Bot chooses FullHouse.");
        } else if (countTarget >= 3) {
            chosen = new ThreeOfAKind();
            System.out.println("Bot chooses ThreeOfAKind.");
        } else if (new SmallStraight().isValid(board)) {
            chosen = new SmallStraight();
            System.out.println("Bot chooses SmallStraight.");
        } else {
            chosen = new Chance();
            System.out.println("Bot chooses Chance.");
        }
        
        // Update score and show bot's score sheet.
        try {
            sheet.updateScore(chosen, board);
        } catch (IllegalArgumentException e) {
            System.out.println("Bot's choice was already used. Falling back on Chance.");
            sheet.updateScore(new Chance(), board);
        }
        System.out.println("\n" + botName + "'s Score Sheet:");
        System.out.println(sheet);
    }
    
    // Returns the dice value that appears most frequently in the board.
    private static int getMostFrequentValue(Board board) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (var dice : board.getFiveDice()) {
            counts.merge(dice.value(), 1, Integer::sum);
        }
        int target = 1;
        int maxCount = 0;
        for (var entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                target = entry.getKey();
            }
        }
        return target;
    }
    
    private static int countOccurrences(Board board, int value) {
        int count = 0;
        for (var dice : board.getFiveDice()) {
            if (dice.value() == value) {
                count++;
            }
        }
        return count;
    }
}
