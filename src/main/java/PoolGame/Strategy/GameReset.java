package PoolGame.Strategy;

import PoolGame.Game;
import PoolGame.Items.Ball;
import PoolGame.Prototype.Cloneable;

/** Resets game when the method of this instance is called */
public class GameReset implements BallPocketStrategy {
    public void fallIntoPocket(Game game, Ball ball) {
        game.reset();
    }

    public GameReset(){}

    public GameReset(GameReset gameReset) {

    }

    @Override
    public Cloneable clone() {
        return new GameReset(this);
    }
}
