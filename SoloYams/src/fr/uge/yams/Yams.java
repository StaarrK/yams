package fr.uge.yams;

import java.util.Scanner;

public class Yams {

	public static String init(Scanner scanner) {

		System.out.println("Welcome, player, please enter your name.");
		return scanner.nextLine();
	}

	private static int askReroll(Scanner scanner) {
	    while (true) {
	        System.out.println("Do you want to reroll a die? Type 0 for no, 1-5 to reroll this die.");
	        var choice = scanner.nextLine();
	        try {
	            int value = Integer.parseInt(choice);
	            if (value >= 0 && value <= 5) {
	                return value;
	            }
	        } catch (NumberFormatException e) {
	            // continue asking
	        }
	        System.out.println("Invalid input. Please enter a number between 0 and 5.");
	    }
	}
    
	private static String askCombination(Scanner scanner, ScoreSheet scoreSheet) {
        while (true) {
            System.out.println("Please choose a combination to score (T/F/Y/S/L/C/P/D):");
            var choice = scanner.nextLine().toUpperCase();
            try {
                var combination = parseCombination(choice);
                if (!scoreSheet.alreadyUsed(combination)) {
                    return choice;
                } else {
                    System.out.println("This combination has already been used. Pick another one!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please try again.");
                }
       
        }
	}
	
	private static Combination parseCombination(String combinationName) {

		return switch (combinationName) {
		case "T" -> new ThreeOfAKind();
		case "F" -> new FullHouse();
		case "Y" -> new YamsCombination();
		case "S" -> new SmallStraight();
		case "L" -> new LargeStraight();
		case "C" -> new Chance();
		case "P" -> new Pair();
		case "D" -> new DoublePair();
		default -> throw new IllegalArgumentException("Unexpected value: " + combinationName);
		};
	}


    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var name = init(scanner);
        System.out.println("\nHello " + name + ", and good luck!\n");

        var scoreSheet = new ScoreSheet();
        final int TOTAL_ROUNDS = 13;

        for (var roundCounter = 0; roundCounter < TOTAL_ROUNDS; roundCounter++) {
            System.out.println("\n=== Round " + (roundCounter + 1) + " ===");
            var board = new Board();
            System.out.println(board);

            for (var updateCounter = 0; updateCounter < 2; updateCounter++) { 
                var choice = askReroll(scanner);
                if (choice > 0) {
                    board.reroll(choice);
                    System.out.println(board);
                } else {
                    break;
                }
            }

            var combinationChoice = parseCombination(askCombination(scanner, scoreSheet));
            scoreSheet.updateScore(combinationChoice, board);
            System.out.println("\nCurrent Score Sheet:");
            System.out.println(scoreSheet);
        }
        System.out.println("C'est fini !");
	}

}
