package com.asteroids.javaasteroidsgame;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class UFO extends Character {
    private Point2D targetPosition;

    private boolean isAlive;

    // UFO constructor
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

    // Method for calculating angle for UFO shooting logic
    public double calculateAngleBetweenUFOAndShip(Ship ship) {
        double deltaX = ship.getCharacter().getTranslateX() - this.getCharacter().getTranslateX();
        double deltaY = ship.getCharacter().getTranslateY() - this.getCharacter().getTranslateY();
        double angle = Math.atan2(deltaY, deltaX);
        return angle;
    }

    public void setVisibility(boolean visibility) {
        this.getCharacter().setVisible(visibility);
    }

}