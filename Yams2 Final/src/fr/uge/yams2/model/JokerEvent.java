package fr.uge.yams2.model;

import java.util.Scanner;

public class JokerEvent implements GameEvent {

  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    System.out.println("Joker event! You get a bonus reroll outcome. Choose a dice value (1–6) to keep.");
    Scanner scanner = new Scanner(System.in);
    int target = scanner.nextInt();
    while (target < 1 || target > 6) {
      System.out.print("Invalid value. Enter a dice value (1–6): ");
      target = scanner.nextInt();
    }
    for (int i = 1; i <= board.getFiveDice().size(); i++) {
      if (board.getFiveDice().get(i - 1).value() != target) {
        board.reroll(i);
      }
    }
    System.out.println("After Joker bonus reroll:");
    System.out.println(board);
  }

  @Override
  public String description() {
    return "Bonus rerolls: choose a dice value";
  }
}
