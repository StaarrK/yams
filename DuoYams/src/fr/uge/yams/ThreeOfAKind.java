package fr.uge.yams;

import java.util.HashMap;

public record ThreeOfAKind() implements Combination {

    @Override
    public boolean isValid(Board board) {
        var counts = new HashMap<Integer, Integer>();
        for (var dice : board.getFiveDice()) {
            counts.merge(dice.value(), 1, Integer::sum);
        }
        return counts.values().stream().anyMatch(count -> count >= 3);
    }

    @Override
    public int score(Board board) {
        return isValid(board) ? board.sumBoard() : 0;
    }
}
