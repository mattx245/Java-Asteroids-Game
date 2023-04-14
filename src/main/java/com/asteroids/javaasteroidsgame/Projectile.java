package com.asteroids.javaasteroidsgame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Projectile extends Character {

    private int shooting_distance;

    public enum ProjectileOrigin {
        SHIP,
        UFO
    }

    public Projectile(int x, int y, double rotation, ProjectileOrigin origin) {
        super(new Polygon(-1, 1, 1, 1, 0, -2), x, y); // Small triangle shape
        this.origin = origin;
        this.setAlive(true);

        this.getCharacter().setFill(Color.YELLOW);
        this.getCharacter().setRotate(rotation);

        double radians = Math.toRadians(rotation);
        double offsetX = 20 * Math.cos(radians);
        double offsetY = 20 * Math.sin(radians);

        this.getCharacter().setTranslateX(x + offsetX);
        this.getCharacter().setTranslateY(y + offsetY);

        this.shooting_distance = 100;
    }
    private ProjectileOrigin origin;

    public Projectile(int x, int y, ProjectileOrigin origin) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
        this.origin = origin;
    }

    public ProjectileOrigin getOrigin() {
        return origin;
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
