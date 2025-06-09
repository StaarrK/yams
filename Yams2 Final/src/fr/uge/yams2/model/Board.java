package fr.uge.yams2.model;

import java.util.ArrayList;

public class Board {

  private final ArrayList<Dice> fiveDice = new ArrayList<>();

  public Board() {
    for (var i = 1; i <= 5; i++) {
      fiveDice.add(new Dice());
    }
  }

  public ArrayList<Dice> getFiveDice() {
    return fiveDice;
  }
  
  public int sumBoard() {
    int sum = 0;
    for (var i = 1; i <= 5; i++) {
      sum += fiveDice.get(i - 1).value();
    }
    return sum;
  }
  
  @Override
  public String toString() {
    var builder = new StringBuilder();
    builder.append("[");
    for (int i = 0; i < fiveDice.size(); i++) {
      builder.append(fiveDice.get(i).value());
      if (i < fiveDice.size() - 1) {
        builder.append(" | ");
      }
    }
    builder.append("]");
    return builder.toString();
  }

  public void reroll(int pos) {
    if (pos < 1 || pos > 5) {
      throw new IllegalArgumentException();
    }
    fiveDice.set(pos - 1, new Dice());
  }

  public static void main(String[] args) {
    var board = new Board();
    System.out.println(board);
    board.reroll(2);
    System.out.println(board);
  }
}
