package com.asteroids.javaasteroidsgame;

import javafx.scene.shape.Polygon;

public class Projectile extends Character {

    private int shooting_distance;

    public Projectile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
        shooting_distance = 100;
    }

    @Override
    public void move() {
        if(shooting_distance < 1) {
            this.setAlive(false);
        } else {
            super.move();
            shooting_distance -= 1;
        }
    }
}
