package PoolGame.Observer;

import PoolGame.Game;

/**
 * An observer for the CueStick.
 * The intention for this class is to notify the Game to save the state just before the cueStick hits the ball.
 */
public class CueStickObserver implements Observer{

    Game game;

    public CueStickObserver(Game game) {
        this.game = game;
    }
    @Override
    public void update() {
        game.saveState();
    }
}
