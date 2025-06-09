package fr.uge.yams2.model;

public class MirrorJoker implements GameEvent {
  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    System.out.println("Mirror Joker activated! Copying opponent’s last score.");
    int opponentLastScore = sheet.getOpponentLastScore(); // Requires implementation.
    sheet.addBonus(opponentLastScore);
    System.out.println("Copied " + opponentLastScore + " points as bonus.");
  }

  @Override
  public String description() {
    return "Mirror Joker: Copy an opponent’s last score into your own sheet.";
  }
}
