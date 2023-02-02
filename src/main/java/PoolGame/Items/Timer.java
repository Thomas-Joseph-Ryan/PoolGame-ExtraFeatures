package PoolGame.Items;

import PoolGame.Drawable;
import PoolGame.Memento.Memento;
import PoolGame.Memento.Savable;
import PoolGame.Observer.TimerObserver;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class Timer implements Drawable, Savable {

    private final List<TimerObserver> timerObservers;
    private int disabled = 0;
    private final Label timeLabel;
    private int clock;
    private int tick;
    private final int FPS = 60;
    private boolean stopped;
    private TimerMemento timerMemento = null;

    /**
     * Constructs a timer object, creates all the timer observers and attaches a unique observer to each ball
     * @param balls The list of balls in the game
     */
    public Timer(List<Ball> balls) {
        this.timerObservers = new ArrayList<>();
        for (Ball ball : balls) {
            TimerObserver observer = new TimerObserver(this, ball);
            ball.attach(observer);
            timerObservers.add(observer);
        }
        tick = 0;
        clock = 0;
        timeLabel = new Label("Time: " + clock);
        Font font = Font.font("Arial", FontWeight.BOLD, 20);
        timeLabel.setFont(font);
        stopped = false;
    }

    /**
     * Updates the label displayed
     */
    private void updateTimerDisplay() {
        timeLabel.setText("Time: " + clock);
    }

    /**
     * Called by the timer observers, updates the timers behaviour based on what ball / how many balls are pocketed
     * @param ball The ball object that was just disposed
     */
    public void update(Ball ball) {
        if (ball.getBallType().equals(Ball.BallType.CUEBALL)) {
            stop();
        } else if (disabled == timerObservers.size() - 1) {
            //All balls are disabled except for CueBall
            stop();
        } else {
            disabled++;
        }
    }

    /**
     * Logic gone through each frame
     */
    public void tick() {
        if (stopped) {
            return;
        }
        if (tick > FPS) {
            tick = 0;
            clock++;
            updateTimerDisplay();
        }
        tick++;
    }

    public void stop() {
        this.stopped = true;
    }

    public void start() {
        this.stopped = false;
    }


    @Override
    public Node getNode() {
        return null;
    }

    @Override
    public void addToGroup(ObservableList<Node> groupChildren) {
        groupChildren.add(timeLabel);
    }

    /**
     * resets the timer
     */
    public void reset() {
        tick = 0;
        clock = 0;
        updateTimerDisplay();
        stopped = false;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    /**
     * Memento save state
     */
    @Override
    public void saveState() {
        this.timerMemento = new TimerMemento(this.disabled, this.clock, this.tick, this.stopped);
    }

    /**
     * Memento restore state
     */
    @Override
    public void restoreState() {
        if (this.timerMemento == null) {
            return;
        }
        this.stopped = this.timerMemento.isStopped();
        this.tick = this.timerMemento.getTick();
        this.clock = this.timerMemento.getClock();
        this.disabled = this.timerMemento.getDisabled();
    }

    /**
     * Timer memento class
     */
    private static class TimerMemento implements Memento {
        private final int disabled;
        private final int clock;
        private final int tick;
        private final boolean stopped;

        public TimerMemento(int disabled, int clock, int tick, boolean stopped) {
            this.clock = clock;
            this.stopped = stopped;
            this.disabled = disabled;
            this.tick = tick;
        }

        private boolean isStopped() {
            return stopped;
        }

        private int getClock() {
            return clock;
        }

        private int getDisabled() {
            return disabled;
        }

        private int getTick() {
            return tick;
        }
    }
}
