package fr.uge.yams2.model;

public class GhostScoreJoker implements GameEvent {
  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    System.out.println("Ghost Score Joker activated! You may use the same category twice this game.");
    // Set a flag in the ScoreSheet (you would need to add a field/method to handle ghost score)
    sheet.enableGhostScore();
  }

  @Override
  public String description() {
    return "Ghost Score: Use the same category twice in a game.";
  }
}
