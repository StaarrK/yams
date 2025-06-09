package fr.uge.yams;

import java.util.HashMap;

public record YamsCombination() implements Combination {

    @Override
    public boolean isValid(Board board) {
        int firstValue = board.getFiveDice().get(0).value();
        return board.getFiveDice().stream().allMatch(d -> d.value() == firstValue);
    }

    @Override
    public int score(Board board) {
        return isValid(board) ? 50 : 0;
    }
}