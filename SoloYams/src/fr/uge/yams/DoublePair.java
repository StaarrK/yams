package fr.uge.yams;

import fr.uge.yams.Pair;

public record DoublePair() implements Combination {
	@Override
	public int score(Board board) {
		return 20;
	}

	@Override
	public String toString() {
		return "Double Pair";
	}

	@Override
	public boolean isValid(Board board) {
		if (board.Pair().isValid(board) && board.pair2().isValid(board)) {
			return true;
		}
		return false;
	}
}
