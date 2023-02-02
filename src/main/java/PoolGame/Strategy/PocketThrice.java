package PoolGame.Strategy;

import PoolGame.Game;
import PoolGame.Items.Ball;
import PoolGame.Prototype.Cloneable;

/** Hide the ball once it falls into the pocket three times or spawn it back to its
 * original location. If there is a ball on the original location, hide the ball.
 */
public class PocketThrice implements BallPocketStrategy {
    private final int FALL_COUNTER_THRESHOLD = 3;

    public void fallIntoPocket(Game game, Ball ball) {
        PocketTwice.fallIntoPocketMultiple(game, ball, FALL_COUNTER_THRESHOLD);
    }

     public PocketThrice(){}

    public PocketThrice(PocketThrice pocketThrice) {

    }

    @Override
    public Cloneable clone() {
        return new PocketThrice(this);
    }
}
