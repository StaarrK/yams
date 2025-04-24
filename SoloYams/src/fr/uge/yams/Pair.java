package fr.uge.yams;

import java.util.ArrayList;

public record Pair() implements Combination {

	@Override
	public int score(Board board) {
		return 0;
	}

	@Override
	public String toString() {
		return "Pair";
	}
	@Override
	public boolean isValid(Board board) {
		ArrayList<Dice> dices = board.getFiveDice();
		
		for (int i = 0; i < dices.size(); i++) {
			for (int j = i + 1; j < dices.size(); j++) {
				if (dices.get(i) == dices.get(j)) {
					return true;
				}
			}
		}
		return false;
		
	}

}
