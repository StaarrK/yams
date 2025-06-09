package fr.uge.yams2.model;

public interface Combination {
	
	default int score(Board board) {
		return 0;
	}
	default boolean isValid(Board board) {
		return true;
	}

}
