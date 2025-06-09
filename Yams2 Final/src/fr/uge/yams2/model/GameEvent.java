package fr.uge.yams2.model;

public interface GameEvent {
  void apply(String playerName, Board board, ScoreSheet sheet);
  String description();
}
