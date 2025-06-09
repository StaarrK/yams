// File: `Yams2/src/fr/uge/yams2/model/ScoreSheet.java`
package fr.uge.yams2.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScoreSheet {
  private final HashMap<String, Integer> scoreMap = new HashMap<>();
  private int bonusPoints = 0;
  
  private boolean ghostScoreEnabled = false;
  private String lastCombination = null;
  private int lastScore = 0;
  private Integer heldDie = null;
  // For demo purposes only; adjust to store opponent score properly.
  private int opponentLastScore = 0;

  public void updateScore(Combination pattern, Board board) {
    Objects.requireNonNull(pattern);
    String key = pattern.getClass().getSimpleName();
    if (alreadyUsed(pattern) && !ghostScoreEnabled) {
      throw new IllegalArgumentException("already a score for this combination");
    }
    int score = pattern.score(board);
    scoreMap.put(key, score);
    // Store last score details for undo and double down operations.
    lastCombination = key;
    lastScore = score;
    // For demo purposes, update opponentLastScore as well.
    opponentLastScore = score;
  }

  public void addBonus(int bonus) {
    bonusPoints += bonus;
  }

  public int scoreTotal() {
    int total = scoreMap.values().stream().mapToInt(Integer::intValue).sum();
    return total + bonusPoints;
  }

  public boolean alreadyUsed(Combination combination) {
    return scoreMap.containsKey(combination.getClass().getSimpleName());
  }

  // Enables use of the same category twice.
  public void enableGhostScore() {
    ghostScoreEnabled = true;
  }

  // Returns the last category score recorded.
  public int getLastCategoryScore() {
    return lastScore;
  }

  // Removes the last scored entry and returns its category key.
  public String undoLastScore() {
    if (lastCombination == null) {
      return null;
    }
    Integer removed = scoreMap.remove(lastCombination);
    String temp = lastCombination;
    lastCombination = null;
    lastScore = 0;
    return temp;
  }

  // Sets the held die value to be used in the next turn.
  public void setHeldDie(int value) {
    heldDie = value;
  }

  // Returns the held die value, if any.
  public Integer getHeldDie() {
    return heldDie;
  }

  // Returns the opponent's last recorded score; adjust as needed for actual game logic.
  public int getOpponentLastScore() {
    return opponentLastScore;
  }

  // Swaps the scores for the two given category keys.
  public boolean swapCategory(String cat1, String cat2) {
    Integer score1 = scoreMap.get(cat1);
    Integer score2 = scoreMap.get(cat2);
    if (score1 == null || score2 == null) {
      return false;
    }
    scoreMap.put(cat1, score2);
    scoreMap.put(cat2, score1);
    return true;
  }

  @Override
  public String toString() {
    return scoreMap.toString() + " | Bonus: " + bonusPoints;
  }
}
