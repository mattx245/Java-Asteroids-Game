package com.asteroids.javaasteroidsgame;

import javafx.scene.shape.Polygon;

public class Ship extends Character {
    public int health;
    public Ship(int x, int y) {

        super(new Polygon(0,0,-10,20,10,20), x, y);


    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        health = newHealth;
    }

    public void death() {
        this.health = this.health-1;
    }
}

