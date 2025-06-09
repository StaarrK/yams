module prog_SAE_JavaFX {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	opens fr.uge.yams.vue to javafx.graphics, javafx.fxml;
	opens fr.uge.yams.controller to javafx.fxml;
	opens fr.uge.yams.model to javafx.fxml;
}
