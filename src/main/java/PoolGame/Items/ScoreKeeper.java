package PoolGame.Items;

import PoolGame.Drawable;
import PoolGame.Memento.Memento;
import PoolGame.Memento.Savable;
import PoolGame.Observer.ScoreboardObserver;
import PoolGame.Prototype.Cloneable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class ScoreKeeper implements Drawable, Savable {

    private final List<ScoreboardObserver> scoreObservers;
    private final Label scoreLabel;
    private int score;

    private ScoreKeeperMemento scoreKeeperMemento = null;

    /**
     * The constructor for the ScoreKeeper label. Creates ScoreBoardObservers and attaches them to the balls
     * @param balls List of balls in the game
     */
    public ScoreKeeper(List<Ball> balls) {
        this.scoreObservers = new ArrayList<>();
        for (Ball ball : balls) {
            ScoreboardObserver observer = new ScoreboardObserver(this, ball);
            ball.attach(observer);
            scoreObservers.add(observer);
        }
        score = 0;
        scoreLabel = new Label("Score: " + score);
        Font font = Font.font("Arial", FontWeight.BOLD, 20);
        scoreLabel.setFont(font);
    }

    public void updateScoreDisplay() {
        scoreLabel.setText("Score: " + score);
    }


    @Override
    public Node getNode() {
        return null;
    }

    @Override
    public void addToGroup(ObservableList<Node> groupChildren) {
        groupChildren.add(scoreLabel);
    }

    /**
     * Increases the value of the scoreBoard based on the value of the ball
     * @param ball Ball which has just been sunk for the final time
     */
    public void update(Ball ball) {
        score += ball.getValue();
        updateScoreDisplay();
    }

    public void reset() {
        score = 0;
        updateScoreDisplay();
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    /**
     * Save the current score board state int scoreKeeperMemento
     */
    @Override
    public void saveState() {
        this.scoreKeeperMemento = new ScoreKeeperMemento(this.score);
    }

    /**
     * Restore scoreKeeperMemento
     */
    @Override
    public void restoreState() {
        if (this.scoreKeeperMemento == null) {
            return;
        }
        this.score = this.scoreKeeperMemento.getScore();
        updateScoreDisplay();
    }

    /**
     * Class for scoreKeeperMemento
     */
    private static class ScoreKeeperMemento implements Memento {

        private final int score;

        private ScoreKeeperMemento(int score) {
            this.score = score;
        }

        private int getScore() {
            return this.score;
        }
    }
}
