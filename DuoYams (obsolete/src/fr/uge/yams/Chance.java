package fr.uge.yams;

public record Chance() implements Combination {
	
	
	@Override
	public int score(Board board) {
		return board.sumBoard();
	}

	@Override
	public String toString() {
		return "Chance";
	}

	@Override
	public boolean isValid(Board board) {
		return true;
	}
	
}
