package fr.uge.yams;

import java.util.HashMap;

public record FullHouse() implements Combination {

    @Override
    public boolean isValid(Board board) {
        var counts = new HashMap<Integer, Integer>();
        for (var dice : board.getFiveDice()) {
            counts.merge(dice.value(), 1, Integer::sum);
        }
        return counts.containsValue(3) && counts.containsValue(2);
    }

    @Override
    public int score(Board board) {
        return isValid(board) ? 25 : 0;
    }
}
