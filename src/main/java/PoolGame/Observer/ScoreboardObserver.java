package PoolGame.Observer;

import PoolGame.Items.Ball;
import PoolGame.Items.ScoreKeeper;

/**
 * Scoreboard Observer class to attach to a ball. Calls the scoreKeeper.update() method when updated to inform it that the score must change
 */
public class ScoreboardObserver implements Observer{

    Ball ball;
    ScoreKeeper scoreKeeper;

    public ScoreboardObserver (ScoreKeeper scoreKeeper, Ball ball) {
        this.ball = ball;
        this.scoreKeeper = scoreKeeper;
    }
    @Override
    public void update() {
        scoreKeeper.update(ball);
    }
}
