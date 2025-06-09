package fr.uge.yams2.model;

import java.util.Scanner;

public class DoubleDownJoker implements GameEvent {
  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Double Down Joker activated! Score a category twice; the second must beat the first.");
    System.out.println("Enter your new score (must be higher than your last score for this category):");
    int newScore = scanner.nextInt();
    int lastScore = sheet.getLastCategoryScore();  // Requires implementation in ScoreSheet.
    if(newScore > lastScore) {
      int bonus = newScore;
      System.out.println("Second score accepted. Bonus of " + bonus + " points added.");
      sheet.addBonus(bonus);
    } else {
      System.out.println("Second score did not beat the first. No bonus added.");
    }
  }

  @Override
  public String description() {
    return "Double Down: Score a category twice — the second one must beat the first.";
  }
}
