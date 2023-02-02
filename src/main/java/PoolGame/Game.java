package PoolGame;

import java.util.ArrayList;
import java.util.List;

import PoolGame.BallBuilder.BallBuilderDirector;
import PoolGame.Config.BallConfig;
import PoolGame.Items.Ball;
import PoolGame.Items.PoolTable;
import PoolGame.Items.ScoreKeeper;
import PoolGame.Items.Timer;
import PoolGame.Observer.CueStickObserver;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.text.Text;

/** The game class that runs the game */
public class Game {
    private PoolTable table;
    private Timer timer;
    private ScoreKeeper scoreKeeper;
    private boolean shownWonText = false;
    private final Text winText = new Text(50, 50, "Win and Bye");

    /**
     * Initialise the game with the provided config
     * @param config The config parser to load the config from
     */
    public Game(ConfigReader config) {
        this.setup(config);
    }

    private void setup(ConfigReader config) {
        this.table = new PoolTable(config.getConfig().getTableConfig());
        List<BallConfig> ballsConf = config.getConfig().getBallsConfig().getBallConfigs();
        List<Ball> balls = new ArrayList<>();
        BallBuilderDirector builder = new BallBuilderDirector();
        builder.registerDefault();
        for (BallConfig ballConf: ballsConf) {
            Ball ball = builder.construct(ballConf);
            if (ball == null) {
                System.err.println("WARNING: Unknown ball, skipping...");
            } else {
                balls.add(ball);
                ball.getCueStick().attach(new CueStickObserver(this));
            }
        }
        this.table.setupBalls(balls);

        this.timer = new Timer(table.getBalls());
        this.scoreKeeper = new ScoreKeeper(table.getBalls());

        this.winText.setVisible(false);
        this.winText.setX(table.getDimX() / 2);
        this.winText.setY(table.getDimY() / 2);
    }

    /**
     * Get the window dimension in the x-axis
     * @return The x-axis size of the window dimension
     */
    public double getWindowDimX() {
        return this.table.getDimX();
    }

    /**
     * Get the window dimension in the y-axis
     * @return The y-axis size of the window dimension
     */
    public double getWindowDimY() {
        return this.table.getDimY();
    }

    /**
     * Get the pool table associated with the game
     * @return The pool table instance of the game
     */
    public PoolTable getPoolTable() {
        return this.table;
    }

    /**
     * Get the timer associated with the game
     * @return The timer instance
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Get the score keeper associated with the game
     * @return The ScoreKeeper instance
     */
    public ScoreKeeper getScoreKeeper() {
        return scoreKeeper;
    }

    /** Add all drawable object to the JavaFX group
     * @param root The JavaFX `Group` instance
    */
    public void addDrawables(Group root) {
        ObservableList<Node> groupChildren = root.getChildren();
        table.addToGroup(groupChildren);
//        timer.addToGroup(groupChildren);
        groupChildren.add(this.winText);
    }

    /** Reset the game */
    public void reset() {
        this.winText.setVisible(false);
        this.shownWonText = false;
        this.table.reset();
        this.timer.reset();
        this.scoreKeeper.reset();
    }

    /** Code to execute every tick. */
    public void tick() {
        if (table.hasWon() && !this.shownWonText) {
            System.out.println(this.winText.getText());
            this.winText.setVisible(true);
            this.shownWonText = true;
        }
        timer.tick();
        table.checkPocket(this);
        table.handleCollision();
        this.table.applyFrictionToBalls();
        for (Ball ball : this.table.getBalls()) {
            ball.move();
        }
    }

    /**
     * Called when pressing 'U' on the keyboard. Undoes the most recent move
     * resets the score, timer and ball positions back to where they were last turn.
     */
    public void undo() {
        this.table.undo();
        this.timer.restoreState();
        this.scoreKeeper.restoreState();
    }

    /**
     * Saves the current state of the game.
     */
    public void saveState() {
        this.table.save();
        this.timer.saveState();
        this.scoreKeeper.saveState();
    }

    /**
     * Makes cheat functionality accessible through game
     */
    public void cheat(String color) {
        for (Ball ball : this.table.getBalls()) {
//            System.out.println(ball.getColourName());
            if (ball.getColourName().equalsIgnoreCase(color) && !ball.isDisabled()) {
                ball.disable();
            }
        }
    }
}
