package com.asteroids.javaasteroidsgame;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Asteroid extends Character {

    private static final Random RANDOM = new Random();
    private String size;
    private Sounds sounds;

    public Asteroid(int x, int y, Sounds sounds) {
        super(createPolygon(), x, y);
        size = "large";
        this.sounds = sounds;

        setWhiteStroke();
        double initialSpeed = 0.25 + RANDOM.nextDouble() * 0.5;
        double angle = RANDOM.nextDouble() * 2 * Math.PI;
        setMovement(new Point2D(Math.cos(angle) * initialSpeed, Math.sin(angle) * initialSpeed));
    }

    private static Polygon createPolygon() {
        Polygon polygon = new Polygon();
        // random number of corners between 8-14
        double angle = 2 * Math.PI;
        double ran_value = RANDOM.nextDouble();

        while (angle >= 0) {
            double radius = 20 + ran_value * 40;
            double x = Math.cos(angle) * radius;
            double y = Math.sin(angle) * radius;
            polygon.getPoints().addAll(x, y);
            // Random angle with small potential to be negative
            // Random values are connected with radius so that if there is a negative angle it happens with a small radius
            ran_value = RANDOM.nextDouble();
            angle -= 0.2 + 1.2 * ran_value - 0.6 * RANDOM.nextDouble();
        }
        return polygon;
    }

    @Override
    public void setAlive(boolean alive) {
        this.sounds.playSound(this.size);
        super.setAlive(alive);
    }

    public int getPoints(){
        if(size.equals("large")) {
            return 20;
        } else if (size.equals("medium")) {
            return 50;
        } else {
            return 100;
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void scaleSpeedSize() {
        double newSpeed;
        if (size.equals("medium")) { //try: getSize() == "medium"
            getCharacter().setScaleX(0.5);
            getCharacter().setScaleY(0.5);
            newSpeed = 0.5 + RANDOM.nextDouble() * 0.75;
        } else {
            getCharacter().setScaleX(0.25);
            getCharacter().setScaleY(0.25);
            newSpeed = 0.75 + RANDOM.nextDouble() * 1.25;
        }
        double angle = RANDOM.nextDouble() * 2 * Math.PI;
        setMovement(new Point2D(Math.cos(angle) * newSpeed, Math.sin(angle) * newSpeed));
    }

    public Asteroid createSmallerAsteroid() {
        Asteroid smallerAsteroid = new Asteroid((int) getCharacter().getTranslateX(), (int) getCharacter().getTranslateY(), this.sounds);

        if (getSize().equals("large")) {
            smallerAsteroid.setSize("medium");

        } else if (getSize().equals("medium")) { //try: getSize() == "medium"
            smallerAsteroid.setSize("small");
        }

        smallerAsteroid.scaleSpeedSize();
        return smallerAsteroid;
    }
}
