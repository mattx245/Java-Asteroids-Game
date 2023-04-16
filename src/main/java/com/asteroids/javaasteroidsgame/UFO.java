package com.asteroids.javaasteroidsgame;


import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class UFO extends Character {
    private Point2D targetPosition;
    private long lastShootTime;
    private static final int SHOOT_DELAY = 2000; // 2 seconds

    public void addToPane(Pane pane) {
        pane.getChildren().add(getCharacter());
    }

    private boolean isAlive;

    public UFO(int x, int y) {
        super(new Polygon(-20, 0, -10, -10, 10, -10, 20, 0, 10, 10, -10, 10), x, y);
        this.targetPosition = new Point2D(x, y);
        this.getCharacter().setFill(Color.GREEN);
        this.getCharacter().setVisible(false); // set initial state to invisible
        this.isAlive = false; // Initialize isAlive to false
    }




    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void updateTargetPosition(double x, double y) {
        this.targetPosition = new Point2D(x, y);
    }

    private int movementChangeCooldown = 0;

    public double calculateAngleBetweenUFOAndShip(Ship ship) {
        double deltaX = ship.getCharacter().getTranslateX() - this.getCharacter().getTranslateX();
        double deltaY = ship.getCharacter().getTranslateY() - this.getCharacter().getTranslateY();
        double angle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
        return angle;
    }



//    @Override
//    public void move() {
//        Point2D direction = targetPosition.subtract(getCharacter().getTranslateX(), getCharacter().getTranslateY());
//        direction = direction.normalize().multiply(0.5);
//        setMovement(direction);
//        super.move();
//    }

    public boolean readyToShoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime > SHOOT_DELAY) {
            lastShootTime = currentTime;
            return true;
        }
        return false;
    }
}