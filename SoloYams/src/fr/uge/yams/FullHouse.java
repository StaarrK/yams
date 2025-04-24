package fr.uge.yams;
import java.util.ArrayList;

import fr.uge.yams.Pair;

public record FullHouse() implements Combination {

	@Override
	public int score(Board board) {
		return 25;
	}

	@Override
	public String toString() {
		return "Full House";
	}
	
	private boolean isAPair(Board board, Dice dice) {
		ArrayList<Dice> dices = board.getFiveDice();
		
		for (int i = 0; i < dices.size(); i++) {
			for (int j = i + 1; j < dices.size(); j++) {
				if (dices.get(i).value() == dices.get(j).value()) {
					return true;
				}
			}
		return false;
		
	

	}
	@Override
	public boolean isValid(Board board) {
		if ( && board.trio().isValid(board)) {
			return true;
		}
	}
}
