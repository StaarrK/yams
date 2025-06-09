package fr.uge.yams2.model;

public class DoublePointsEvent implements GameEvent {

  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    System.out.println("Double points event! Your score this round will be doubled.");
    // The event will be applied as bonus after the combination is chosen.
  }

  @Override
  public String description() {
    return "Double points for this round";
  }
}
