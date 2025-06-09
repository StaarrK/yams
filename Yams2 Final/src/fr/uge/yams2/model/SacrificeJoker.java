package fr.uge.yams2.model;

public class SacrificeJoker implements GameEvent {
  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    System.out.println("Sacrifice Joker activated! You sacrifice this turn for a flat +25 bonus points.");
    sheet.addBonus(25);
    System.out.println("25 bonus points added.");
  }

  @Override
  public String description() {
    return "Sacrifice Joker: Sacrifice this turn to get +25 flat points.";
  }
}
