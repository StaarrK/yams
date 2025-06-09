package fr.uge.yams2.model;

import java.util.Scanner;

public class SwapSheetJoker implements GameEvent {
  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Swap Sheet Joker activated! Enter the category name you want to swap:");
    String categoryToSwap = scanner.nextLine().trim();
    System.out.println("Enter the opponent's category to swap with:");
    String opponentCategory = scanner.nextLine().trim();
    // Invoke a method to swap scores between players.
    boolean swapped = sheet.swapCategory(categoryToSwap, opponentCategory);
    if(swapped) {
      System.out.println("Successfully swapped category scores.");
    } else {
      System.out.println("Swap failed: categories may not exist or cannot be swapped.");
    }
  }

  @Override
  public String description() {
    return "Swap Sheet: Swap a scoring category with another player.";
  }
}
