package fr.uge.yams2.model;

public class UndoJoker implements GameEvent {
  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    System.out.println("Undo Joker activated! Your last scoring decision will be undone.");
    // Remove last score; assumes ScoreSheet maintains lastEntry state.
    String removed = sheet.undoLastScore();
    if(removed != null) {
      System.out.println("Removed score for combination: " + removed);
    } else {
      System.out.println("No scoring decision to undo.");
    }
  }

  @Override
  public String description() {
    return "Undo Joker: Undo your last scoring decision — only once per game.";
  }
}
