package PoolGame.Strategy;

import PoolGame.Game;
import PoolGame.Items.Ball;
import PoolGame.Prototype.Cloneable;

/** Hide the ball once it falls into the pocket */
public class PocketOnce implements BallPocketStrategy {
    public void fallIntoPocket(Game game, Ball ball) {
        ball.disable();
    }

    public PocketOnce(){}

    public PocketOnce(PocketOnce pocketOnce) {

    }

    @Override
    public Cloneable clone() {
        return new PocketOnce(this);
    }
}
