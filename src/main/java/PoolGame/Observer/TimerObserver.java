package PoolGame.Observer;

import PoolGame.Items.Ball;
import PoolGame.Items.Timer;

public class TimerObserver implements Observer{

    private Timer timer;
    private Ball ball;

    /**
     * An observer which notifies the Timer every time a ball is disposed
     * @param timer The timer object which displays on screen
     * @param ball The ball that this observer will observe
     */
    public TimerObserver(Timer timer, Ball ball) {
        this.ball = ball;
        this.timer = timer;
    }

    /**
     * Updates the timer, passing through the ball being observed.
     */
    @Override
    public void update() {
        timer.update(ball);
    }
}
