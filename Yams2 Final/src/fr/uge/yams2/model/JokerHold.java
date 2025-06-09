package fr.uge.yams2.model;

import java.util.Scanner;

public class JokerHold implements GameEvent {
  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Joker Hold activated! Choose a die (1-5) to freeze for your next turn:");
    int pos = scanner.nextInt();
    while (pos < 1 || pos > 5) {
      System.out.print("Invalid position. Enter a die number (1-5): ");
      pos = scanner.nextInt();
    }
    // Save the chosen die's value in ScoreSheet (requires additional logic in ScoreSheet)
    int heldValue = board.getFiveDice().get(pos - 1).value();
    sheet.setHeldDie(heldValue);
    System.out.println("Die with value " + heldValue + " held for next turn.");
  }

  @Override
  public String description() {
    return "Joker Hold: Freeze one die between turns.";
  }
}
