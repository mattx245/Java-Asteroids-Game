package com.asteroids.javaasteroidsgame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Projectile extends Character {

    private int shootingDistance;
    private ProjectileOrigin origin;

    public enum ProjectileOrigin {
        SHIP,
        UFO
    }

    public Projectile(int x, int y, double rotation, ProjectileOrigin origin) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
        this.origin = origin;
        this.setAlive(true);

        this.getCharacter().setFill(Color.WHITE);
        this.getCharacter().setRotate(rotation);

        double radians = Math.toRadians(rotation);
        double offsetX = 10 + Math.cos(radians);
        double offsetY = -4 + Math.sin(radians);

        this.getCharacter().setTranslateX(x + offsetX);
        this.getCharacter().setTranslateY(y + offsetY);

        this.shootingDistance = 100;
    }

//
    public ProjectileOrigin getOrigin() {
        return origin;
    }

    @Override
    public void move() {
        if (shootingDistance < 1) {
            this.setAlive(false);
            this.getCharacter().setVisible(false); // Hide the projectile when it's no longer alive
        } else {
            super.move();
            shootingDistance -= 1;
        }
    }
}
