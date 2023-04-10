package com.asteroids.javaasteroidsgame;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid extends Character {

    private double rotationalMovement;
    private String size;

    public Asteroid(int x, int y) {

        //large: slow -> 2M
        //medium: faster -> 2S
        //small: even faster -> destroyed

        super(new PolygonFactory().createPolygon(), x, y);
        size = "Large";
        Random rnd = new Random();

        super.getCharacter().setRotate(rnd.nextInt(360));

        int accelerationAmount = 1 + rnd.nextInt(10);
        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }

        this.rotationalMovement = 0.5 - rnd.nextDouble();
    }

    public Asteroid(int x, int y, String size) {
        super(new PolygonFactory().createPolygon(), x, y);
        Random rnd = new Random();

        super.getCharacter().setRotate(rnd.nextInt(360));

        int accelerationAmount = 1 + rnd.nextInt(10);
        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }

        this.rotationalMovement = 0.5 - rnd.nextDouble();
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Polygon createAsteroid(String size) {
        Random rnd = new Random();

        double size_n = 10 + rnd.nextInt(10);

        Polygon polygon = new Polygon();
        double c1 = Math.cos(Math.PI * 2 / 5);
        double c2 = Math.cos(Math.PI / 5);
        double s1 = Math.sin(Math.PI * 2 / 5);
        double s2 = Math.sin(Math.PI * 4 / 5);

        polygon.getPoints().addAll(
                size_n, 0.0,
                size_n * c1, -1 * size_n * s1,
                -1 * size_n * c2, -1 * size_n * s2,
                -1 * size_n * c2, size_n * s2,
                size_n * c1, size_n * s1);

        for (int i = 0; i < polygon.getPoints().size(); i++) {
            int change = rnd.nextInt(5) - 2;
            polygon.getPoints().set(i, polygon.getPoints().get(i) + change);
        }

        return polygon;
    }

    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
    }
}
