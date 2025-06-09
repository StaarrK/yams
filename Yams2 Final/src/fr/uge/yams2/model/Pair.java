package fr.uge.yams2.model;

import java.util.HashMap;

public record Pair() implements Combination {

    @Override
    public boolean isValid(Board board) {
        var counts = new HashMap<Integer, Integer>();
        for (var dice : board.getFiveDice()) {
            counts.merge(dice.value(), 1, Integer::sum);
        }
        return counts.values().stream().anyMatch(count -> count >= 2);
    }

    @Override
    public int score(Board board) {
        var counts = new HashMap<Integer, Integer>();
        for (var dice : board.getFiveDice()) {
            counts.merge(dice.value(), 1, Integer::sum);
        }
        return counts.entrySet().stream()
                     .filter(e -> e.getValue() >= 2)
                     .mapToInt(e -> e.getKey() * 2)
                     .max()
                     .orElse(0);
    }
}
