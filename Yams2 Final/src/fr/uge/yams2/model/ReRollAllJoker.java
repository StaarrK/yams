package fr.uge.yams2.model;

public class ReRollAllJoker implements GameEvent {
  @Override
  public void apply(String playerName, Board board, ScoreSheet sheet) {
    System.out.println("Re-Roll All Joker activated! Re-rolling all dice.");
    for (int i = 1; i <= board.getFiveDice().size(); i++) {
      board.reroll(i);
    }
    System.out.println("Board after re-roll all:");
    System.out.println(board);
  }

  @Override
  public String description() {
    return "Re-Roll All: Re-roll all five dice once, even after your third roll.";
  }
}
