package PoolGame.Strategy;

import PoolGame.Game;
import PoolGame.Items.Ball;
import PoolGame.Prototype.Cloneable;

/** Hide the ball once it falls into the pocket twice or spawn it back to its
 * original location. If there is a ball on the original location, hide the ball.
 */
public class PocketTwice implements BallPocketStrategy {

    private final int FALL_COUNTER_THRESHOLD = 2;

    public void fallIntoPocket(Game game, Ball ball) {
        fallIntoPocketMultiple(game, ball, FALL_COUNTER_THRESHOLD);
    }

    static void fallIntoPocketMultiple(Game game, Ball ball, int fall_counter_threshold) {
        ball.incrementFallCounter();
        if (ball.getFallCounter() >= fall_counter_threshold) {
            ball.disable();
        } else {
            ball.resetPosition();
            for (Ball ballB: game.getPoolTable().getBalls()) {
                if (ball.isColliding(ballB)) {
                    ball.disable();
                }
            }
        }
    }

    public PocketTwice(){}

    public PocketTwice(PocketTwice pocketTwice) {

    }

    @Override
    public Cloneable clone() {
        return new PocketTwice(this);
    }
}
