package fr.uge.yams2.model;

import java.util.Scanner;

public class SwapDieJoker implements GameEvent {
  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Swap Dice Joker activated! Choose a die (1-5) to swap:");
    int pos = scanner.nextInt();
    while (pos < 1 || pos > 5) {
      System.out.print("Invalid position. Enter a die number (1-5): ");
      pos = scanner.nextInt();
    }
    System.out.println("Enter the desired face value (1-6):");
    int face = scanner.nextInt();
    while (face < 1 || face > 6) {
      System.out.print("Invalid face. Enter a dice value (1-6): ");
      face = scanner.nextInt();
    }
    // Directly create a new Dice with the chosen face.
    board.getFiveDice().set(pos - 1, new Dice(face));
    System.out.println("Board after swap:");
    System.out.println(board);
  }

  @Override
  public String description() {
    return "Swap Dice: Swap one dice with any face you want, once per game.";
  }
}
