/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package PoolGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Objects;

import PoolGame.Items.CueStick;
import PoolGame.Items.Pocket;
import PoolGame.Items.PoolTable;
import PoolGame.Items.TopPane;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.json.simple.parser.ParseException;

import PoolGame.ConfigReader.ConfigKeyMissingException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.util.Duration;

/** The JavaFX application */
public class App extends Application {

    private final double FRAMETIME = 1.0 / 60.0;
    private Stage stage;
    private Pane pane;
    private Group root;
    private BorderPane borderPane;
    private Scene scene;

    private ConfigReader loadConfig(List<String> args) {
        String configPath;
        boolean isResourcesDir = false;
		if (args.size() > 0) {
			configPath = args.get(0);
		} else {
//			 configPath = "src/main/resources/config_easy.json";
			configPath = "/config_easy.json";
            isResourcesDir = true;
		}
		// parse the file:
        ConfigReader config = null;
        try {
            config = new ConfigReader(configPath, isResourcesDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s\n", e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s\n", e.getMessage());
            System.exit(1);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s\n", e.getMessage());
            System.exit(1);
        } catch (ConfigKeyMissingException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s\n", e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s\n", e.getMessage());
            System.exit(1);
        }
        return config;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane = new Pane();
        root = new Group();
        pane.getChildren().add(root);

        borderPane.setCenter(pane);
        scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setTitle("PoolGame");
        stage.show();

        ConfigReader config = loadConfig(getParameters().getRaw());
        Game game = new Game(config);

        pane.setMaxSize(game.getWindowDimX(), game.getWindowDimY());
        pane.setPrefSize(game.getWindowDimX(), game.getWindowDimY());
        pane.setMinSize(game.getWindowDimX(), game.getWindowDimY());

        stage.setHeight(game.getWindowDimY() + CueStick.getHeight()*3);
        stage.setWidth(game.getWindowDimX() + CueStick.getHeight()*3);

        //Add timer and score to top pane
        borderPane.setTop(TopPane.addHBox(game.getTimer(), game.getScoreKeeper()));

        game.addDrawables(root);
        addKeyBinds(game, scene);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(FRAMETIME),
        (actionEvent) -> {
                game.tick();
            });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    /**
     * The entry point of the program
     * @param args CLI arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Relaunches the game engine with a different level loaded for the config reader.
     * Uses the same fx display objects so the window is not closed and reopened, but rather
     * the group of objects being displayed changes.
     * @param configReader The new configReader object with a different config path
     */
    public void changeLevels(ConfigReader configReader) {

        root.getChildren().clear();
        Game game = new Game(configReader);

        //Add timer and score to top pane
        borderPane.setTop(TopPane.addHBox(game.getTimer(), game.getScoreKeeper()));

        game.addDrawables(root);
        addKeyBinds(game, scene);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(FRAMETIME),
                (actionEvent) -> {
                    game.tick();
                });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    /**
     * Adds the key bindings necessary for the scene.
     * @param game The game object running the current game
     * @param scene The scene that is currently on the stage
     */
    private void addKeyBinds(Game game, Scene scene) {

        scene.setOnKeyPressed(
                (actionEvent) -> {
                    //Reset Keybinding
                    if (Objects.equals(actionEvent.getCode().getChar(), "R")) {
                        game.reset();
                    }
                    //Level select keybindings
                    else if (Objects.equals(actionEvent.getCode().getChar(), "E")) {
                        List<String> path = new ArrayList<>();
                        path.add("src/main/resources/config_easy.json");
                        changeLevels(loadConfig(path));
                    } else if (Objects.equals(actionEvent.getCode().getChar(), "M")) {
                        List<String> path = new ArrayList<>();
                        path.add("src/main/resources/config_normal.json");
                        changeLevels(loadConfig(path));
                    } else if (Objects.equals(actionEvent.getCode().getChar(), "H")) {
                        List<String> path = new ArrayList<>();
                        path.add("src/main/resources/config_hard.json");
                        changeLevels(loadConfig(path));
                    }
                    //Undo keybinding
                    else if (Objects.equals(actionEvent.getCode().getChar(), "U")) {
                        game.undo();
                    }
                    //Cheat keybindings
                    else if (Objects.equals(actionEvent.getCode().getChar(), "1")) {
                        game.cheat("0xff0000ff");
                    } else if (Objects.equals(actionEvent.getCode().getChar(), "2")) {
                        game.cheat("0xffff00ff");
                    } else if (Objects.equals(actionEvent.getCode().getChar(), "3")) {
                        game.cheat("0x006400ff");
                    } else if (Objects.equals(actionEvent.getCode().getChar(), "4")) {
                        game.cheat("0xa52a2aff");
                    } else if (Objects.equals(actionEvent.getCode().getChar(), "5")) {
                        game.cheat("0x0000ffff");
                    } else if (Objects.equals(actionEvent.getCode().getChar(), "6")) {
                        game.cheat("0x800080ff");
                    } else if (Objects.equals(actionEvent.getCode().getChar(), "7")) {
                        game.cheat("0x000000ff");
                    } else if (Objects.equals(actionEvent.getCode().getChar(), "8")) {
                        game.cheat("0xffa500ff");
                    }
                }
        );
    }
}