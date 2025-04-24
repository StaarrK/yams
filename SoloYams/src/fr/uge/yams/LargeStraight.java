package fr.uge.yams;

import java.util.ArrayList;

public record LargeStraight() implements Combination {

	@Override
	public int score(Board board) {
		return 40;
	}

	@Override
	public String toString() {
		return "Large Straight";
	}

	@Override
	public boolean isValid(Board board) {
		ArrayList<Dice> dices = board.getFiveDice();
		if (dices.size() != 5) {
			return false;
		}
		
		if (dices.get(0).value() == 1 && dices.get(1).value() == 2 && dices.get(2).value() == 3 && dices.get(3).value() == 4 && dices.get(4).value() == 5) {
			return true;
		}
		
		if (dices.get(0).value() == 2 && dices.get(1).value() == 3 && dices.get(2).value() == 4 && dices.get(3).value() == 5 && dices.get(4).value() == 6) {
			return true;
		}
		
		return false;
	}

}
