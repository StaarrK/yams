package fr.uge.yams;

import java.util.Set;
import java.util.stream.Collectors;

public record LargeStraight() implements Combination {

    @Override
    public boolean isValid(Board board) {
        var values = board.getFiveDice().stream()
                          .map(Dice::value)
                          .collect(Collectors.toSet());
        return values.containsAll(Set.of(1, 2, 3, 4, 5)) ||
               values.containsAll(Set.of(2, 3, 4, 5, 6));
    }

    @Override
    public int score(Board board) {
        return isValid(board) ? 40 : 0;
    }
}
