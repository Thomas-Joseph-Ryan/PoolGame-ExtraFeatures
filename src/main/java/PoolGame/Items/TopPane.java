package PoolGame.Items;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class TopPane {

    /**
     * Creates the header for the frame. Places the timer and score centered in the scene.
     * @param timer The timer object the game is using
     * @param scoreKeeper The scoreKeeper object the game is using
     * @return A HBox coloured light blue with the two labels in place
     */
    public static HBox addHBox(Timer timer, ScoreKeeper scoreKeeper) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #709DF6;");

        hbox.getChildren().addAll(timer.getTimeLabel(), scoreKeeper.getScoreLabel());
        hbox.setAlignment(Pos.CENTER);

        return hbox;
    }
}
