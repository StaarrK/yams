package fr.uge.yams.model;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public record DoublePair() implements Combination {

    @Override
    public boolean isValid(Board board) {
        var counts = new HashMap<Integer, Integer>();
        for (var dice : board.getFiveDice()) {
            counts.merge(dice.value(), 1, Integer::sum);
        }
        long pairCount = counts.values().stream().filter(count -> count >= 2).count();
        return pairCount >= 2;
    }

    @Override
    public int score(Board board) {
        var counts = new HashMap<Integer, Integer>();
        for (var dice : board.getFiveDice()) {
            counts.merge(dice.value(), 1, Integer::sum);
        }
        List<Integer> pairs = counts.entrySet().stream()
                                   .filter(e -> e.getValue() >= 2)
                                   .map(e -> e.getKey() * 2)
                                   .sorted((a, b) -> Integer.compare(b, a)) // Sort in descending order
                                   .collect(Collectors.toList());
        if (pairs.size() < 2) {
            return 0;
        }
        return pairs.get(0) + pairs.get(1);
    }
}