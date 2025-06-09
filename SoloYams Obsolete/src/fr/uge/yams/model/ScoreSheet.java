package fr.uge.yams.model;

import java.util.HashMap;
import java.util.Objects;

public class ScoreSheet {

	private final HashMap<Combination, Integer> scoreMap = new HashMap<>();

	public void updateScore(Combination pattern, Board board) {
		Objects.requireNonNull(pattern);
		if (scoreMap.containsKey(pattern)) {
			throw new IllegalArgumentException("already a score for this combination");
		}
		scoreMap.put(pattern, pattern.score(board));
	}

	public int scoreTotal() {

		return scoreMap.values().stream().mapToInt(Integer::intValue).sum();
	}
	
	public boolean alreadyUsed(Combination combination) {
	    return scoreMap.containsKey(combination.getClass().getSimpleName());
	}


	@Override
	public String toString() {
		return scoreMap.toString();
	}

}
