// File: `fr/uge/yams/view/MainGameController.java`
package fr.uge.yams.vue;

import fr.uge.yams.model.Board;
import javafx.scene.image.Image;
import fr.uge.yams.model.ScoreSheet;
import fr.uge.yams.model.Chance;
import fr.uge.yams.model.Combination;
import fr.uge.yams.model.DoublePair;
import fr.uge.yams.model.FullHouse;
import fr.uge.yams.model.LargeStraight;
import fr.uge.yams.model.Pair;
import fr.uge.yams.model.SmallStraight;
import fr.uge.yams.model.ThreeOfAKind;
import fr.uge.yams.model.YamsCombination;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class MainGameController {
  @FXML private Label nom_joueur;
  @FXML private Label num_tour;
  @FXML private Label nb_relances;
  @FXML private Label joueur1_score;
  @FXML private Label joueur2_score;
  @FXML private ComboBox<String> comboCombinaison;
  @FXML private Button relance;
  @FXML private Button confirm;
  @FXML private Button skip;
  @FXML private ImageView de1, de2, de3, de4, de5;
  
  private Board board;
  private ScoreSheet scoreSheet;
  private int currentRound = 1;
  private final int TOTAL_ROUNDS = 13;
  private int remainingRerolls = 2;
  
  // To track selected dice (indices 0-4)
  private boolean[] isSelected = new boolean[5];
  
  @FXML
  public void initialize() {
    // Initialize game and UI components
    scoreSheet = new ScoreSheet();
    board = new Board();
    updateDiceDisplay();
    updateInfoLabels();
    
    // Initialize ComboBox with combination options
    comboCombinaison.getItems().addAll("Pair", "Double Pair", "Three of a Kind", "Full House",
      "Small Straight", "Large Straight", "Yams", "Chance");
  }
  
  // Toggle selection of a die when clicked.
  @FXML
  private void onClickDe1(MouseEvent event) { toggleDie(0, de1); }
  @FXML
  private void onClickDe2(MouseEvent event) { toggleDie(1, de2); }
  @FXML
  private void onClickDe3(MouseEvent event) { toggleDie(2, de3); }
  @FXML
  private void onClickDe4(MouseEvent event) { toggleDie(3, de4); }
  @FXML
  private void onClickDe5(MouseEvent event) { toggleDie(4, de5); }
  
  private void toggleDie(int index, ImageView dieView) {
    isSelected[index] = !isSelected[index];
    // Visual indication of selection (e.g., add a drop shadow)
    if(isSelected[index]) {
      dieView.setEffect(new DropShadow(20, Color.BLUE));
    } else {
      dieView.setEffect(null);
    }
  }
  
  // Called when the user clicks the "Relancer" button
  @FXML
  private void onRelancerDes() {
    if(remainingRerolls > 0) {
      for (int i = 0; i < isSelected.length; i++) {
        if(isSelected[i]) {
          // Board.reroll expects a die number (1-indexed)
          board.reroll(i + 1);
          isSelected[i] = false;
        }
      }
      remainingRerolls--;
      updateDiceDisplay();
      updateInfoLabels();
    }
  }
  
  // Called when the user confirms a combination
  @FXML
  private void onValiderCombinaison() {
    String choice = comboCombinaison.getValue();
    if(choice == null) return;
    Combination combination = parseCombination(choice);
    try {
      scoreSheet.updateScore(combination, board);
    } catch(IllegalArgumentException e) {
      // Could display an error alert here.
    }
    nextRound();
  }
  
  // Called when the user skips the turn.
  @FXML
  private void onPasserTour() {
    nextRound();
  }
  
  // Advances the game round.
  private void nextRound() {
    if(currentRound < TOTAL_ROUNDS) {
      currentRound++;
      remainingRerolls = 2;
      board = new Board();
      updateDiceDisplay();
      updateInfoLabels();
    } else {
      // End game: update UI to show final scores.
      num_tour.setText("Game Over");
      nb_relances.setText("");
      // Update score labels accordingly.
      joueur1_score.setText("" + scoreSheet.scoreTotal());
      // Here you might add additional end game logic.
      relance.setDisable(true);
      confirm.setDisable(true);
      skip.setDisable(true);
    }
  }
  
  // Update dice ImageViews based on current board values.
  private void updateDiceDisplay() {
	  var dice = board.getFiveDice();
	  setDieImage(de1, dice.get(0).value());
	  setDieImage(de2, dice.get(1).value());
	  setDieImage(de3, dice.get(2).value());
	  setDieImage(de4, dice.get(3).value());
	  setDieImage(de5, dice.get(4).value());
	}

  
  // Update informational labels.
  private void updateInfoLabels() {
    num_tour.setText("" + currentRound);
    nb_relances.setText("" + remainingRerolls);
    // nom_joueur and joueur scores can be updated here as needed.
  }
  
 
	private void setDieImage(ImageView dieView, int value) {
	  dieView.setAccessibleText(String.valueOf(value));
	  String imagePath = value + ".png";
	  dieView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
	}

  
  // Parse the combination string into a game Combination object.
  private Combination parseCombination(String choice) {
    return switch(choice) {
      case "Three of a Kind" -> new ThreeOfAKind();
      case "Full House" -> new FullHouse();
      case "Yams" -> new YamsCombination();
      case "Small Straight" -> new SmallStraight();
      case "Large Straight" -> new LargeStraight();
      case "Chance" -> new Chance();
      case "Pair" -> new Pair();
      case "Double Pair" -> new DoublePair();
      default -> new Chance();
    };
  }
}
