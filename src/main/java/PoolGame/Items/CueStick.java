package PoolGame.Items;

import PoolGame.Observer.Observer;
import PoolGame.Observer.Subject;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import PoolGame.Drawable;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CueStick implements Drawable, Subject {

    private Rectangle rectangle;

    private Circle circle;
    private Rotate rotate;
    private final double XOFFSET = 3;
    private final double WIDTH = 6;
    private static final double HEIGHT = 125;
    private final double RADIUS = 7.5;
    private List<Observer> observers;

    /**
     * Initialises the CueStick object
     */
    public CueStick() {
        rectangle = new Rectangle();
        rectangle.setFill(Paint.valueOf("tan"));
        rectangle.setVisible(false);
        circle = new Circle();
        circle.setFill(Paint.valueOf("MAROON"));
        circle.setRadius(RADIUS);
        circle.setVisible(false);
        rotate = new Rotate();
        rotate.pivotXProperty().bind(circle.centerXProperty());
        rotate.pivotYProperty().bind(circle.centerYProperty());
        rectangle.getTransforms().add(rotate);
        observers = new ArrayList<>();
    }

    /**
     * Makes the CueStick visible, sets its position
     * @param centerX X Center of CueBall
     * @param centerY Y Center of CueBall
     * @param cueBall CueBall object
     */
    public void startCueStick(double centerX, double centerY, Ball cueBall) {
        rectangle.setX(centerX-XOFFSET);
        rectangle.setY(centerY);
        rectangle.setWidth(WIDTH);
        rectangle.setHeight(HEIGHT);
        circle.setCenterX(centerX);
        circle.setCenterY(centerY+HEIGHT);
        rotate.setAngle(0);

        registerCircleActions(centerX, centerY, cueBall);
    }

    @Override
    public Node getNode() {
        return this.rectangle;
    }

    @Override
    public void addToGroup(ObservableList<Node> groupChildren) {

    }

    public Circle getCircle() {
        return circle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Makes the cueStick visible or invisible
     * @param b true for visible, false for invisible
     */
    public void setVisible(boolean b) {
        rectangle.setVisible(b);
        circle.setVisible(b);
    }

    /**
     * Registers the actions of the CueStick when clicked
     * @param centerX X Center of CueBall
     * @param centerY Y Center of CueBall
     * @param cueBall CueBall object
     */
    public void registerCircleActions(double centerX, double centerY, Ball cueBall) {
        AtomicReference<Double> initX = new AtomicReference<>((double) 0);
        AtomicReference<Double> initY = new AtomicReference<>((double) 0);
        this.circle.setOnMousePressed(
                (actionEvent) -> {
                    initX.set(actionEvent.getX());
                    initY.set(actionEvent.getY());
                }
        );

        this.circle.setOnMouseDragged(
                (actionEvent) -> {
                    circle.setCenterX(actionEvent.getX());
                    circle.setCenterY(actionEvent.getY());

                    double x = initX.get();
                    double relativeX = actionEvent.getX() - x;
                    double y = initY.get();
                    double relativeY = actionEvent.getY() - y;

                    rectangle.setX(centerX - XOFFSET + relativeX);
                    rectangle.setY(centerY + relativeY);
                    rotate.setAngle(calculateAngle(centerX , centerY, actionEvent.getX(), actionEvent.getY()));
        });

        this.circle.setOnMouseReleased(
                (actionEvent) -> {
                    this.setVisible(false);
                    alert();
                    //TODO: if time, figure out how to get the ball to move based on the tip of the CueStick
                    Point2D vec = cueBall.calculateCueBallVelOnHit(actionEvent.getX(), actionEvent.getY());
                    cueBall.setXVel(vec.getX());
                    cueBall.setYVel(vec.getY());
                }
        );
    }

    /**
     * Calculates the angle of rotation for CueStick to point at ball
     * @param x1 CenterX of CueBall
     * @param y1 CenterY of CueBall
     * @param x2 Current mouse position X
     * @param y2 Current mouse position Y
     * @return Angle of rotation
     */
    public static double calculateAngle(double x1, double y1, double x2, double y2)
    {
        double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        // Keep angle between 0 and 360
        angle = angle + Math.ceil( -angle / 360 ) * 360;

        return -angle;
    }

    public static double getHeight() {
        return HEIGHT;
    }

    /**
     * Detaches observer
     * @param observer CueStick Observer
     */
    @Override
    public void detach(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Attaches observer
     * @param observer CueStick Observer
     */
    @Override
    public void attach(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Updates observers
     */
    @Override
    public void alert() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
