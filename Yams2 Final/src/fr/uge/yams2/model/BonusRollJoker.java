package fr.uge.yams2.model;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class BonusRollJoker implements GameEvent {
  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Bonus Roll Joker activated! You get an extra (4th) roll.");
    System.out.println("Enter dice numbers (1-5) to re-roll, separated by spaces, or press ENTER to skip:");
    String input = scanner.nextLine().trim();
    if(input.isEmpty()){
      System.out.println("No dice re-rolled.");
      return;
    }
    String[] tokens = input.split("\\s+");
    List<Integer> rerolls = new ArrayList<>();
    for(String token : tokens) {
      try {
        int val = Integer.parseInt(token);
        if(val >= 1 && val <= 5) {
          rerolls.add(val);
        }
      } catch(NumberFormatException e) {
        // skip invalid token
      }
    }
    for (Integer pos : rerolls) {
      board.reroll(pos);
    }
    System.out.println("Board after bonus roll:");
    System.out.println(board);
  }

  @Override
  public String description() {
    return "Bonus Roll Joker: You get a 4th roll this turn.";
  }
}
