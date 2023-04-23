package com.asteroids.javaasteroidsgame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Projectile extends Character {

    private int shooting_distance;
    private ProjectileOrigin origin;

    public enum ProjectileOrigin {
        SHIP,
        UFO
    }

    public Projectile(int x, int y, double rotation, ProjectileOrigin origin) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
        //-1, 1, 1, 1, 0, -2
        this.origin = origin;
        this.setAlive(true);

        this.getCharacter().setFill(Color.WHITE);
        this.getCharacter().setRotate(rotation);

        double radians = Math.toRadians(rotation);
        double offsetX = 10 + 1 * Math.cos(radians);
        double offsetY = -4 + 1 * Math.sin(radians);

        this.getCharacter().setTranslateX(x + offsetX);
        this.getCharacter().setTranslateY(y + offsetY);

        this.shooting_distance = 100;
    }

//
    public ProjectileOrigin getOrigin() {
        return origin;
    }

    @Override
    public void move() {
        if (shooting_distance < 1) {
            this.setAlive(false);
            this.getCharacter().setVisible(false); // Hide the projectile when it's no longer alive
        } else {
            super.move();
            shooting_distance -= 1;
        }
    }
}
