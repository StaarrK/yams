package fr.uge.yams;

public record ThreeOfAKind() implements Combination {
	
	
	
	@Override
	public int score(Board board) {

		return 15;
	}

	@Override
	public String toString() {
		return "Three of A Kind";
	}
	
	@Override
	public boolean isValid(Board board) {
		int[] dices = new int[6];
		for (int i = 0; i < board.getFiveDice().size(); i++) {
			dices[board.getFiveDice().get(i).value()- 1]++;
		}
		for (int i = 0; i < dices.length; i++) {
			if (dices[i] >= 3) {
				return true;
			}
		}
		return false;
		
	}

}
